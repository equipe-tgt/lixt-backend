package br.com.ifsp.pi.lixt.controller.crud;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.ProductDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints de produtos e categorias")
public class ProductCrudTest {
	
	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private ProductController productController;
	
	private List<ProductDto> listProducts;
	private CategoryDto category;
	
	@BeforeAll
	public void createProducts() {
		
		category = categoryController.save(CategoryDto.builder().name("alimento").build());
		listProducts = new ArrayList<>();
				
		listProducts.add(
				ProductDto.builder()
					.name("arroz")
					.categoryId(category.getId())
					.category(category)
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(5))
					.build()
		);
			
		listProducts.add(
				ProductDto.builder()
					.name("feijão")
					.categoryId(category.getId())
					.category(category)
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(1))
					.build()
		);
		
		listProducts = this.productController.saveAll(listProducts);
	}
	
	@Test
	@DisplayName("Testar endpoints de busca de produtos")
	public void validateProducts() {

		for(Long id : listProducts.stream().map(element -> element.getId()).collect(Collectors.toList())) {
			assertThat(this.productController.findById(id).getCategory()).isNotNull();
			assertThat(this.productController.findById(id).getCategory().getName()).isNotNull();
			assertThat(this.productController.findById(id).getCategory().getId()).isEqualTo(category.getId());			
		}
	}
	
	@AfterAll
	public void deleteProducts() {
		
		for(Long id : listProducts.stream().map(element -> element.getId()).collect(Collectors.toList())) {
			this.productController.deleteById(id);
		}
		
		this.categoryController.deleteById(category.getId());
	}

}
