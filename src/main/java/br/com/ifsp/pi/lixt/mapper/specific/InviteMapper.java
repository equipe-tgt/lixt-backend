package br.com.ifsp.pi.lixt.mapper.specific;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembers;
import br.com.ifsp.pi.lixt.dto.specific.InviteDto;

public abstract class InviteMapper {
	
	private InviteMapper() {}

	public static InviteDto entityToDto(ListMembers entity) {

		if(Objects.isNull(entity))
			return null;
		
		return InviteDto.builder()
				.id(entity.getId())
				.nameList(entity.getList().getNameList())
				.description(entity.getList().getDescription())
				.statusListMember(entity.getStatusListMember())
				.userInvited(entity.getUser().getName())
				.userWhoInvite(entity.getList().getUser().getName())
				.build();
	}
	
}
