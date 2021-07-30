package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.data.dto.UserDtoData;
import br.com.ifsp.pi.lixt.data.dto.json.CommentDtoDataJson;
import br.com.ifsp.pi.lixt.data.dto.json.ListOfItemsDtoDataJson;
import br.com.ifsp.pi.lixt.data.dto.json.ProductOfListDtoDataJson;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.CommentDto;
import br.com.ifsp.pi.lixt.dto.ListMembersDto;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.instantiator.CommentDtoInstantior;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantior;
import br.com.ifsp.pi.lixt.instantiator.ProductOfListDtoInstantior;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponse;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponseList;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseDelete;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseGet;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponsePost;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponsePut;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints na classe ProductOfListController")
@TestMethodOrder(OrderAnnotation.class)
class ProductOfListControllerTest {

	@Autowired
	private AuthController authController;
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	private List<ProductOfListDto> productsOfList = new ArrayList<>();
	private ListOfItemsDto listOfItems;
	private CategoryDto category;
	private ProductDto product;
	private List<OauthUserDto> oauthUsers = new ArrayList<>();
	private String token;
	private List<ListMembersDto> listMembers = new ArrayList<>();
	private List<CommentDto> comments = new ArrayList<>();
	
	@BeforeAll
	void createProducts() {
		category = categoryController.save(CategoryDto.builder().name("alimento").build());
		product = this.productController.save(ProductMapper.entityToDto(ProductDtoInstantior.createProduct("Arroz", category, MeasureType.KG, 5)));

		UserDtoData.dataForProductOfListControllerTest().forEach(user -> {
			oauthUsers.add((OauthUserDto) this.authController.register(user).getBody());
			oauthUsers.get(oauthUsers.size() - 1).setPassword("123");
		});
	}
	
	@Test
	@DisplayName("Busca de produtos cadastrados pelo usuário ou cujo user ID é null")
	@Order(1)
	void searchProduct() throws Exception {
		
		String[] valuesToSearch = new String[] {"Arroz", "arroz", "Ar", "oz", "rr"};
		
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		
		for(String valueToSearch : valuesToSearch) {
			Long result = RequestWithResponseList.createGetRequestJson(mockMvc, "/product//by-name/" + valueToSearch, token, ProductDto.class)
					.stream().filter(element -> ((ProductDto) element).getId().equals(product.getId())).count();
			
			assertThat(result).isOne();
		}
	}
	
	@DisplayName("Criar lista, produtos da lista e convites")
	@Test
	@Order(2)
	void testList() throws Exception {

		String token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		this.listOfItems = (ListOfItemsDto) RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), token, ListOfItemsDto.class);
		
		assertThat(listOfItems).isNotNull();
		
		for(int i=1; i<4; i++) {
			listMembers.add(
					(ListMembersDto) RequestWithResponse.createPostRequestJson(mockMvc, "/membersList/send-invite/" + this.listOfItems.getId(), oauthUsers.get(i).getUsername(), token, ListMembersDto.class)
			);
		}
		
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(1));
		listMembers.set(
				0, 
				(ListMembersDto) RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/accept-invite/" + this.listMembers.get(0).getId(), token, ListMembersDto.class)
		);
		
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(2));
		listMembers.set(
				1, 
				(ListMembersDto) RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/reject-invite/" + this.listMembers.get(1).getId(), token, ListMembersDto.class)
		);
		
		for(int i=0; i<2; i++) {
			for(String productOfList : ProductOfListDtoDataJson.initializeValues(this.listOfItems, ProductMapper.dtoToEntity(this.product))) {
				String tokenUser = RequestOauth2.authenticate(mockMvc, oauthUsers.get(i));
				ProductOfListDto product = (ProductOfListDto) RequestWithResponse.createPostRequestJson(mockMvc, "/productOfList", productOfList, tokenUser, ProductOfListDto.class);
				this.productsOfList.add(product);
			}
		}
	}
	
	@DisplayName("Criar comentário")
	@Test
	@Order(3)
	void createComment() throws Exception {
				
		for(String comment : CommentDtoDataJson.initializeValues(oauthUsers.get(1), this.productsOfList.get(0))) {
				
			CommentDto commentDto = (CommentDto) RequestWithResponse.createPostRequestJson(mockMvc, "/comment", comment, oauthUsers.get(1), CommentDto.class);
			
			for(OauthUserDto userToTryUpdate : oauthUsers) {
				
				if(userToTryUpdate.getId().equals(commentDto.getUserId())) {
					commentDto.setContent("CARROZ");
					ValidatorStatusResponsePut.isOk(mockMvc, userToTryUpdate, "/comment/" + commentDto.getId(), CommentDtoInstantior.createCommentJson(commentDto));
					
					token = RequestOauth2.authenticate(mockMvc, userToTryUpdate);
					CommentDto commentTemp = (CommentDto) RequestWithResponse.createGetRequestJson(mockMvc, "/comment/" + commentDto.getId(), token, CommentDto.class);
					assertThat(commentTemp.getContent()).isEqualTo(commentDto.getContent());
					
					ValidatorStatusResponsePut.isPreconditionFailed(mockMvc, userToTryUpdate, "/comment/0", CommentDtoInstantior.createCommentJson(commentTemp));
					
					commentTemp.setUserId(0l);
					ValidatorStatusResponsePut.isPreconditionFailed(mockMvc, userToTryUpdate, "/comment/" + commentDto.getId(), CommentDtoInstantior.createCommentJson(commentTemp));
					
					continue;
				}
				
				commentDto.setContent("banana");
				ValidatorStatusResponsePut.isForbidden(mockMvc, userToTryUpdate, "/comment/" + commentDto.getId(), CommentDtoInstantior.createCommentJson(commentDto));
			}
			
			for(OauthUserDto userToTryDelete : oauthUsers) {
				
				if(userToTryDelete.getId().equals(commentDto.getUserId()))
					continue;
				
				ValidatorStatusResponseDelete.isForbidden(mockMvc, userToTryDelete, "/comment/" + commentDto.getId());
			}
			
			this.comments.add(commentDto);
			assertThat(commentDto).isNotNull();
		}
		
		for(int i=0; i<=1; i++) {
			token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(i));
			
			assertThat(
					RequestWithResponseList.createGetRequestJson(
							mockMvc, "/productOfList/" + this.productsOfList.get(0).getId() + "/comments", token, CommentDto.class).size()
			).isPositive();
		}
		
		for(int i=2; i<5; i++) {
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId() + "/comments");
		}
	}
	
	@DisplayName("Manipulações de produto da lista")
	@Test
	@Order(4)
	void manipulateProductsOfList() throws Exception {
		
		for(int i=0; i<=1; i++) {
			token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(i));
			
			for(int j=0; j<=1; j++) {
				assertThat(
						RequestWithResponse.createGetRequestJson(
								mockMvc, "/productOfList/" + this.productsOfList.get(j).getId(), token, ProductOfListDto.class)
				).isNotNull();
				
				this.productsOfList.get(j).setIsMarked(true);
				ValidatorStatusResponsePut.isOk(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(j).getId(), ProductOfListDtoInstantior.createProductOfListJson(this.productsOfList.get(j)));
				
				ProductOfListDto productTemp = (ProductOfListDto) RequestWithResponse.createGetRequestJson(
						mockMvc, "/productOfList/" + this.productsOfList.get(j).getId(), token, ProductOfListDto.class);
				
				assertTrue(productTemp.getIsMarked());
				
				ValidatorStatusResponsePut.isPreconditionFailed(mockMvc, oauthUsers.get(i), "/productOfList/0", ProductOfListDtoInstantior.createProductOfListJson(this.productsOfList.get(j)));
			}
		}
		
		for(int i=2; i<5; i++) {
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId());
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(1).getId());
			ValidatorStatusResponsePost.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList", ProductOfListDtoDataJson.createValue(this.listOfItems, ProductMapper.dtoToEntity(this.product)));
			ValidatorStatusResponsePut.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId(), ProductOfListDtoInstantior.createProductOfListJson(this.productsOfList.get(0)));
			ValidatorStatusResponseDelete.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId());
		}
	}

	@AfterAll
	void deleteData() throws Exception {
		
		for(CommentDto comment : comments) {
			for(OauthUserDto userToTryDelete : oauthUsers) {
				if(userToTryDelete.getId().equals(comment.getUserId()))
					ValidatorStatusResponseDelete.isOk(mockMvc, userToTryDelete, "/comment/" + comment.getId());
			}
		}

		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/productOfList/" + productsOfList.get(0).getId());
		
		for(int i=1; i<4; i++) {
			ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(1), "/productOfList/" + productsOfList.get(i).getId());
		}
		
		for(ListMembersDto listMember: listMembers)
			ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/membersList/" + listMember.getId());
		
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/list/" + this.listOfItems.getId());
		
		this.productService.deleteById(product.getId());
		this.categoryController.deleteById(this.category.getId());
		this.oauthUsers.forEach(user -> this.userService.deleteById(user.getId()));
	}
	
}
