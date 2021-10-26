package br.com.ifsp.pi.lixt.mapper.dashboard;

import java.util.Objects;

import br.com.ifsp.pi.lixt.dashboard.response.IDashboardCategory;
import br.com.ifsp.pi.lixt.dto.dashboard.DashboardCategoryDto;

public class DashboardCategoryMapper {
	
	public static DashboardCategoryDto build(IDashboardCategory entity) {
		if(Objects.isNull(entity))
			return null;

		return DashboardCategoryDto.builder()
				.month(entity.getMonth())
				.price(entity.getPrice())
				.build();
	}

}
