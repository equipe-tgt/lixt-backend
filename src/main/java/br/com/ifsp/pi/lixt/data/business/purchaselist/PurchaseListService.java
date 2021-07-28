package br.com.ifsp.pi.lixt.data.business.purchaselist;

import java.util.List;

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
	
	public List<PurchaseList> saveAll(List<PurchaseList> purchasesList) {
		return (List<PurchaseList>) this.purchaseListRepository.saveAll(purchasesList);
	}
	
	public void deleteById(Long id) {
		this.purchaseListRepository.deleteById(id);
	}
}
