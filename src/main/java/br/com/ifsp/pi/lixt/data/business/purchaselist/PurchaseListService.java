package br.com.ifsp.pi.lixt.data.business.purchaselist;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseListService {

	private final PurchaseListRepository purchaseListRepository;
		
	public PurchaseList findById(Long id) {
		return this.purchaseListRepository.findById(id).orElse(null);
	}
	
	public PurchaseList save(PurchaseList purchaseList) {
		return this.purchaseListRepository.save(purchaseList);
	}
	
	public void deleteById(Long id) {
		this.purchaseListRepository.deleteById(id);
	}
}
