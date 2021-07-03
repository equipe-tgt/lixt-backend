package br.com.ifsp.pi.lixt.facade;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.list.ListOfItems;
import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembers;
import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembersService;
import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.data.enumeration.StatusListMember;
import br.com.ifsp.pi.lixt.utils.exceptions.ForbiddenException;
import br.com.ifsp.pi.lixt.utils.exceptions.NotFoundException;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.ValidatorAccess;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListMembersFacade {
	
	private final UserService userService;
	private final ListMembersService listMembersService;
	private final ListOfItemsService listOfItemsService;
	
	public ListMembers sendInvite(Long listId, String username) throws RuntimeException {
		
		ListOfItems listOfItems = this.listOfItemsService.findById(listId);
		
		if(!ValidatorAccess.canAcces(listOfItems.getOwnerId())) {
			throw new ForbiddenException();
		}
		
		User user = this.userService.findByUsernameOrEmail(username);
		
		if(Objects.isNull(user)) {
			throw new NotFoundException("Usuário não encontrado");
		}
		
		ListMembers listMembers = ListMembers.builder()
				.listId(listId)
				.userId(user.getId())
				.user(user)
				.statusListMember(StatusListMember.WAITING)
				.build();
		
		return this.listMembersService.save(listMembers);
	}
	
	public ListMembers acceptInvite(Long listMembersId) throws RuntimeException {
		
		ListMembers listMembers = this.listMembersService.findById(listMembersId);
		
		if(!ValidatorAccess.canAcces(listMembers.getUserId())) {
			throw new ForbiddenException();
		}
		
		listMembers.setStatusListMember(StatusListMember.ACCEPT);
		return this.listMembersService.save(listMembers);
	}
	
	public ListMembers rejectInvite(Long listMembersId) throws RuntimeException {
		
		ListMembers listMembers = this.listMembersService.findById(listMembersId);
		
		if(!ValidatorAccess.canAcces(listMembers.getUserId())) {
			throw new ForbiddenException();
		}
		
		listMembers.setStatusListMember(StatusListMember.REJECT);
		return this.listMembersService.save(listMembers);
	}
	
	public void removeUserAtList(Long listMembersId) throws RuntimeException {
		
		ListMembers listMembers = this.listMembersService.findById(listMembersId);
		
		if(!ValidatorAccess.canAcces(listMembers.getUserId())) {
			throw new ForbiddenException();
		}
		
		this.listMembersService.deleteById(listMembersId);
	}

}
