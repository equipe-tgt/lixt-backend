package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;
import java.util.stream.Collectors;

import br.com.ifsp.pi.lixt.data.business.purchase.Purchase;
import br.com.ifsp.pi.lixt.dto.PurchaseDto;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;

public abstract class PurchaseMapper {
	
	public static Purchase dtoToEntity(PurchaseDto dto) throws PreconditionFailedException {
		
		if(Objects.isNull(dto)) 
			return null;
		
		return Purchase.builder()
				.id(dto.getId())
				.userId(dto.getUserId())
				.listId(dto.getListId())
				.purchaseLocalId(dto.getPurchaseLocalId())
				.purchaseDate(dto.getPurchaseDate())
				.purchaseLocal(PurchaseLocalMapper.dtoToEntity(dto.getPurchaseLocal()))
				.itemsOfPurchase(Objects.isNull(dto.getItemsOfPurchase()) ? null : dto.getItemsOfPurchase().stream().map(ItemOfPurchaseMapper::dtoToEntity).collect(Collectors.toList()))
				.build();
	}
	
	public static PurchaseDto entityToDto(Purchase entity) {
		
		if(Objects.isNull(entity)) 
			return null;
		
		return PurchaseDto.builder()
				.id(entity.getId())
				.userId(entity.getUserId())
				.listId(entity.getListId())
				.purchaseLocalId(entity.getPurchaseLocalId())
				.purchaseDate(entity.getPurchaseDate())
				.purchaseLocal(PurchaseLocalMapper.entityToDto(entity.getPurchaseLocal()))
				.itemsOfPurchase(Objects.isNull(entity.getItemsOfPurchase()) ? null : entity.getItemsOfPurchase().stream().map(ItemOfPurchaseMapper::entityToDto).collect(Collectors.toList()))
				.build();
	}

}
