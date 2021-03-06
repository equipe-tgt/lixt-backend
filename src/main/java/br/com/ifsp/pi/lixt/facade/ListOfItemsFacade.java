package br.com.ifsp.pi.lixt.facade;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.list.ListOfItems;
import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfListService;
import br.com.ifsp.pi.lixt.utils.exceptions.ForbiddenException;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import br.com.ifsp.pi.lixt.utils.security.Users;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.ValidatorAccess;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListOfItemsFacade {

	private final ProductOfListService productOfListService;
	private final ListOfItemsService listOfItemsService;
	
	public ListOfItems findById(Long id) {
		
		var listOfItems = this.listOfItemsService.findById(id);
		List<Long> membersIds = this.listOfItemsService.findMembersIdsByListId(id);

		if(!(ValidatorAccess.canAcces(listOfItems.getOwnerId()) || ValidatorAccess.canAcces(membersIds)))
			throw new ForbiddenException();
		
		return listOfItems;
	}
	
	public ListOfItems save(ListOfItems list) {
		list.setOwnerId(Users.getUserId());
		return this.listOfItemsService.save(list);
	}
	
	public ListOfItems update(ListOfItems list, Long id) throws PreconditionFailedException {
		
		if(!list.getId().equals(id))
			throw new PreconditionFailedException("Erro ao atualizar lista");
		
		Long ownerIdList = this.listOfItemsService.findOwnerIdByListId(id);
		
		if(!ValidatorAccess.canAcces(ownerIdList))
			throw new ForbiddenException();
		
		return this.listOfItemsService.save(list);
	}
	
	public void deleteById(Long id) {
		
		Long ownerIdList = this.listOfItemsService.findOwnerIdByListId(id);
		
		if(!ValidatorAccess.canAcces(ownerIdList))
			throw new ForbiddenException();
		
		this.listOfItemsService.deleteById(id);
	}

	public List<ListOfItems> findUserLists() {
		return this.listOfItemsService.findUserLists(Users.getUserId());
	}
	
	public Integer cleanUserIdAtProductsOfList(Long listId) {
		Long ownerIdList = this.listOfItemsService.findOwnerIdByListId(listId);
		
		if(!ValidatorAccess.canAcces(ownerIdList))
			throw new ForbiddenException();
		
		return this.productOfListService.cleanUserIdAtProductsOfList(listId);
	}
}
