package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantior;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionUpdateFailedException;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints na classe ProductController")
@TestMethodOrder(OrderAnnotation.class)
class ProductControllerTest {
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private CategoryController categoryController;
	
	private CategoryDto category;
	
	private ProductDto product;
	
	@BeforeAll
	void createProducts() {
		category = categoryController.save(CategoryDto.builder().name("alimento").build());
		product = this.productController.save(ProductMapper.entityToDto(ProductDtoInstantior.createProduct("Arroz", category, MeasureType.KG, 5)));
	}
	
//	@Test
//	@DisplayName("Busca de produtos cadastrados pelo usuário ou cujo user ID é null")
//	@Order(1)
//	void searchProduct() {
//		assertThat(productController.findByName("rro").stream().filter(p -> p.getId() == product.getId()).count()).isEqualTo(1);
//	}
	
	@Test
	@DisplayName("Atualizar um produto")
	@Order(2)
	void updateProduct() {
		product.setName("Banana");
		ProductDto productUpdated = productController.update(product, product.getId());
		
		assertAll(
				() -> assertThat(productUpdated.getId()).isEqualTo(product.getId()),
				() -> assertThat(productUpdated.getName()).isEqualTo("Banana"),
				() -> assertThat(productController.findById(product.getId()).getName()).isEqualTo(productUpdated.getName())
		);
	}
	
	@Test
	@DisplayName("Atualizar produto com erro")
	@Order(3)
	void updateCategoryWithError() {
		product.setName("Maçã");
		assertThrows(PreconditionUpdateFailedException.class, () -> productController.update(product, 0l));
	}
	
	@AfterAll
	void deleteProducts() {
		this.productController.deleteById(product.getId());
		this.categoryController.deleteById(category.getId());
	}
}
