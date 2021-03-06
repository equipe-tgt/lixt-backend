package br.com.ifsp.pi.lixt.dto;

import java.math.BigDecimal;

import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
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
public class ProductOfListDto {
	
	private Long id;
	
	private Long productId;
	
	private Long listId;
	
	private Long assignedUserId;
	
	private Long userWhoMarkedId;
	
	private String name;
	
	private Boolean isMarked;

	private Integer plannedAmount;

	private Integer markedAmount;
	
	private BigDecimal price;
	
	private BigDecimal measureValue;
	
	private MeasureType measureType;

	@ApiModelProperty(hidden = true)
	private ProductDto product;
	
	private Integer amountComment;
	
}
