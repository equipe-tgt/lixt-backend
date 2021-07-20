package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.PluralAttribute.CollectionType;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.core.type.ObjectMapper;

import br.com.ifsp.pi.lixt.data.business.list.ListOfItems;
import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.data.dto.UserDtoData;
import br.com.ifsp.pi.lixt.data.dto.json.ListOfItemsDtoDataJson;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponse;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseDelete;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseGet;
//import jdk.internal.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.core.type.TypeReference;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponse;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoint ListOfItems")
@TestMethodOrder(OrderAnnotation.class)
public class ListOfItemsControllerTest {
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ListOfItemsController listOfItemsController;
	
	private OauthUserDto user;
	
	private List<Product> listProducts = new ArrayList<>();
	
	private ListOfItemsDto listOfItems;
	
	private CategoryDto category;
	
	private List<OauthUserDto> oauthUsers = new ArrayList<>();
	
	private List<UserDto> users = new ArrayList<>();

	@BeforeAll
	void registerUserAndList() throws Exception {
		
		UserDtoData.initializeValues().forEach(user -> {
			oauthUsers.add((OauthUserDto) this.authController.register(user).getBody());
			users.add(UserMapper.dtoOauthToDto(oauthUsers.get(oauthUsers.size() - 1)));
			oauthUsers.get(oauthUsers.size() - 1).setPassword("123");
		});
		
		
		//user = UserDtoData.initializeValues().get(0);
		//user = (OauthUserDto) authController.register(UserDtoInstantior.createUser("user", "user", "user@hotmail.com", "123")).getBody();
		//assertThat(this.authController.register(user).getStatusCode()).isEqualTo(HttpStatus.OK);
		
		//String token = RequestOauth2.authenticate(mockMvc, user);
		//this.listOfItems = (ListOfItemsDto) RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), token, ListOfItemsDto.class);

		//ValidatorStatusResponseGet.isOk(mockMvc, user, "/list/".concat(this.listOfItems.getId().toString()));
		
		//listOfItemsController.save(listOfItems);
		
		/*UserDtoData.initializeValues().forEach(user -> {
			oauthUsers.add((OauthUserDto) this.authController.register(user).getBody());
			users.add(UserMapper.dtoOauthToDto(oauthUsers.get(oauthUsers.size() - 1)));
			oauthUsers.get(oauthUsers.size() - 1).setPassword("123");
		});
		
		
		user = (OauthUserDto) authController.register(UserDtoInstantior.createUser("user", "user", "user@hotmail.com", "123")).getBody();
		
		assertThat(user).isNotNull();
		assertThat(user.getPassword()).isNull();
		//----------------
		category = categoryController.save(CategoryDto.builder().name("padaria").build());
		
		listProducts.add(ProductDtoInstantior.createProduct("Pão de batata", category, MeasureType.KG, 5));
		listProducts.add(ProductDtoInstantior.createProduct("Calzone", category, MeasureType.KG, 2));
		
		listProducts = this.productService.saveAll(listProducts);
		
		listOfItems = ListOfItemsMapper.*/
	}
	
	@DisplayName("Encontrar as listas do usuário pelo token")
	@Test
	void searchUsersLists() throws Exception {
		//listOfItemsController.findUserLists();
		//assertThat(listOfItemsController.findUserLists().contains(listOfItems)).isEqualTo(true);
		String token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		this.listOfItems = (ListOfItemsDto) RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), token, ListOfItemsDto.class);
		
		//assertThat();
		
		/*MvcResult result = mockMvc.perform(get("/list/by-user").header("Authorization", token).contentType(MediaType.TEXT_PLAIN_VALUE))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json"))//;
			.andReturn();*/
																																			// ArrayList.class
		//ArrayList<ListOfItemsDto> list = (ArrayList<ListOfItemsDto>) RequestWithResponse.createGetRequestJson(mockMvc, "/list/by-user", token, new TypeReference<List<ListOfItemsDto>>() {});
		
		ResultActions listResult = 
				mockMvc.perform(ResquestBuilder.createGetRequestJson("/list/by-user", token))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		//List<ListOfItemsDto> list = new ObjectMapper().readValue(listResult, new TypeReference<List<ListOfItemsDto>>() {});
		
		//System.out.println(list.get(0).getOwner());
		//assertThat(list.get(0)).isEqualTo(listOfItems);
			//.andExpect(MockMvcResultMatchers.content().json(convertObjectToJsonString(listOfItems)));
		//System.out.println(result.getResponse().getContentAsString());
		
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/list/by-user");
		//ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/list/".concat(this.listOfItems.getId().toString()));
	}
	
	
	@AfterAll
	void deleteUsersAndList () throws Exception {
		//ValidatorStatusResponseDelete.isOk(mockMvc, user, "/list/" + this.listOfItems.getId());
		//this.userService.deleteById(user.getId());
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/list/" + this.listOfItems.getId());
		this.oauthUsers.forEach(user -> this.userService.deleteById(user.getId()));
	}
}
