package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembers;
import br.com.ifsp.pi.lixt.dto.ListMembersDto;
import br.com.ifsp.pi.lixt.utils.mapper.Mapper;

public abstract class ListMembersMapper extends Mapper {
	
	private ListMembersMapper() {}
	
	public static ListMembersDto entityToDto(ListMembers entity) {

		if(Objects.isNull(entity))
			return null;
		
		return ListMembersDto.builder()
				.id(entity.getId())
				.userId(entity.getUserId())
				.listId(entity.getListId())
				.statusListMember(entity.getStatusListMember())
				.user(map(entity.getUser(), UserMapper::entityToDto))
				.build();
	}
	
	public static ListMembers dtoToEntity(ListMembersDto dto) {

		if(Objects.isNull(dto))
			return null;
		
		return ListMembers.builder()
				.id(dto.getId())
				.userId(dto.getUserId())
				.listId(dto.getListId())
				.statusListMember(dto.getStatusListMember())
				.user(map(dto.getUser(), UserMapper::dtoToEntity))
				.build();
	}

}
