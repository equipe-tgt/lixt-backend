package br.com.ifsp.pi.lixt.data.business.product;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public Product findById(Long id) {
		return this.productRepository.findById(id).orElse(null);
	}
	
	public Product save(Product product) {
		return this.productRepository.save(product);
	}
	
	public List<Product> saveAll(List<Product> products) {
		return (List<Product>) this.productRepository.saveAll(products);
	}
	
	public void deleteById(Long id) {
		this.productRepository.deleteById(id);
	}
	
	public Product findByName(String name) {
		return this.productRepository.findByName(name);
	}

}
