package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseLocalService {
	
	private final PurchaseLocalRepository purchaseLocalRepository;
	private final PurchaseLocalSpecRepository purchaseLocalSpecRepository;
	
	private static final double DISTANCE_IN_METERS_NEAR = 10;
	
	public PurchaseLocal findById(Long id) {
		return this.purchaseLocalRepository.findById(id).orElse(null);
	}
	
	public PurchaseLocal save(PurchaseLocal purchaseLocal) {
		return this.purchaseLocalRepository.save(purchaseLocal);
	}
	
	public void deleteById(Long id) {
		this.purchaseLocalRepository.deleteById(id);
	}
	
	public Integer updateNamePurchaseLocal(Long id, String name) {
		return this.purchaseLocalRepository.updateNamePurchaseLocal(id, name);
	}
	
	public List<PurchaseLocal> findPurchasesLocalNear(PurchaseLocal purchaseLocal) {
		return this.purchaseLocalSpecRepository.findPurchasesLocalNear(purchaseLocal, DISTANCE_IN_METERS_NEAR);
	}

}
