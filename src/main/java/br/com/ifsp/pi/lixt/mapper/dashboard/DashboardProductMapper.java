package br.com.ifsp.pi.lixt.mapper.dashboard;

import java.util.Objects;

import br.com.ifsp.pi.lixt.dashboard.response.IDashboardProduct;
import br.com.ifsp.pi.lixt.dto.dashboard.DashboardProductDto;

public class DashboardProductMapper {
	
	public static DashboardProductDto build(IDashboardProduct entity) {
		if(Objects.isNull(entity))
			return null;
		
		return DashboardProductDto.builder()
				.date(entity.getDate())
				.price(entity.getPrice())
				.name(entity.getName())
				.build();
	}

}
