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
public class ProductDto {

	private Long id;
	
	private String name;
	
	private BigDecimal price;	
	
	private Long userId;
	
	private Long categoryId;
	
	private String barcode;
	
	private BigDecimal measureValue;
	
	private MeasureType measureType;
	
	private CategoryDto category;

}
