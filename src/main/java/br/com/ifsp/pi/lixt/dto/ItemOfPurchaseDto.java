package br.com.ifsp.pi.lixt.dto;

import java.math.BigDecimal;

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
public class ItemOfPurchaseDto {

	private Long id;
		
	private Long productId;
	
	private Long productOfListId;
	
	private Long purcharseListId;
	
	private String name;
	
	private Integer amount;
	
	private BigDecimal price;
	
	private BigDecimal measureValue;
	
	private MeasureType measureType;
	
	private ProductDto product;
}
