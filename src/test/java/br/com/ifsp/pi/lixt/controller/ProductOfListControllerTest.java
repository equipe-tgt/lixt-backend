package br.com.ifsp.pi.lixt.controller;

import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.data.dto.UserDtoData;
import br.com.ifsp.pi.lixt.data.dto.json.CommentDtoDataJson;
import br.com.ifsp.pi.lixt.data.dto.json.ListOfItemsDtoDataJson;
import br.com.ifsp.pi.lixt.data.dto.json.ProductOfListDtoDataJson;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.*;
import br.com.ifsp.pi.lixt.dto.specific.AllCommentsDto;
import br.com.ifsp.pi.lixt.instantiator.CommentDtoInstantiator;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantiator;
import br.com.ifsp.pi.lixt.instantiator.ProductOfListDtoInstantiator;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.response.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	private List<UserDto> oauthUsers = new ArrayList<>();
	private String token;
	private List<ListMembersDto> listMembers = new ArrayList<>();
	private List<CommentDto> comments = new ArrayList<>();

	@BeforeAll
	void createProducts() throws SQLException {
		category = categoryController.save(CategoryDto.builder().name("alimento").build());
		ResponseEntity<Object> response = this.productController.save(ProductMapper.entityToDto(ProductDtoInstantiator.createProduct("Arroz", category, MeasureType.KG, 5)));
		product = (ProductDto) response.getBody();

		UserDtoData.dataForProductOfListControllerTest().forEach(user -> {
			oauthUsers.add((UserDto) this.authController.register(user, null).getBody());
			oauthUsers.get(oauthUsers.size() - 1).setPassword("123");
			this.authController.activeUser(this.userService.findFirstAccesTokenById(oauthUsers.get(oauthUsers.size() - 1).getId()), null);
		});
	}

	@Test
	@DisplayName("Busca de produtos cadastrados pelo usu??rio ou cujo user ID ?? null")
	@Order(1)
	void searchProduct() throws Exception {

		String[] valuesToSearch = new String[] {"Arroz", "arroz", "Ar", "oz", "rr"};

		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));

		for(String valueToSearch : valuesToSearch) {
			Long result = RequestWithResponseList.createGetRequestJson(mockMvc, "/product//by-name/" + valueToSearch, token, ProductDto.class)
					.stream().filter(element -> element.getId().equals(product.getId())).count();

			assertThat(result).isOne();
		}
	}

	@DisplayName("Criar lista, produtos da lista e convites")
	@Test
	@Order(2)
	void testList() throws Exception {

		String token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		this.listOfItems = RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), token, ListOfItemsDto.class);

		assertThat(listOfItems).isNotNull();

		for(int i=1; i<4; i++) {
			listMembers.add(
					RequestWithResponse.createPostRequestJson(mockMvc, "/membersList/send-invite/" + this.listOfItems.getId(), oauthUsers.get(i).getUsername(), token, ListMembersDto.class)
			);
		}

		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(1));
		listMembers.set(
				0,
				RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/accept-invite/" + this.listMembers.get(0).getId(), token, ListMembersDto.class)
		);

		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(2));
		listMembers.set(
				1,
				RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/reject-invite/" + this.listMembers.get(1).getId(), token, ListMembersDto.class)
		);

		for(int i=0; i<2; i++) {
			for(String productOfList : ProductOfListDtoDataJson.initializeValues(this.listOfItems,
					Objects.requireNonNull(ProductMapper.dtoToEntity(this.product)))) {
				String tokenUser = RequestOauth2.authenticate(mockMvc, oauthUsers.get(i));
				ProductOfListDto product = RequestWithResponse.createPostRequestJson(mockMvc, "/productOfList", productOfList, tokenUser, ProductOfListDto.class);
				this.productsOfList.add(product);
			}
		}
	}

	@DisplayName("Criar coment??rio")
	@Test
	@Order(3)
	void createComment() throws Exception {

		for(String comment : CommentDtoDataJson.initializeValues(oauthUsers.get(1), this.productsOfList.get(0))) {

			CommentDto commentDto = RequestWithResponse.createPostRequestJson(mockMvc, "/comment", comment, oauthUsers.get(1), CommentDto.class);

			for(UserDto userToTryUpdate : oauthUsers) {

				if(userToTryUpdate.getId().equals(commentDto.getUserId())) {
					commentDto.setContent("CARROZ");
					ValidatorStatusResponsePut.isOk(mockMvc, userToTryUpdate, "/comment/" + commentDto.getId(), CommentDtoInstantiator.createCommentJson(commentDto));

					token = RequestOauth2.authenticate(mockMvc, userToTryUpdate);
					CommentDto commentTemp = RequestWithResponse.createGetRequestJson(mockMvc, "/comment/" + commentDto.getId(), token, CommentDto.class);
					assertThat(commentTemp.getContent()).isEqualTo(commentDto.getContent());

					ValidatorStatusResponsePut.isPreconditionFailed(mockMvc, userToTryUpdate, "/comment/0", CommentDtoInstantiator.createCommentJson(commentTemp));

					commentTemp.setUserId(0L);
					ValidatorStatusResponsePut.isPreconditionFailed(mockMvc, userToTryUpdate, "/comment/" + commentDto.getId(), CommentDtoInstantiator.createCommentJson(commentTemp));

					continue;
				}

				commentDto.setContent("banana");
				ValidatorStatusResponsePut.isForbidden(mockMvc, userToTryUpdate, "/comment/" + commentDto.getId(), CommentDtoInstantiator.createCommentJson(commentDto));
			}

			for(UserDto userToTryDelete : oauthUsers) {

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
					RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productsOfList.get(0).getId() + "/comments", token, AllCommentsDto.class)
							.getCommentsDto().size()
			).isPositive();
		}

		for(int i=2; i<5; i++) {
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId() + "/comments");
		}
	}

	@DisplayName("Manipula????es de produto da lista")
	@Test
	@Order(5)
	void manipulateProductsOfList() throws Exception {

		for(int i=0; i<=1; i++) {
			token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(i));

			for(int j=0; j<=1; j++) {
				assertThat(
						RequestWithResponse.createGetRequestJson(
								mockMvc, "/productOfList/" + this.productsOfList.get(j).getId(), token, ProductOfListDto.class)
				).isNotNull();

				this.productsOfList.get(j).setIsMarked(true);
				ValidatorStatusResponsePut.isOk(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(j).getId(), ProductOfListDtoInstantiator.createProductOfListJson(this.productsOfList.get(j)));

				ProductOfListDto productTemp = RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productsOfList.get(j).getId(), token, ProductOfListDto.class);

				assertTrue(productTemp.getIsMarked());

				ValidatorStatusResponsePut.isPreconditionFailed(mockMvc, oauthUsers.get(i), "/productOfList/0", ProductOfListDtoInstantiator.createProductOfListJson(this.productsOfList.get(j)));
			}
		}

		for(int i=2; i<5; i++) {
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId());
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(1).getId());
			ValidatorStatusResponsePost.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList", ProductOfListDtoDataJson.createValue(this.listOfItems,
					Objects.requireNonNull(ProductMapper.dtoToEntity(this.product))));
			ValidatorStatusResponsePut.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId(), ProductOfListDtoInstantiator.createProductOfListJson(this.productsOfList.get(0)));
			ValidatorStatusResponseDelete.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId());
		}
	}

	@DisplayName("Marcar quantidades de um produto")
	@Test
	@Order(6)
	void markProductsOfList() throws Exception {
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		assertThat(
				RequestWithResponse.createGetRequestJson(
						mockMvc, "/productOfList/" + this.productsOfList.get(0).getId(), token, ProductOfListDto.class)
		).isNotNull();

		this.productsOfList.get(0).setMarkedAmount(1);

		ValidatorStatusResponsePut.isOk(mockMvc,
										oauthUsers.get(0),
									"/productOfList/" + this.productsOfList.get(0).getId() + "/mark-amount/" + this.productsOfList.get(0).getMarkedAmount(),
										ProductOfListDtoInstantiator.createProductOfListJson(this.productsOfList.get(0)));

		ProductOfListDto productTemp = RequestWithResponse.createGetRequestJson(
				mockMvc, "/productOfList/" + this.productsOfList.get(0).getId(), token, ProductOfListDto.class);

		assertThat(productTemp.getMarkedAmount()).isEqualTo(1);

		this.productsOfList.get(0).setMarkedAmount(3);

		ValidatorStatusResponsePut.isOk(mockMvc,
				oauthUsers.get(0),
				"/productOfList/" + this.productsOfList.get(0).getId() + "/mark-amount/" + this.productsOfList.get(0).getMarkedAmount(),
				ProductOfListDtoInstantiator.createProductOfListJson(this.productsOfList.get(0)));

		productTemp = RequestWithResponse.createGetRequestJson(
				mockMvc, "/productOfList/" + this.productsOfList.get(0).getId(), token, ProductOfListDto.class);

		assertThat(productTemp.getMarkedAmount()).isEqualTo(3);
	}
	@DisplayName("Marcar e atribuir itens")
	@Test
	@Order(7)
	void markAndAtribute() throws Exception {

		for(int i=2; i<5; i++) {
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/mark/" + this.productsOfList.get(0).getId());
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/clean/" + this.productsOfList.get(0).getId());
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/"+ this.productsOfList.get(0).getId() + "/assigned-to-me");
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId() + "/assigned-to/" + oauthUsers.get(1).getId());
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/list/" + this.listOfItems.getId() + "/clean-marked-itens");
		}

		for(int i=0; i<2; i++) {
			ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(i), "/productOfList/mark/" + this.productsOfList.get(0).getId());
			ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(i), "/productOfList/clean/" + this.productsOfList.get(0).getId());
			ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(i), "/productOfList/"+ this.productsOfList.get(0).getId() + "/assigned-to-me");

			if(i == 0)
				ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId() + "/assigned-to/" + oauthUsers.get(1).getId());
			if(i == 1)
				ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/productOfList/" + this.productsOfList.get(0).getId() + "/assigned-to/" + oauthUsers.get(1).getId());
		}

		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/productOfList/mark/" + this.productsOfList.get(1).getId());
		assertThat(RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productsOfList.get(1).getId() + "/assigned-to-me", oauthUsers.get(1), Integer.class)).isOne();
		assertThat(RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/mark/" + this.productsOfList.get(1).getId(), oauthUsers.get(0), Integer.class)).isZero();
		assertThat(RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productsOfList.get(1).getId() + "/assigned-to-me", oauthUsers.get(0), Integer.class)).isZero();

		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/productOfList/"+ this.productsOfList.get(2).getId() + "/assigned-to-me");
		assertThat(RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/mark/" + this.productsOfList.get(2).getId(), oauthUsers.get(1), Integer.class)).isZero();
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/productOfList/"+ this.productsOfList.get(2).getId() + "/assigned-to-me");
		assertThat(RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/mark/" + this.productsOfList.get(2).getId(), oauthUsers.get(1), Integer.class)).isOne();

		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/productOfList/clean/" + this.productsOfList.get(0).getId());
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/productOfList/" + this.productsOfList.get(0).getId() + "/assigned-to/" + oauthUsers.get(1).getId());
		assertThat(RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productsOfList.get(0).getId() + "/assigned-to/" + oauthUsers.get(1).getId(), oauthUsers.get(0), Integer.class)).isOne();

		ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(1), "/list/" + this.listOfItems.getId() + "/clean-marked-itens");
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/list/" + this.listOfItems.getId() + "/clean-marked-itens");
	}

	@AfterAll
	void deleteData() throws Exception {

		for(CommentDto comment : comments) {
			for(UserDto userToTryDelete : oauthUsers) {
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
