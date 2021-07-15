package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;
import java.util.stream.Collectors;

import br.com.ifsp.pi.lixt.data.business.list.ListOfItems;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;

public abstract class ListOfItemsMapper {
	
	public static ListOfItemsDto entityToDto(ListOfItems entity) {

		if(Objects.isNull(entity))
			return null;
		
		return ListOfItemsDto.builder()
				.id(entity.getId())
				.nameList(entity.getNameList())
				.description(entity.getDescription())
				.ownerId(entity.getOwnerId())
				.owner(Objects.isNull(entity.getUser()) ? null : entity.getUser().getName())
				.productsOfList(Objects.isNull(entity.getProductsOfList()) ? null : entity.getProductsOfList().stream().map(ProductOfListMapper::entityToDto).collect(Collectors.toList()))
				.listMembers(Objects.isNull(entity.getListMembers()) ? null : entity.getListMembers().stream().map(ListMembersMapper::entityToDto).collect(Collectors.toList()))
				.build();
	}
	
	public static ListOfItems dtoToEntity(ListOfItemsDto dto) {

		if(Objects.isNull(dto))
			return null;
		
		return ListOfItems.builder()
				.id(dto.getId())
				.nameList(dto.getNameList())
				.description(dto.getDescription())
				.ownerId(dto.getOwnerId())
				.productsOfList(Objects.isNull(dto.getProductsOfList()) ? null : dto.getProductsOfList().stream().map(ProductOfListMapper::dtoToEntity).collect(Collectors.toList()))
				.listMembers(Objects.isNull(dto.getListMembers()) ? null : dto.getListMembers().stream().map(ListMembersMapper::dtoToEntity).collect(Collectors.toList()))
				.build();
	}

}
