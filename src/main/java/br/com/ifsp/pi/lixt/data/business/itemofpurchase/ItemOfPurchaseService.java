package br.com.ifsp.pi.lixt.data.business.itemofpurchase;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemOfPurchaseService {
	
	private final ItemOfPurchaseRepository itemOfPurchaseRepository;
	
	public List<ItemOfPurchase> saveAll(List<ItemOfPurchase> itemsOfPurchase) {
		return (List<ItemOfPurchase>) this.itemOfPurchaseRepository.saveAll(itemsOfPurchase);
	}

}
