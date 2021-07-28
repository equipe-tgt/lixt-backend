package br.com.ifsp.pi.lixt.data.business.itemofpurchase;

import java.util.List;

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
	
	public List<ItemOfPurchase> saveAll(List<ItemOfPurchase> itemsOfPurchase) {
		return (List<ItemOfPurchase>) this.itemOfPurchaseRepository.saveAll(itemsOfPurchase);
	}
	
	public void deleteById(Long id) {
		this.itemOfPurchaseRepository.deleteById(id);
	}

}
