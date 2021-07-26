package br.com.ifsp.pi.lixt.data.business.itemofpurchase;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemOfPurchaseService {
	
	private final ItemOfPurchaseRepository itemOfPurchaseRepository;
	
	public ItemOfPurchase findById(Long id) {
		return this.itemOfPurchaseRepository.findById(id).orElse(null);
	}
	
	public ItemOfPurchase save(ItemOfPurchase itemOfPurchase) {
		return this.itemOfPurchaseRepository.save(itemOfPurchase);
	}
	
	public void deleteById(Long id) {
		this.itemOfPurchaseRepository.deleteById(id);
	}

}
