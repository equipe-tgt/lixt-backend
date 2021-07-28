package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;
import java.util.stream.Collectors;

import br.com.ifsp.pi.lixt.data.business.purchaselist.PurchaseList;
import br.com.ifsp.pi.lixt.dto.PurchaseListDto;

public abstract class PurchaseListMapper {
	
	private PurchaseListMapper() {}
	
	public static PurchaseList dtoToEntity(PurchaseListDto dto) {
		
		if(Objects.isNull(dto)) 
			return null;
		
		return PurchaseList.builder()
				.id(dto.getId())
				.purchaseId(dto.getPurchaseId())
				.listId(dto.getListId())
				.partialPurchasePrice(dto.getPartialPurchasePrice())
				.itemsOfPurchase(Objects.isNull(dto.getItemsOfPurchase()) ? null : dto.getItemsOfPurchase().stream().map(ItemOfPurchaseMapper::dtoToEntity).collect(Collectors.toList()))
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
				.itemsOfPurchase(Objects.isNull(entity.getItemsOfPurchase()) ? null : entity.getItemsOfPurchase().stream().map(ItemOfPurchaseMapper::entityToDto).collect(Collectors.toList()))
				.build();
	}

}
