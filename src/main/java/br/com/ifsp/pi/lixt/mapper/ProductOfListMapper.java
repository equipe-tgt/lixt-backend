package br.com.ifsp.pi.lixt.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.com.ifsp.pi.lixt.data.business.comment.Comment;
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
				.amountComment(extractAmountCommentsInList(entity.getComments()) + extractAmountGlobalCommentsInList(entity))
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
	
	private static Integer extractAmountCommentsInList(List<Comment> comments) {
		if(Objects.isNull(comments))
			return 0;
		return comments.size();
	}
	
	private static Integer extractAmountGlobalCommentsInList(ProductOfList productOfList) {
		if(Objects.isNull(productOfList.getProduct()) || Objects.isNull(productOfList.getProduct().getGlobalComments()))
			return 0;
		return productOfList.getProduct().getGlobalComments()
				.stream().filter(e -> e.getUserId() == productOfList.getListOfItems().getOwnerId()).collect(Collectors.toList()).size();
	}
}
