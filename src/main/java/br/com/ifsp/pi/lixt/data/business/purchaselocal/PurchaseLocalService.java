package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseLocalService {
	
	private final PurchaseLocalRepository purchaseLocalRepository;
	
	private static final double DISTANCE_IN_METERS_NEAR = 10;
	
	public PurchaseLocal findById(Long id) {
		return this.purchaseLocalRepository.findById(id).orElse(null);
	}
	
	public PurchaseLocal save(PurchaseLocal purchaseLocal) {
		return this.purchaseLocalRepository.save(purchaseLocal);
	}
	
	public List<PurchaseLocal> saveAll(List<PurchaseLocal> purchasesLocal) {
		return (List<PurchaseLocal>) this.purchaseLocalRepository.saveAll(purchasesLocal);
	}
	
	public void deleteById(Long id) {
		this.purchaseLocalRepository.deleteById(id);
	}
	
	public Integer updateNamePurchaseLocal(Long id, String name) {
		return this.purchaseLocalRepository.updateNamePurchaseLocal(id, name);
	}
	
	public List<PurchaseLocal> findPurchasesLocalNear(Point localization) {
		return this.purchaseLocalRepository.findPurchasesLocalNear(localization, DISTANCE_IN_METERS_NEAR);
	}

}
