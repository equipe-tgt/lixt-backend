package br.com.ifsp.pi.lixt.dto.dashboard;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardProductDto {
	
	private LocalDateTime date;
	
	private BigDecimal price;
	
	private String name;

}
