package br.com.ifsp.pi.lixt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.dashboard.DashboardService;
import br.com.ifsp.pi.lixt.dashboard.request.DashboardCategoryFilter;
import br.com.ifsp.pi.lixt.dto.dashboard.DashboardCategoryDto;
import br.com.ifsp.pi.lixt.mapper.dashboard.DashboardCategoryMapper;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de dashboard e histórico")
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
	
	private final DashboardService dashboardService;
	
	@PostMapping("/category")
	public List<DashboardCategoryDto> findDashboardCategory(@RequestBody DashboardCategoryFilter filter) {
		return this.dashboardService.findDashboardCategory(filter).stream().map(DashboardCategoryMapper::build).collect(Collectors.toList());
	}

}
