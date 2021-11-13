package br.com.ifsp.pi.lixt.dto;

import io.swagger.annotations.ApiModelProperty;
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
public class UserDto {

	@ApiModelProperty(hidden = true)
	private Long id;

	private String name;
	
	private String username;

	private String email;
	
	private String password;

	private boolean globalCommentsChronOrder;

	private boolean olderCommentsFirst;

}
