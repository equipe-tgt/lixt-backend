package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantior;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints na classe ProductController")
public class ProductControllerTest {
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryController categoryController;
	
	private CategoryDto category;
	
	private Product product;
	
	@BeforeAll
	void createProducts() {
		category = categoryController.save(CategoryDto.builder().name("alimento").build());
		
		product = ProductDtoInstantior.createProduct("Arroz", category, MeasureType.KG, 5);
		
		this.productService.save(product);
	}
	
	@Test
	@DisplayName("Salvar um produto")
	void saveProduct() {
		assertThat(productController.save( ProductMapper.entityToDto(product) ).getName())
			.isEqualTo(ProductMapper.entityToDto(product).getName());
	}
	
	@Test
	@DisplayName("Busca de produtos cadastrados pelo usuário ou cujo user ID é null")
	void searchProduct() {
		assertThat(productController.findByName("Arroz").stream().filter(p -> p.getId() == product.getId()).count())
			.isEqualTo(1);
	}
	
	
	@AfterAll
	void deleteProducts() {
		this.productController.deleteById(product.getId());
		this.categoryController.deleteById(category.getId());
	}
}
