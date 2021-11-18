package br.com.ifsp.pi.lixt.dashboard.request;

import java.time.LocalDateTime;

import br.com.ifsp.pi.lixt.dashboard.UnityTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardTimeFilter {
	
	private Long listId;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private UnityTime unityTime;

}
