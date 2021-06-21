package br.com.ifsp.pi.lixt.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.data.business.category.Category;
import br.com.ifsp.pi.lixt.data.business.category.CategoryService;
import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;

@SpringBootTest
public class ProductData {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Test
	public void createProductData() {
		
		Category category = categoryService.save(Category.builder().name("alimento").build());
		
		List<Product> products = new ArrayList<>();

		products.add(
				Product.builder()
					.name("arroz")
					.price(new BigDecimal(32))
					.categoryId(category.getId())
					.category(category)
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(5))
					.build()
		);
		
		products.add(
				Product.builder()
					.name("feijão")
					.price(new BigDecimal(20))
					.categoryId(category.getId())
					.category(category)
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(1))
					.build()
		);
		
		productService.saveAll(products);
	}

}
