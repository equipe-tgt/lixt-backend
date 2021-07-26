package br.com.ifsp.pi.lixt.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class PurchaseDto {
	
	private Long id;
	
	private Long userId;
	
	private Long listId;
	
	private Long purchaseLocalId;
	
	private BigDecimal purchasePrice;
	
	private LocalDateTime purchaseDate;
	
	private List<ItemOfPurchaseDto> itemsOfPurchase;
	
	private PurchaseLocalDto purchaseLocal;

}
