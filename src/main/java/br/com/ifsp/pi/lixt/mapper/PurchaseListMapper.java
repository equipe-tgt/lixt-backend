package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.purchaselist.PurchaseList;
import br.com.ifsp.pi.lixt.dto.PurchaseListDto;
import br.com.ifsp.pi.lixt.utils.mapper.Mapper;

public abstract class PurchaseListMapper extends Mapper {
	
	private PurchaseListMapper() {}
	
	public static PurchaseList dtoToEntity(PurchaseListDto dto) {
		
		if(Objects.isNull(dto) || dto.getItemsOfPurchase().isEmpty()) 
			return null;
		
		return PurchaseList.builder()
				.id(dto.getId())
				.purchaseId(dto.getPurchaseId())
				.listId(dto.getListId())
				.partialPurchasePrice(dto.getPartialPurchasePrice())
				.itemsOfPurchase(map(dto.getItemsOfPurchase(), ItemOfPurchaseMapper::dtoToEntity))
				.build();
	}
	
	public static PurchaseListDto entityToDto(PurchaseList entity) {
		
		if(Objects.isNull(entity)) 
			return null;
		
		return PurchaseListDto.builder()
				.id(entity.getId())
				.purchaseId(entity.getPurchaseId())
				.listId(entity.getListId())
				.partialPurchasePrice(entity.getPartialPurchasePrice())
				.itemsOfPurchase(map(entity.getItemsOfPurchase(), ItemOfPurchaseMapper::entityToDto))
				.build();
	}

}
