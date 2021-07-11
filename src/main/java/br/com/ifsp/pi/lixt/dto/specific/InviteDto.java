package br.com.ifsp.pi.lixt.dto.specific;

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
public class InviteDto {

	private String nameList;
	
	private String description;
	
	private String userInvited;
	
	private String userWhoInvite;
	
	private StatusListMember statusListMember;
	
}
