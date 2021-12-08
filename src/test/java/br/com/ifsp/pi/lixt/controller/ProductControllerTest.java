package br.com.ifsp.pi.lixt.controller;

import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantiator;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
	void createProducts() throws SQLException {
		category = categoryController.save(CategoryDto.builder().name("alimento").build());
		ResponseEntity<Object> arroz = this.productController.save(ProductMapper.entityToDto(ProductDtoInstantiator.createProduct("Arroz", category, MeasureType.KG, 5)));
		product = (ProductDto) arroz.getBody();
	}

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
		assertThrows(PreconditionFailedException.class, () -> productController.update(product, 0L));
	}

	@Test
	@DisplayName("Tentar adicionar um produto com código de barras já cadastrado")
	@Order(4)
	void saveProductWithAlreadyExistBarcode() throws SQLException{
		ProductDto newProduct  = new ProductDto(null,
				"Pão de batata",
				null,
				category.getId(),
				"7891268400014",
				BigDecimal.valueOf(1),
				MeasureType.UNITY,
				category);

		ResponseEntity<Object> response = this.productController.save(newProduct);

		ProductDto registeredProduct = (ProductDto) response.getBody();

		assertThat(registeredProduct).isNotNull();
		assertEquals(registeredProduct.getName(), newProduct.getName());

		assertEquals(this.productController.save(newProduct).getStatusCode().value(), HttpStatus.CONFLICT.value());

		this.productController.deleteById(registeredProduct.getId());
	}

	@AfterAll
	void deleteProducts() {
		this.productController.deleteById(product.getId());
		this.categoryController.deleteById(category.getId());
	}
}
