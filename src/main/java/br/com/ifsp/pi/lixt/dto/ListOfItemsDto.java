package br.com.ifsp.pi.lixt.dto;

import java.util.List;

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

	private Long id;
	
	private String nameList;
	
	private Long ownerId;
	
	private String owner;
	
	private String description;
	
	private List<ProductOfListDto> productsOfList;
	
	private List<ListMembersDto> listMembers;
}
