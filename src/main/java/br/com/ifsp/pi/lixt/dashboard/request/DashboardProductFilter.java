package br.com.ifsp.pi.lixt.dashboard.request;

import java.time.LocalDateTime;

import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardProductFilter {

	private String name;
	
	private String brand;
	
	private Long purchaseLocalId;
	
	private MeasureType measureType;
	
	private LocalDateTime maxDate;
	
	private LocalDateTime minDate;
}
