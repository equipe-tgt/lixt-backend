package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.list.ListOfItems;
import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.utils.mapper.Mapper;

public abstract class ListOfItemsMapper extends Mapper {
	
	private ListOfItemsMapper() {}
	
	public static ListOfItemsDto entityToDto(ListOfItems entity) {

		if(Objects.isNull(entity))
			return null;
		
		return ListOfItemsDto.builder()
				.id(entity.getId())
				.nameList(entity.getNameList())
				.description(entity.getDescription())
				.ownerId(entity.getOwnerId())
				.owner(map(entity.getUser(), User::getName))
				.productsOfList(map(entity.getProductsOfList(), ProductOfListMapper::entityToDto))
				.listMembers(map(entity.getListMembers(), ListMembersMapper::entityToDto))
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
				.productsOfList(map(dto.getProductsOfList(), ProductOfListMapper::dtoToEntity))
				.listMembers(map(dto.getListMembers(), ListMembersMapper::dtoToEntity))
				.build();
	}

}
