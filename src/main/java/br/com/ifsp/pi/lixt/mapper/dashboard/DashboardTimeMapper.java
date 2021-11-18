package br.com.ifsp.pi.lixt.mapper.dashboard;

import java.util.Objects;

import br.com.ifsp.pi.lixt.dashboard.response.IDashboardTime;
import br.com.ifsp.pi.lixt.dto.dashboard.DashboardTimeDto;

public class DashboardTimeMapper {
	
	public static DashboardTimeDto build(IDashboardTime entity) {
		if(Objects.isNull(entity))
			return null;

		return DashboardTimeDto.builder()
				.time(entity.getTime())
				.price(entity.getPrice())
				.build();
	}

}
