package br.com.ifsp.pi.lixt.controller;

import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.data.dto.UserDtoData;
import br.com.ifsp.pi.lixt.data.dto.json.GlobalCommentDtoJson;
import br.com.ifsp.pi.lixt.data.dto.json.ListOfItemsDtoDataJson;
import br.com.ifsp.pi.lixt.data.dto.json.ProductOfListDtoDataJson;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.*;
import br.com.ifsp.pi.lixt.dto.specific.AllCommentsDto;
import br.com.ifsp.pi.lixt.instantiator.GlobalCommentDtoInstantiator;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantiator;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponse;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseDelete;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseGet;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponsePut;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints na classe GlobalCommentController")
class GlobalCommentControllerTest {

	@Autowired
	GlobalCommentController globalCommentController;

	@Autowired
	private CategoryController categoryController;

	@Autowired
	private AuthController authController;

	@Autowired
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductController productController;

	@Autowired
	private ProductService productService;

	private CategoryDto category;

	private ProductDto product;

	private List<UserDto> oauthUsers = new ArrayList<>();

	private ListOfItemsDto listOfItems;

	private ProductOfListDto productOfListDto;

	private String tokenOauthUser0;

	private String tokenOauthUser1;

	@BeforeAll
	void createTestPreconditions() throws Exception {
		category = categoryController.save(CategoryDto.builder().name("Alimenta????o").build());
		ResponseEntity<Object> response = this.productController.save(ProductMapper.entityToDto(ProductDtoInstantiator.createProduct("Arroz", category, MeasureType.KG, 5)));
		product = (ProductDto) response.getBody();

		UserDtoData.dataForGlobalCommentControllerTest().subList(0, 2).forEach(user -> {
			oauthUsers.add((UserDto) this.authController.register(user, null).getBody());
			oauthUsers.get(oauthUsers.size() - 1).setPassword("123");
			this.authController.activeUser(this.userService.findFirstAccesTokenById(oauthUsers.get(oauthUsers.size() - 1).getId()), null);
		});

		tokenOauthUser0 = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));

		tokenOauthUser1 = RequestOauth2.authenticate(mockMvc, oauthUsers.get(1));

		this.listOfItems = RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), tokenOauthUser0, ListOfItemsDto.class);

		assertThat(listOfItems).isNotNull();

		String productOfListJson = ProductOfListDtoDataJson.initializeValues(this.listOfItems,
				Objects.requireNonNull(ProductMapper.dtoToEntity(this.product))).get(0);

		productOfListDto = RequestWithResponse.createPostRequestJson(mockMvc, "/productOfList", productOfListJson, tokenOauthUser0, ProductOfListDto.class);
	}

	@Test
	@DisplayName("Adicionar e deletar coment??rios globais")
	void addAndDeleteGlobalComments() throws Exception {

		String globalComment1 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(0);

		GlobalCommentDto globalCommentDto1 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment1, oauthUsers.get(0), GlobalCommentDto.class);
		ValidatorStatusResponseGet.isOk(mockMvc, oauthUsers.get(0), "/globalComment/" + globalCommentDto1.getId());

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);

		String globalComment2 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(1);

		GlobalCommentDto globalCommentDto2 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment2, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(2);

		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/globalComment/" + globalCommentDto1.getId());

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);

		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/globalComment/" + globalCommentDto2.getId());

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isZero();
	}

	@Test
	@DisplayName("Atualizar coment??rio global")
	void updateGlobalComments() throws Exception {
		String globalComment1 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(0);

		GlobalCommentDto globalCommentDto1 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment1, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);

		globalCommentDto1.setContent("Coment??rio global [atualizado]");

		ValidatorStatusResponsePut.isOk(mockMvc,
				oauthUsers.get(0),
				"/globalComment",
				GlobalCommentDtoInstantiator.createGlobalCommentJson(globalCommentDto1));

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc,
								"/productOfList/" + this.productOfListDto.getId() + "/comments",
								tokenOauthUser0,
								AllCommentsDto.class)
						.getGlobalCommentsDto().get(0).getContent()
		).isEqualTo("Coment??rio global [atualizado]");

		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/globalComment/" + globalCommentDto1.getId());

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isZero();
	}

	@Test
	@DisplayName("Mesmo coment??rio global em listas diferentes")
	void sameGlobalCommentInTwoLists() throws Exception {
		String globalComment1 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(0);

		GlobalCommentDto globalCommentDto1 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment1, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);


		ListOfItemsDto otherList = RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), tokenOauthUser0, ListOfItemsDto.class);

		assertThat(otherList).isNotNull();

		String productOfListJson = ProductOfListDtoDataJson.initializeValues(otherList, Objects.requireNonNull(ProductMapper.dtoToEntity(this.product))).get(0);

		ProductOfListDto productOfListDto2 = RequestWithResponse.createPostRequestJson(mockMvc, "/productOfList", productOfListJson, tokenOauthUser0, ProductOfListDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc,
								"/productOfList/" + productOfListDto2.getId() + "/comments",
								tokenOauthUser0,
								AllCommentsDto.class)
						.getGlobalCommentsDto().get(0).getContent()
		).isEqualTo(globalCommentDto1.getContent());



		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/globalComment/" + globalCommentDto1.getId());

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isZero();

		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/productOfList/" + productOfListDto2.getId());
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/list/" + otherList.getId());
	}

	@Test
	@DisplayName("Ver coment??rio global na lista compartilhada mesmo n??o sendo o autor")
	void listMemberReadGlobalComment() throws Exception {

		String globalComment1 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(0);

		GlobalCommentDto globalCommentDto1 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment1, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);

		String globalComment3 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(2);

		GlobalCommentDto globalCommentDto3 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment3, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(2);

		ListMembersDto listMember = RequestWithResponse.createPostRequestJson(mockMvc, "/membersList/send-invite/" + this.listOfItems.getId(), oauthUsers.get(1).getUsername(), tokenOauthUser0, ListMembersDto.class);

		RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/accept-invite/" + listMember.getId(), tokenOauthUser1, ListMembersDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc,
								"/productOfList/" + this.productOfListDto.getId() + "/comments",
								tokenOauthUser1,
								AllCommentsDto.class)
						.getGlobalCommentsDto().get(0).getContent()
		).isEqualTo(globalCommentDto1.getContent());


		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/globalComment/" + globalCommentDto1.getId());
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/globalComment/" + globalCommentDto3.getId());
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/membersList/" + listMember.getId());
	}

	@Test
	@DisplayName("Apenas o autor pode editar coment??rios globais")
	void nonAuthorEditGlobalComment() throws Exception {
		String globalComment1 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(0);

		GlobalCommentDto globalCommentDto1 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment1, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);

		globalCommentDto1.setContent("Coment??rio global [atualizado]");

		ValidatorStatusResponsePut.isOk(mockMvc,
				oauthUsers.get(0),
				"/globalComment",
				GlobalCommentDtoInstantiator.createGlobalCommentJson(globalCommentDto1));

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc,
								"/productOfList/" + this.productOfListDto.getId() + "/comments",
								tokenOauthUser0,
								AllCommentsDto.class)
						.getGlobalCommentsDto().get(0).getContent()
		).isEqualTo("Coment??rio global [atualizado]");


		ListMembersDto listMember = RequestWithResponse.createPostRequestJson(mockMvc, "/membersList/send-invite/" + this.listOfItems.getId(), oauthUsers.get(1).getUsername(), tokenOauthUser0, ListMembersDto.class);

		RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/accept-invite/" + listMember.getId(), tokenOauthUser1, ListMembersDto.class);

		globalCommentDto1.setContent("Coment??rio global [atualizado 2]");

		ValidatorStatusResponsePut.isForbidden(mockMvc,
				oauthUsers.get(1),
				"/globalComment",
				GlobalCommentDtoInstantiator.createGlobalCommentJson(globalCommentDto1));

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc,
								"/productOfList/" + this.productOfListDto.getId() + "/comments",
								tokenOauthUser1,
								AllCommentsDto.class)
						.getGlobalCommentsDto().get(0).getContent()
		).isEqualTo("Coment??rio global [atualizado]");



		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/globalComment/" + globalCommentDto1.getId());

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isZero();

		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/membersList/" + listMember.getId());
	}

	@AfterAll
	void deleteProducts() throws Exception {
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/productOfList/" + productOfListDto.getId());
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/list/" + this.listOfItems.getId());
		this.productService.deleteById(product.getId());
		this.categoryController.deleteById(this.category.getId());
		this.oauthUsers.forEach(user -> this.userService.deleteById(user.getId()));
	}
}

