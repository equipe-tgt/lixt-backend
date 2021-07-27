package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;
import br.com.ifsp.pi.lixt.data.business.itemofpurchase.ItemOfPurchase;
import br.com.ifsp.pi.lixt.dto.ItemOfPurchaseDto;

public abstract class ItemOfPurchaseMapper {

	public static ItemOfPurchaseDto entityToDto(ItemOfPurchase entity) {

		if(Objects.isNull(entity))
			return null;
		
		return ItemOfPurchaseDto.builder()
				.id(entity.getId())
				.productId(entity.getProductId())
				.purcharseListId(entity.getPurcharseListId())
				.name(entity.getName())
				.price(entity.getPrice())
				.amount(entity.getAmount())
				.measureType(entity.getMeasureType())
				.measureValue(entity.getMeasureValue())
				.product(ProductMapper.entityToDto(entity.getProduct()))
				.build();
	}
	
	public static ItemOfPurchase dtoToEntity(ItemOfPurchaseDto dto) {

		if(Objects.isNull(dto))
			return null;
		
		return ItemOfPurchase.builder()
				.id(dto.getId())
				.productId(dto.getProductId())
				.purcharseListId(dto.getPurcharseListId())
				.name(dto.getName())
				.price(dto.getPrice())
				.amount(dto.getAmount())
				.measureType(dto.getMeasureType())
				.measureValue(dto.getMeasureValue())
				.product(ProductMapper.dtoToEntity(dto.getProduct()))
				.build();
	}
	
}
