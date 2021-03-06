package br.com.ifsp.pi.lixt.facade;

import java.time.LocalDateTime;
import java.util.List;

import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import org.springframework.stereotype.Service;
import br.com.ifsp.pi.lixt.data.business.comment.Comment;
import br.com.ifsp.pi.lixt.data.business.comment.CommentService;
import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.utils.exceptions.ForbiddenException;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import br.com.ifsp.pi.lixt.utils.security.Users;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.ValidatorAccess;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentFacade {
	
	private final CommentService commentService;
	private final ListOfItemsService listOfItemsService;
	private final UserService userService;
	
	public Comment findById(Long id) {
		
		Long ownerId = this.listOfItemsService.findOwnerIdByCommentId(id);
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByCommentId(id);
		
		if(!(ValidatorAccess.canAcces(membersIds) || ValidatorAccess.canAcces(ownerId))) {
			throw new ForbiddenException();
		}
		
		return this.commentService.findById(id);
	}
	
	public Comment save(Comment comment) {
		
		Long ownerId = this.listOfItemsService.findOwnerIdByProductOfListId(comment.getProductOfListId());
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByProductOfListId(comment.getProductOfListId());
		
		if(!(ValidatorAccess.canAcces(membersIds) || ValidatorAccess.canAcces(ownerId))) {
			throw new ForbiddenException();
		}
		
		comment.setDate(LocalDateTime.now());
		comment.setUserId(Users.getUserId());

		User user = this.userService.findById(Users.getUserId());
		comment.setUser(user);
		return this.commentService.save(comment);
	}
	
	public Comment update(Comment comment, Long id) throws PreconditionFailedException {

		Long userId = this.commentService.findUserIdByCommentId(id);

		if(!(comment.getId().equals(id) && comment.getUserId().equals(userId)))
			throw new PreconditionFailedException("Erro ao atualizar coment??rio");
		
		if(!ValidatorAccess.canAcces(comment.getUserId())) 
			throw new ForbiddenException();
		
		return this.commentService.save(comment);
	}
	
	public void deleteById(Long id) {
		
		Long userId = this.commentService.findUserIdByCommentId(id);
		
		if(!ValidatorAccess.canAcces(userId)) 
			throw new ForbiddenException();
		
		this.commentService.deleteById(id);
	}

}
