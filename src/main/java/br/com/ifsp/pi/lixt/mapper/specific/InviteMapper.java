package br.com.ifsp.pi.lixt.mapper.specific;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembers;
import br.com.ifsp.pi.lixt.dto.specific.InviteDto;

public class InviteMapper {

	public static InviteDto entityToDto(ListMembers entity) {

		if(Objects.isNull(entity))
			return null;
		
		return InviteDto.builder()
				.nameList(entity.getList().getNameList())
				.description(entity.getList().getDescription())
				.statusListMember(entity.getStatusListMember())
				.userInvited(entity.getUser().getName())
				.userWhoInvite(entity.getUser().getName())
				.build();
	}
	
}
