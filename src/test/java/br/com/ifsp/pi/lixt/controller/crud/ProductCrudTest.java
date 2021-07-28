package br.com.ifsp.pi.lixt.controller.crud;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.controller.CategoryController;
import br.com.ifsp.pi.lixt.controller.ProductController;
import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantior;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints de produtos e categorias")
class ProductCrudTest {
	
	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private ProductService productService;
	
	private List<Product> listProducts = new ArrayList<>();
	private CategoryDto category;
	
	@BeforeAll
	void createProducts() {
		category = categoryController.save(CategoryDto.builder().name("alimento").build());
				
		listProducts.add(ProductDtoInstantior.createProduct("Arroz", category, MeasureType.KG, 5));
		listProducts.add(ProductDtoInstantior.createProduct("Feijão", category, MeasureType.KG, 2));
		
		listProducts = this.productService.saveAll(listProducts);
	}
	
	@Test
	@DisplayName("Testar endpoints de busca de produtos")
	void validateProducts() {

		listProducts.stream().map(element -> element.getId()).forEach(id -> {
			assertThat(this.productController.findById(id).getCategory()).isNotNull();
			assertThat(this.productController.findById(id).getCategory().getName()).isNotNull();
			assertThat(this.productController.findById(id).getCategory().getId()).isEqualTo(category.getId());			
		});
	}
	
	@AfterAll
	void deleteProducts() {
		listProducts.stream().map(element -> element.getId()).forEach(id -> this.productController.deleteById(id));
		this.categoryController.deleteById(category.getId());
	}

}
