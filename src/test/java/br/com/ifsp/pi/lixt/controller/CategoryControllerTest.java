package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.dto.CategoryDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoint Category")
@TestMethodOrder(OrderAnnotation.class)
class CategoryControllerTest {
	
	@Autowired
	private CategoryController categoryController;
	
	private CategoryDto category;
	
	@BeforeAll
	void createCategory() {
		category = categoryController.save(CategoryDto.builder().name("pães").build());
	}
	
	@Test
	@DisplayName("Buscar todas as categorias")
	void findAllCategories() {
		assertThat(categoryController.findAll().contains(category)).isEqualTo(true);
	}
	
	@Test
	@DisplayName("Encontrar categoria pelo ID")
	void findCategoryById() {
		assertThat(categoryController.findById(category.getId()).equals(category));
	}
	
	@Test
	@DisplayName("Atualizar categoria")
	void updateCategory() {
		category.setName("padaria");
		
		assertThat(categoryController.update(category, category.getId()).getName().equalsIgnoreCase("padaria"));
	}
	
	@AfterAll
	void deleteProducts() {
		this.categoryController.deleteById(category.getId());
	}
}
