package br.com.ifsp.pi.lixt.facade;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.list.ListOfItems;
import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.utils.exceptions.ForbiddenException;
import br.com.ifsp.pi.lixt.utils.exceptions.PrecoditionUpdateFailedException;
import br.com.ifsp.pi.lixt.utils.security.Users;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.ValidatorAccess;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListOfItemsFacade {

	private final ListOfItemsService listOfItemsService;
	
	public ListOfItems findById(Long id) {
		
		ListOfItems listOfItems = this.listOfItemsService.findById(id);
		
		if(!ValidatorAccess.canAcces(listOfItems.getOwnerId()))
			throw new ForbiddenException();
		
		return listOfItems;
	}
	
	public ListOfItems save(ListOfItems list) {
		list.setOwnerId(Users.getUserId());
		return this.listOfItemsService.save(list);
	}
	
	public ListOfItems update(ListOfItems list, Long id) throws PrecoditionUpdateFailedException {
		
		if(!list.getId().equals(id))
			throw new PrecoditionUpdateFailedException("Erro ao atualizar lista");
		
//		Long ownerIdList = this.listOfItemsService.findOwnerIdByListMemberId(id);
//		
//		if(!ValidatorAccess.canAcces(ownerIdList))
//			throw new ForbiddenException();
		
		return this.listOfItemsService.save(list);
	}
	
	public void deleteById(Long id) {
		
//		Long ownerIdList = this.listOfItemsService.findOwnerIdByListMemberId(id);
//		
//		if(!ValidatorAccess.canAcces(ownerIdList))
//			throw new ForbiddenException();
		
		this.listOfItemsService.deleteById(id);
	}

	public List<ListOfItems> findUserLists() {
		return this.listOfItemsService.findUserLists(Users.getUserId());
	}
}
