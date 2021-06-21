package br.com.ifsp.pi.lixt.data.business.product;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public List<Product> saveAll(List<Product> products) {
		return (List<Product>) this.productRepository.saveAll(products);
	}

}
