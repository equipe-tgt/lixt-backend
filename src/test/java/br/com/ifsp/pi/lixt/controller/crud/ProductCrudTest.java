package br.com.ifsp.pi.lixt.controller.crud;

import br.com.ifsp.pi.lixt.controller.CategoryController;
import br.com.ifsp.pi.lixt.controller.ProductController;
import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantiator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

		listProducts.add(ProductDtoInstantiator.createProduct("Arroz", category, MeasureType.KG, 5));
		listProducts.add(ProductDtoInstantiator.createProduct("Feijão", category, MeasureType.KG, 2));

		listProducts = this.productService.saveAll(listProducts);
	}

	@Test
	@DisplayName("Testar endpoints de busca de produtos")
	void validateProducts() {

		listProducts.stream().map(Product::getId).forEach(id -> {
			assertThat(this.productController.findById(id).getCategory()).isNotNull();
			assertThat(this.productController.findById(id).getCategory().getName()).isNotNull();
			assertThat(this.productController.findById(id).getCategory().getId()).isEqualTo(category.getId());
		});
	}

	@AfterAll
	void deleteProducts() {
		listProducts.stream().map(Product::getId).forEach(id -> this.productController.deleteById(id));
		this.categoryController.deleteById(category.getId());
	}

}
