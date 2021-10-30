package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.purchase.Purchase;
import br.com.ifsp.pi.lixt.dto.PurchaseDto;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import br.com.ifsp.pi.lixt.utils.mapper.Mapper;

public abstract class PurchaseMapper extends Mapper {
	
	private PurchaseMapper() {}
	
	public static Purchase dtoToEntity(PurchaseDto dto) throws PreconditionFailedException {
		
		if(Objects.isNull(dto)) 
			return null;
		
		return Purchase.builder()
				.id(dto.getId())
				.userId(dto.getUserId())
				.purchasePrice(dto.getPurchasePrice())
				.purchaseLocalId(dto.getPurchaseLocalId())
				.purchaseDate(dto.getPurchaseDate())
				.purchaseLocal(PurchaseLocalMapper.dtoToEntity(dto.getPurchaseLocal()))
				.purchaseLists(map(dto.getPurchaseLists(), PurchaseListMapper::dtoToEntity))
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
				.purchasePrice(entity.getPurchasePrice())
				.purchaseLocal(map(entity.getPurchaseLocal(), PurchaseLocalMapper::entityToDto))
				.purchaseLists(map(entity.getPurchaseLists(), PurchaseListMapper::entityToDto))
				.build();
	}

}
