package br.com.ifsp.pi.lixt.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.dashboard.request.DashboardCategoryFilter;
import br.com.ifsp.pi.lixt.dashboard.response.IDashboardCategory;
import br.com.ifsp.pi.lixt.data.business.purchase.PurchaseRepository;
import br.com.ifsp.pi.lixt.utils.security.Users;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {
	
	private final PurchaseRepository purchaseRepository;
	
	public List<IDashboardCategory> findDashboardCategory(DashboardCategoryFilter filter) {
		return this.purchaseRepository.findDashboardCategory(Users.getUserId(), filter);
	}

}
