package br.com.ifsp.pi.lixt.dto;

import java.util.List;

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
public class ListOfItemsDto {

	@ApiModelProperty(hidden = true)
	private Long id;
	
	private String nameList;
	
	private Long ownerId;

	@ApiModelProperty(hidden = true)
	private String owner;
	
	private String description;

	@ApiModelProperty(hidden = true)
	private List<ProductOfListDto> productsOfList;

	@ApiModelProperty(hidden = true)
	private List<ListMembersDto> listMembers;
}
