package br.com.ifsp.pi.lixt.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.ifsp.pi.lixt.utils.conversion.date.LocalDateTimeDeserializer;
import br.com.ifsp.pi.lixt.utils.conversion.date.LocalDateTimeSerializer;
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
		
	private Long purchaseLocalId;
	
	private BigDecimal purchasePrice;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime purchaseDate;
	
	private PurchaseLocalDto purchaseLocal;

	private List<PurchaseListDto> purchaseLists;
	
}
