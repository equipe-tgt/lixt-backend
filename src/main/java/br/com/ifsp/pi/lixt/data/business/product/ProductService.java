package br.com.ifsp.pi.lixt.data.business.product;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.integration.BarcodeService;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.utils.database.operations.Like;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	private final BarcodeService barcodeService;

	private static final int LENGTH_DATA_BY_DEFAULT = 12;
	private static final String SORT_DATA_BY_DEFAULT = "name";
	
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
	
	public List<Product> findByName(String name, Long userId) {
		return this.productRepository.findByName(
				Like.contains(name), 
				userId,
				PageRequest.of(0, LENGTH_DATA_BY_DEFAULT, Sort.by(SORT_DATA_BY_DEFAULT))
		);
	}
	
	public Product findByBarcode(String barcode) {
		var product = productRepository.findByBarcode(barcode);
		
		if(Objects.nonNull(product)) {
			return product;
		}
		
		Map<String, Object> productMap = this.barcodeService.getProductByBarcode(barcode);
		
		if(Objects.isNull(productMap))
			return null;
		
		return this.productRepository.save(ProductMapper.modelIntoApiParams(productMap));
	}

}
