package br.com.ifsp.pi.lixt.data.business.purchaselist;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseListService {

	private final PurchaseListRepository purchaseListRepository;

	public List<PurchaseList> saveAll(List<PurchaseList> purchasesList) {
		return (List<PurchaseList>) this.purchaseListRepository.saveAll(purchasesList);
	}
	
}
