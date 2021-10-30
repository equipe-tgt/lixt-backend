package br.com.ifsp.pi.lixt.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.dashboard.request.DashboardCategoryFilter;
import br.com.ifsp.pi.lixt.dashboard.request.DashboardTimeFilter;
import br.com.ifsp.pi.lixt.dashboard.response.IDashboardCategory;
import br.com.ifsp.pi.lixt.dashboard.response.IDashboardTime;
import br.com.ifsp.pi.lixt.data.business.purchase.PurchaseRepository;
import br.com.ifsp.pi.lixt.data.business.purchase.PurchaseSpecRepository;
import br.com.ifsp.pi.lixt.utils.security.Users;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {
	
	private final PurchaseSpecRepository purchaseSpecRepository;
	private final PurchaseRepository purchaseRepository;
	
	public List<IDashboardTime> findDashboardTime(DashboardTimeFilter filter) {
		return this.purchaseSpecRepository.findDashboardTime(Users.getUserId(), filter);
	}
	
	public List<IDashboardCategory> findDashboardCategory(DashboardCategoryFilter filter) {
		return this.purchaseRepository.findDashboardCategory(Users.getUserId(), filter);
	}

}
