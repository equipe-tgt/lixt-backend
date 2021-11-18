package br.com.ifsp.pi.lixt.dashboard.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCategoryFilter {
	
	private LocalDateTime maxDate;
	
	private LocalDateTime minDate;
	
	private String category;

}
