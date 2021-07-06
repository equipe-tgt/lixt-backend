package br.com.ifsp.pi.lixt.facade;

import java.util.List;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfList;
import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfListService;
import br.com.ifsp.pi.lixt.utils.exceptions.ForbiddenException;
import br.com.ifsp.pi.lixt.utils.exceptions.PrecoditionUpdateFailedException;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.ValidatorAccess;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductOfListFacade {
	
	private final ProductOfListService productOfListService;
	private final ListOfItemsService listOfItemsService;
	
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
	
	public List<ProductOfList> saveAll(List<ProductOfList> productsOfList) {
		return this.productOfListService.saveAll(productsOfList);
	}
	
	public ProductOfList update(ProductOfList productOfList, Long id) throws PrecoditionUpdateFailedException {
		
		if(!productOfList.getId().equals(id))
			throw new PrecoditionUpdateFailedException("Erro ao atualizar produto da lista");
		
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

}
