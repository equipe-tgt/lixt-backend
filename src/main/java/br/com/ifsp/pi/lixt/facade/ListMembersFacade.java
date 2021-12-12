package br.com.ifsp.pi.lixt.facade;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembers;
import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembersService;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.data.enumeration.StatusListMember;
import br.com.ifsp.pi.lixt.utils.exceptions.DuplicatedDataException;
import br.com.ifsp.pi.lixt.utils.exceptions.ForbiddenException;
import br.com.ifsp.pi.lixt.utils.exceptions.NotFoundException;
import br.com.ifsp.pi.lixt.utils.exceptions.SendMailException;
import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.mail.templates.TypeMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.CreatorParametersMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.FormatterMail;
import br.com.ifsp.pi.lixt.utils.security.Users;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.ValidatorAccess;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListMembersFacade {
	
	private final UserService userService;
	private final ListMembersService listMembersService;
	private final ListOfItemsService listOfItemsService;
	private final SenderMail senderMail;
	
	public ListMembers sendInvite(Long listId, String username) throws ForbiddenException, NotFoundException {
		
		Long ownerIdList = this.listOfItemsService.findOwnerIdByListId(listId);
		
		if(!ValidatorAccess.canAcces(ownerIdList)) {
			throw new ForbiddenException();
		}
		
		var user = this.userService.findByUsernameOrEmail(username);
		
		if(Objects.isNull(user)) {
			throw new NotFoundException("Usuário não encontrado");
		}
		
		var listMembers = this.listMembersService.findByListIdAndUserId(listId, user.getId());
		
		if(Objects.nonNull(listMembers)) {
			throw new DuplicatedDataException("Convite já enviado para esse usuário");
		}
		
		listMembers = ListMembers.builder()
				.listId(listId)
				.userId(user.getId())
				.user(user)
				.statusListMember(StatusListMember.WAITING)
				.build();
		
		return this.listMembersService.save(listMembers);
	}
	
	public ListMembers alterStatusInvite(Long listMembersId, StatusListMember status) throws ForbiddenException {
		
		var listMembers = this.listMembersService.findById(listMembersId);
		
		if(!ValidatorAccess.canAcces(listMembers.getUserId())) {
			throw new ForbiddenException();
		}
		
		listMembers.setStatusListMember(status);
		return this.listMembersService.save(listMembers);
	}
	
	public void removeUserAtList(Long listMembersId) throws ForbiddenException {
		
		Long memberId = this.listMembersService.findUserIdByListMembersId(listMembersId);
		Long ownerId = this.listOfItemsService.findOwnerIdByListMemberId(listMembersId);
		
		if(!(ValidatorAccess.canAcces(ownerId) || ValidatorAccess.canAcces(memberId))) {
			throw new ForbiddenException();
		}
		
		this.listMembersService.deleteById(listMembersId);
	}
	
	public List<ListMembers> findListMembersSentByUser() {
		return this.listOfItemsService.findListMembersSentByUser(Users.getUserId());
	}
	
	public List<ListMembers> findListMembersReceviedByUser() {
		return this.listMembersService.findListMembersReceviedByUser(Users.getUserId());
	}
	
	public boolean inviteJoinPlatform(String email, Languages language) {
		if(Objects.nonNull(this.userService.findByEmail(email)))
			throw new DuplicatedDataException("Usuário já cadastrado na plataforma");
		
		MailDto mail = TypeMail.INVITE.apply(language);
		Map<String, String> params = CreatorParametersMail.invite(Users.getName(), Users.getEmail());
		FormatterMail.build(mail, params, email);
		
		boolean responseSendMail = senderMail.sendEmail(mail);

		if(!responseSendMail) {
			throw new SendMailException();
		}
		
		return true;
	}

}
