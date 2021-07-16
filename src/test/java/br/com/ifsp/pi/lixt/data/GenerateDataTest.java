package br.com.ifsp.pi.lixt.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import br.com.ifsp.pi.lixt.controller.AuthController;
import br.com.ifsp.pi.lixt.controller.CategoryController;
import br.com.ifsp.pi.lixt.controller.PurchaseLocalController;
import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.data.dto.json.CommentDtoDataJson;
import br.com.ifsp.pi.lixt.data.dto.json.ListOfItemsDtoDataJson;
import br.com.ifsp.pi.lixt.data.dto.ProductDtoData;
import br.com.ifsp.pi.lixt.data.dto.json.ProductOfListDtoDataJson;
import br.com.ifsp.pi.lixt.data.dto.UserDtoData;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.CommentDto;
import br.com.ifsp.pi.lixt.dto.ListMembersDto;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponse;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseDelete;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseGet;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponsePost;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Criar massa de dados e testar primeiros casos de uso e casos de segurança")
@TestMethodOrder(OrderAnnotation.class)
class GenerateDataTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PurchaseLocalController purchaseLocalController;
	
	@Autowired
	private UserService userService;
	
	private List<OauthUserDto> oauthUsers = new ArrayList<>();
	private List<Product> products = new ArrayList<>();
	private CategoryDto category;
	private List<ProductOfListDto> productsOfList = new ArrayList<>();
	private ListOfItemsDto listOfItems;
	private List<UserDto> users = new ArrayList<>();
	private PurchaseLocalDto purchaseLocal;
	private ListMembersDto listMembers;
	private List<CommentDto> comments = new ArrayList<>();
		
	@BeforeAll
	void initializeData() throws Exception {

		UserDtoData.initializeValues().forEach(user -> {
			oauthUsers.add((OauthUserDto) this.authController.register(user).getBody());
			users.add(UserMapper.dtoOauthToDto(oauthUsers.get(oauthUsers.size() - 1)));
			oauthUsers.get(oauthUsers.size() - 1).setPassword("123");
		});
		
		assertThat(this.authController.register(oauthUsers.get(0)).getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		
		this.category = categoryController.save(CategoryDto.builder().name("Alimentação").build());		
		this.products = this.productService.saveAll(ProductDtoData.initializeValues(category));
		
		purchaseLocal = this.purchaseLocalController.save(
				PurchaseLocalDto.builder().name("Mercado Extra").latitude(23.66666).longitude(20.77777).build()
		);
		
		assertThat(purchaseLocal).isNotNull();
	}
	
	@DisplayName("Criar lista e validar que apenas o dono e os membros da lista que aceitaram o convite tem acesso à ela")
	@Test
	@Order(1)
	void testList() throws Exception {
		String token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		this.listOfItems = (ListOfItemsDto) RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), token, ListOfItemsDto.class);

		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/list/".concat(this.listOfItems.getId().toString()));
		ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(1), "/list/".concat(this.listOfItems.getId().toString()));
		ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(2), "/list/".concat(this.listOfItems.getId().toString()));
	}
	
	@DisplayName("Criar produtos da lista")
	@Test
	@Order(2)
	void testProductsOfList() throws Exception {
		
		for(String productOfList : ProductOfListDtoDataJson.initializeValues(this.listOfItems, this.products.get(0))) {
			String tokenUser = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
			ProductOfListDto product = (ProductOfListDto) RequestWithResponse.createPostRequestJson(mockMvc, "/productOfList", productOfList, tokenUser, ProductOfListDto.class);
			this.productsOfList.add(product);
			assertThat(product).isNotNull();
		}
		
		assertThat(this.productsOfList.size()).isGreaterThan(0);
		System.out.println(this.listOfItems.getId());
	}
	
	@DisplayName("Criar convite e validar que apenas o dono e os membros da lista que aceitaram o convite tem acesso à ela")
	@Test
	@Order(3)
	void testCreateListMembers() throws Exception {
		
		String token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		listMembers = (ListMembersDto) RequestWithResponse.createPostRequestJson(mockMvc, "/membersList/send-invite/" + this.listOfItems.getId(), "teste", token, ListMembersDto.class);
		assertThat(listMembers).isNotNull();
		
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/list/".concat(this.listOfItems.getId().toString()));
		ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(1), "/list/".concat(this.listOfItems.getId().toString()));
		ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(2), "/list/".concat(this.listOfItems.getId().toString()));
		System.out.println(this.listOfItems.getId());
	}
	
	@DisplayName("Aceitar convite e validar que apenas o dono e os membros da lista que aceitaram o convite tem acesso à ela")
	@Test
	@Order(4)
	void testAcceptListMembers() throws Exception {
		
		String token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(1));
		listMembers = (ListMembersDto) RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/accept-invite/" + this.listOfItems.getId(), token, ListMembersDto.class);
		assertThat(listMembers).isNotNull();
		
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/list/".concat(this.listOfItems.getId().toString()));
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(1), "/list/".concat(this.listOfItems.getId().toString()));
		ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(2), "/list/".concat(this.listOfItems.getId().toString()));
	}
	
	@DisplayName("Criar comentário e validar que apenas o dono e os membros da lista que aceitaram o convite aos comentários e podem comentar")
	@Test
	@Order(5)
	void createData() throws Exception {
		
		for(String comment : CommentDtoDataJson.initializeValues(this.users.get(0), this.productsOfList.get(0))) {
			CommentDto commentDto = (CommentDto) RequestWithResponse.createPostRequestJson(mockMvc, "/comment", comment, oauthUsers.get(0), CommentDto.class);
			this.comments.add(commentDto);
			assertThat(commentDto).isNotNull();
			
			commentDto = (CommentDto) RequestWithResponse.createPostRequestJson(mockMvc, "/comment", comment, oauthUsers.get(1), CommentDto.class);
			this.comments.add(commentDto);
			assertThat(commentDto).isNotNull();
			
			ValidatorStatusResponsePost.isForbidden(mockMvc, oauthUsers.get(2), "/comment", comment);
		}
		
		assertThat(this.comments.size()).isGreaterThan(0);
		
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/comment/" + this.comments.get(0).getId());
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(1), "/comment/" + this.comments.get(0).getId());
		ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(2), "/comment/" + this.comments.get(0).getId());		
	}
	
	@AfterAll
	void deleteData() throws Exception {
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/list/" + this.listOfItems.getId());
		
		this.products.forEach(product -> this.productService.deleteById(product.getId()));
		this.categoryController.deleteById(this.category.getId());
		this.oauthUsers.forEach(user -> this.userService.deleteById(user.getId()));
	}

}
