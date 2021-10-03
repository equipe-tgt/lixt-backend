package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfList;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.utils.mapper.Mapper;

public abstract class ProductOfListMapper extends Mapper {
	
	private ProductOfListMapper() {}

	public static ProductOfListDto entityToDto(ProductOfList entity) {

		if(Objects.isNull(entity))
			return null;
		
		return ProductOfListDto.builder()
				.id(entity.getId())
				.productId(entity.getProductId())
				.listId(entity.getListId())
				.assignedUserId(entity.getAssignedUserId())
				.userWhoMarkedId(entity.getUserWhoMarkedId())
				.name(entity.getName())
				.isMarked(entity.getIsMarked())
				.price(entity.getPrice())
				.plannedAmount(entity.getPlannedAmount())
				.markedAmount(entity.getMarkedAmount())
				.measureType(entity.getMeasureType())
				.measureValue(entity.getMeasureValue())
				.product(map(entity.getProduct(), ProductMapper::entityToDto))
				.amountComment(Objects.isNull(entity.getComments()) ? null : entity.getComments().size())
				.build();
	}
	
	public static ProductOfList dtoToEntity(ProductOfListDto dto) {

		if(Objects.isNull(dto))
			return null;
		
		return ProductOfList.builder()
				.id(dto.getId())
				.productId(dto.getProductId())
				.listId(dto.getListId())
				.assignedUserId(dto.getAssignedUserId())
				.userWhoMarkedId(dto.getUserWhoMarkedId())
				.name(dto.getName())
				.isMarked(dto.getIsMarked())
				.price(dto.getPrice())
				.plannedAmount(dto.getPlannedAmount())
				.markedAmount(dto.getMarkedAmount())
				.measureType(dto.getMeasureType())
				.measureValue(dto.getMeasureValue())
				.product(map(dto.getProduct(), ProductMapper::dtoToEntity))
				.build();
	}
}
