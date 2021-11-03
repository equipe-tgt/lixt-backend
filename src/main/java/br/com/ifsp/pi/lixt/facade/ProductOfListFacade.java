package br.com.ifsp.pi.lixt.facade;

import br.com.ifsp.pi.lixt.data.business.comment.Comment;
import br.com.ifsp.pi.lixt.data.business.globalcomment.GlobalComment;
import br.com.ifsp.pi.lixt.data.business.globalcomment.GlobalCommentService;
import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfList;
import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfListService;
import br.com.ifsp.pi.lixt.dto.specific.AllCommentsDto;
import br.com.ifsp.pi.lixt.mapper.specific.AllCommentsMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.ForbiddenException;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import br.com.ifsp.pi.lixt.utils.security.Users;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.ValidatorAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductOfListFacade {
	
	private final ProductOfListService productOfListService;
	private final ListOfItemsService listOfItemsService;
	private final GlobalCommentService globalCommentService;
	
	public ProductOfList findById(Long id) {
		
		Long ownerId = this.listOfItemsService.findOwnerIdByProductOfListId(id);
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByProductOfListId(id);
		
		if(!(ValidatorAccess.canAcces(membersIds) || ValidatorAccess.canAcces(ownerId))) {
			throw new ForbiddenException();
		}
		
		return this.productOfListService.findById(id);
	}
	
	public ProductOfList save(ProductOfList productOfList) {
		
		Long ownerId = this.listOfItemsService.findOwnerIdByListId(productOfList.getListId());
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByListId(productOfList.getListId());
		
		if(!(ValidatorAccess.canAcces(membersIds) || ValidatorAccess.canAcces(ownerId))) {
			throw new ForbiddenException();
		}
		
		return this.productOfListService.save(productOfList);
	}
	
	public ProductOfList update(ProductOfList productOfList, Long id) throws PreconditionFailedException {
		
		if(!productOfList.getId().equals(id))
			throw new PreconditionFailedException("Erro ao atualizar produto da lista");
		
		Long ownerId = this.listOfItemsService.findOwnerIdByProductOfListId(id);
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByProductOfListId(id);
		
		if(!ValidatorAccess.canAcces(membersIds) && !ValidatorAccess.canAcces(ownerId)) {
			throw new ForbiddenException();
		}
		
		return this.productOfListService.save(productOfList);
	}
	
	public void deleteById(Long id) {
		
		Long ownerId = this.listOfItemsService.findOwnerIdByProductOfListId(id);
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByProductOfListId(id);
		
		if(!(ValidatorAccess.canAcces(membersIds) || ValidatorAccess.canAcces(ownerId))) {
			throw new ForbiddenException();
		}
		
		this.productOfListService.deleteById(id);
	}
	
	public AllCommentsDto findCommentsByProductOfListId(Long id) {
		
		Long ownerId = this.listOfItemsService.findOwnerIdByProductOfListId(id);
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByProductOfListId(id);
		
		if(!(ValidatorAccess.canAcces(membersIds) || ValidatorAccess.canAcces(ownerId))) {
			throw new ForbiddenException();
		}

		List<GlobalComment> globalComments = this.globalCommentService.findByProductId(id);
		globalComments = globalComments.stream()
				.filter(comment -> comment.getIsPublic() || Objects.equals(Users.getUserId(), comment.getUserId()))
				.collect(Collectors.toList());
		List<Comment> comments = this.productOfListService.findCommentsByProductOfListId(id);
		
		return AllCommentsMapper.entityToDto(globalComments, comments);
	}
	
	public Integer markProduct(Long productId) {
		Long ownerId = this.listOfItemsService.findOwnerIdByProductOfListId(productId);
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByProductOfListId(productId);
		
		if(!(ValidatorAccess.canAcces(membersIds) || ValidatorAccess.canAcces(ownerId))) {
			throw new ForbiddenException();
		}
		
		var productOfList = this.productOfListService.findById(productId);
		
		if(productOfList.getIsMarked()) {
			return 0;
		}
		
		return this.productOfListService.markProduct(Users.getUserId(), productId);
	}
	
	public Integer cleanProductOfList(Long productOfListId) {
		Long ownerId = this.listOfItemsService.findOwnerIdByProductOfListId(productOfListId);
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByProductOfListId(productOfListId);
		
		if(!(ValidatorAccess.canAcces(membersIds) || ValidatorAccess.canAcces(ownerId))) {
			throw new ForbiddenException();
		}
		
		return this.productOfListService.cleanProductOfList(productOfListId);
	}
	
	public Integer assignedItemToMe(Long productOfListId) {
		
		Long ownerId = this.listOfItemsService.findOwnerIdByProductOfListId(productOfListId);
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByProductOfListId(productOfListId);
		
		if(!(ValidatorAccess.canAcces(membersIds) || ValidatorAccess.canAcces(ownerId))) {
			throw new ForbiddenException();
		}
		
		Long oldAssignedUserId = this.productOfListService.findById(productOfListId).getAssignedUserId();
		
		if(Objects.isNull(oldAssignedUserId)) {
			return this.productOfListService.assignedItemToUser(Users.getUserId(), true, productOfListId);
		}
		if(Objects.equals(Users.getUserId(), oldAssignedUserId)) {
			return this.productOfListService.assignedItemToUser(null, false, productOfListId);
		}
		
		return 0;
	}
	
	public Integer assignedItemToUser(Long userId, Long productOfListId) {
		Long ownerId = this.listOfItemsService.findOwnerIdByProductOfListId(productOfListId);
		
		if(!ValidatorAccess.canAcces(ownerId))
			throw new ForbiddenException();
		
		var productOfList = this.productOfListService.findById(productOfListId);
		
		if(productOfList.getIsMarked()) {
			return 0;
		}
		
		return this.productOfListService.assignedItemToUser(userId, productOfListId);
	}

	public Integer updateMarkedAmount(Integer markedAmount, Long productOfListId) {
		return this.productOfListService.updateMarkedAmount(markedAmount, productOfListId);
	}

}
