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
				.purchaseLocalId(dto.getPurchaseLocalId())
				.purchaseDate(dto.getPurchaseDate())
				.purchaseLocal(PurchaseLocalMapper.dtoToEntity(dto.getPurchaseLocal()))
				.purchaseLists(Objects.isNull(dto.getPurchaseLists()) ? null : dto.getPurchaseLists().stream().map(PurchaseListMapper::dtoToEntity).collect(Collectors.toList()))
				.build();
	}
	
	public static PurchaseDto entityToDto(Purchase entity) {
		
		if(Objects.isNull(entity)) 
			return null;
		
		return PurchaseDto.builder()
				.id(entity.getId())
				.userId(entity.getUserId())
				.purchaseLocalId(entity.getPurchaseLocalId())
				.purchaseDate(entity.getPurchaseDate())
				.purchaseLocal(PurchaseLocalMapper.entityToDto(entity.getPurchaseLocal()))
				.purchaseLists(Objects.isNull(entity.getPurchaseLists()) ? null : entity.getPurchaseLists().stream().map(PurchaseListMapper::entityToDto).collect(Collectors.toList()))
				.build();
	}

}
