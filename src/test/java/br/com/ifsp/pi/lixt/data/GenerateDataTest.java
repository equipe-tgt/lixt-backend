package br.com.ifsp.pi.lixt.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.controller.AuthController;
import br.com.ifsp.pi.lixt.controller.CategoryController;
import br.com.ifsp.pi.lixt.controller.ListMembersController;
import br.com.ifsp.pi.lixt.controller.ListOfItemsController;
import br.com.ifsp.pi.lixt.controller.ProductController;
import br.com.ifsp.pi.lixt.controller.ProductOfListController;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Criar massa de dados")
class GenerateDataTest {
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private ListOfItemsController listOfItemsController;
	
	@Autowired
	private ProductOfListController productOfListController;
	
	@Autowired
	private ListMembersController listMembersController;
	
	private List<ProductDto> listProducts = new ArrayList<>();
	private CategoryDto category;
	private List<ProductOfListDto> listProductsOfList = new ArrayList<>();
	private ListOfItemsDto listOfItems;

	@Test
	void createData() {
		
		OauthUserDto oauthUser1 = OauthUserDto.builder()
				.name("leo")
				.username("leo")
				.email("leo_narita@hotmail.com")
				.password("123")
				.build();
		
		OauthUserDto oauthUser2 = OauthUserDto.builder()
				.name("teste")
				.username("teste")
				.email("teste@gmail.com")
				.password("123")
				.build();
		
		OauthUserDto oauthUser3 = OauthUserDto.builder()
				.name("teste3")
				.username("teste3")
				.email("teste3@gmail.com")
				.password("123")
				.build();
		
		oauthUser1 = (OauthUserDto) this.authController.register(oauthUser1).getBody();
		oauthUser2 = (OauthUserDto) this.authController.register(oauthUser2).getBody();
		oauthUser3 = (OauthUserDto) this.authController.register(oauthUser3).getBody();
		
		UserDto user1 = UserMapper.dtoOauthToDto(oauthUser1);
		UserDto user2 = UserMapper.dtoOauthToDto(oauthUser2);
		UserDto user3 = UserMapper.dtoOauthToDto(oauthUser3);
		
		assertThat(user1.getId()).isNotNull();
		assertThat(user2.getId()).isNotNull();
		assertThat(user3.getId()).isNotNull();
		
		category = categoryController.save(CategoryDto.builder().name("Alimentação").build());
		
		listProducts.add(
				ProductDto.builder()
					.name("Arroz")
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.KG).measureValue(new BigDecimal(5))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Feijão")
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.KG).measureValue(new BigDecimal(2))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Sal")
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.KG).measureValue(new BigDecimal(1))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Açúcar")
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.KG).measureValue(new BigDecimal(1))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Azeite")
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.L).measureValue(new BigDecimal(1))
					.build()
		);
		
		listProducts.add(
				ProductDto.builder()
					.name("Vinagre")
					.categoryId(category.getId()).category(category)
					.measureType(MeasureType.ML).measureValue(new BigDecimal(750))
					.build()
		);
		
		listProducts = this.productController.saveAll(listProducts);
		
		listOfItems = this.listOfItemsController.save(
				ListOfItemsDto.builder().nameList("Lista De Teste").ownerId(user1.getId()).description("Teste").build()
		);
		
		listProductsOfList.add(
				ProductOfListDto.builder()
					.productId(listProducts.get(0).getId())
					.listId(listOfItems.getId())
					.name("Arroz branco")
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(5))
					.isMarked(false)
					.build()
		);
		
		listProductsOfList.add(
				ProductOfListDto.builder()
					.productId(listProducts.get(0).getId())
					.listId(listOfItems.getId())
					.name("Arroz integral")
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(5))
					.isMarked(false)
					.build()
		);
		
		listProductsOfList = this.productOfListController.saveAll(listProductsOfList);
		
		assertThat(listProductsOfList).isNotNull();
		
		listMembersController.sendInvite(listOfItems.getId(), user2.getUsername());
		listMembersController.acceptInvite(listOfItems.getId());
	}
	
}
