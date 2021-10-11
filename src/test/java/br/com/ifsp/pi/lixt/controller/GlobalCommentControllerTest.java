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
import br.com.ifsp.pi.lixt.instantiator.GlobalCommentDtoInstantior;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantior;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

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
		category = categoryController.save(CategoryDto.builder().name("Alimentação").build());
		product = this.productController.save(ProductMapper.entityToDto(ProductDtoInstantior.createProduct("Arroz", category, MeasureType.KG, 5)));

		UserDtoData.dataForProductOfListControllerTest().subList(0, 2).forEach(user -> {
			oauthUsers.add((UserDto) this.authController.register(user, null).getBody());
			oauthUsers.get(oauthUsers.size() - 1).setPassword("123");
			this.authController.activeUser(this.userService.findFirstAccesTokenById(oauthUsers.get(oauthUsers.size() - 1).getId()), null);
		});

		tokenOauthUser0 = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));

		tokenOauthUser1 = RequestOauth2.authenticate(mockMvc, oauthUsers.get(1));

		this.listOfItems = RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), tokenOauthUser0, ListOfItemsDto.class);

		assertThat(listOfItems).isNotNull();

		String productOfListJson = ProductOfListDtoDataJson.initializeValues(this.listOfItems, ProductMapper.dtoToEntity(this.product)).get(0);

		productOfListDto = RequestWithResponse.createPostRequestJson(mockMvc, "/productOfList", productOfListJson, tokenOauthUser0, ProductOfListDto.class);
	}

	@Test
	@DisplayName("Adicionar e deletar comentários globais")
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
	@DisplayName("Atualizar comentário global")
	void updateGlobalComments() throws Exception {
		String globalComment1 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(0);

		GlobalCommentDto globalCommentDto1 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment1, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);

		globalCommentDto1.setContent("Comentário global [atualizado]");

		ValidatorStatusResponsePut.isOk(mockMvc,
				oauthUsers.get(0),
				"/globalComment",
				GlobalCommentDtoInstantior.createGlobalCommentJson(globalCommentDto1));

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc,
								"/productOfList/" + this.productOfListDto.getId() + "/comments",
								tokenOauthUser0,
								AllCommentsDto.class)
						.getGlobalCommentsDto().get(0).getContent()
		).isEqualTo("Comentário global [atualizado]");

		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/globalComment/" + globalCommentDto1.getId());

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isZero();
	}

	@Test
	@DisplayName("Mesmo comentário global em listas diferentes")
	void sameGlobalCommentInTwoLists() throws Exception {
		String globalComment1 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(0);

		GlobalCommentDto globalCommentDto1 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment1, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);


		ListOfItemsDto otherList = RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), tokenOauthUser0, ListOfItemsDto.class);

		assertThat(otherList).isNotNull();

		String productOfListJson = ProductOfListDtoDataJson.initializeValues(otherList, ProductMapper.dtoToEntity(this.product)).get(0);

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
	@DisplayName("Ver comentário global na lista compartilhada mesmo não sendo o autor")
	void listMemberReadGlobalComment() throws Exception {

		String globalComment1 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(0);

		GlobalCommentDto globalCommentDto1 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment1, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);


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
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/membersList/" + listMember.getId());
	}

	@Test
	@DisplayName("Apenas o autor pode editar comentários globais")
	void nonAuthorEditGlobalComment() throws Exception {
		String globalComment1 = GlobalCommentDtoJson.initializeValues(oauthUsers.get(0), this.productOfListDto).get(0);

		GlobalCommentDto globalCommentDto1 = RequestWithResponse.createPostRequestJson(mockMvc, "/globalComment", globalComment1, oauthUsers.get(0), GlobalCommentDto.class);

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc, "/productOfList/" + this.productOfListDto.getId() + "/comments", tokenOauthUser0, AllCommentsDto.class)
						.getGlobalCommentsDto().size()
		).isEqualTo(1);

		globalCommentDto1.setContent("Comentário global [atualizado]");

		ValidatorStatusResponsePut.isOk(mockMvc,
				oauthUsers.get(0),
				"/globalComment",
				GlobalCommentDtoInstantior.createGlobalCommentJson(globalCommentDto1));

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc,
								"/productOfList/" + this.productOfListDto.getId() + "/comments",
								tokenOauthUser0,
								AllCommentsDto.class)
						.getGlobalCommentsDto().get(0).getContent()
		).isEqualTo("Comentário global [atualizado]");


		ListMembersDto listMember = RequestWithResponse.createPostRequestJson(mockMvc, "/membersList/send-invite/" + this.listOfItems.getId(), oauthUsers.get(1).getUsername(), tokenOauthUser0, ListMembersDto.class);

		RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/accept-invite/" + listMember.getId(), tokenOauthUser1, ListMembersDto.class);

		globalCommentDto1.setContent("Comentário global [atualizado 2]");

		ValidatorStatusResponsePut.isForbidden(mockMvc,
				oauthUsers.get(1),
				"/globalComment",
				GlobalCommentDtoInstantior.createGlobalCommentJson(globalCommentDto1));

		assertThat(
				RequestWithResponse.createGetRequestJson(mockMvc,
								"/productOfList/" + this.productOfListDto.getId() + "/comments",
								tokenOauthUser1,
								AllCommentsDto.class)
						.getGlobalCommentsDto().get(0).getContent()
		).isEqualTo("Comentário global [atualizado]");



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

