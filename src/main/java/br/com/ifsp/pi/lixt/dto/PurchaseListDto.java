package br.com.ifsp.pi.lixt.dto;

import java.math.BigDecimal;
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
public class PurchaseListDto {

	private Long id;
	
	private Long purchaseId;
	
	private Long listId;
	
	private BigDecimal partialPurchasePrice;

	private List<ItemOfPurchaseDto> itemsOfPurchase;
}
