package br.com.ifsp.pi.lixt.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
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
public class ProductOfListDto {
	
	private Long id;
	
	private Long productId;
	
	private Long listId;
	
	private Long assignedUserId;
	
	private Long userWhoMarkedId;
	
	private String name;
	
	private Boolean isMarked;

	private Integer amount;
	
	private BigDecimal price;
	
	private BigDecimal measureValue;
	
	private MeasureType measureType;
	
	private ProductDto product;
	
	private List<CommentDto> comments;

}
