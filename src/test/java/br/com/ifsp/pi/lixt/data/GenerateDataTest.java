package br.com.ifsp.pi.lixt.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
@DisplayName("Criar massa de dados")
public class GenerateDataTest {
	
	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private ProductController productController;
	
	private List<ProductDto> listProducts = new ArrayList<>();
	private CategoryDto category;

	@Test
	public void createData() {
		
		category = categoryController.save(CategoryDto.builder().name("Alimentação").build());
		
		listProducts.add(
				ProductDto.builder()
					.name("Arroz").price(new BigDecimal(30))
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.KG).measureValue(new BigDecimal(5))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Feijão").price(new BigDecimal(15))
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.KG).measureValue(new BigDecimal(2))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Sal").price(new BigDecimal(1))
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.KG).measureValue(new BigDecimal(1))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Açúcar").price(new BigDecimal(4))
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.KG).measureValue(new BigDecimal(1))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Azeite").price(new BigDecimal(20))
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.L).measureValue(new BigDecimal(1))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Vinagre").price(new BigDecimal(6))
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.ML).measureValue(new BigDecimal(750))
					.build()
		);
		
		this.productController.saveAll(listProducts);
	}
	
}
