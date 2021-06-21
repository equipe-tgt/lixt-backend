package br.com.ifsp.pi.lixt.data.business.productOfList;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductOfListService {
	
	private final ProductOfListRepository productOfListRepository;
	
	public ProductOfList save(ProductOfList productOfLists) {
		return this.productOfListRepository.save(productOfLists);
	}
	
	public List<ProductOfList> saveAll(List<ProductOfList> productOfLists) {
		return (List<ProductOfList>) this.productOfListRepository.saveAll(productOfLists);
	}

}
