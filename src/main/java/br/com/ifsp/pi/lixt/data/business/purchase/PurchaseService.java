package br.com.ifsp.pi.lixt.data.business.purchase;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseRepository purchaseRepository;
	
	public Purchase findById(Long id) {
		return this.purchaseRepository.findById(id).orElse(null);
	}
	
	public Purchase save(Purchase purchase) {
		return this.purchaseRepository.save(purchase);
	}
	
	public void deleteById(Long id) {
		this.purchaseRepository.deleteById(id);
	}
	
	public List<Purchase> findUserPurchases(Long userId) {
		return this.purchaseRepository.findUserPurchases(userId);
	}
	
}
