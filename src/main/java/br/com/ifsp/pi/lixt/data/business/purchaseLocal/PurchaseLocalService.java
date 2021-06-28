package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseLocalService {
	
	private final PurchaseLocalRepository purchaseLocalRepository;
	
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

}
