package br.com.ifsp.pi.lixt.dto;

import br.com.ifsp.pi.lixt.data.enumeration.StatusListMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ListMembersDto {

	private Long id;

	private Long userId;
	
	private Long listId;
	
	private StatusListMember statusListMember;
	
	private UserDto user;
}
