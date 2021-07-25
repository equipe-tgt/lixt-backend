package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.test.web.servlet.MockMvc;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.data.dto.UserDtoData;
import br.com.ifsp.pi.lixt.data.dto.json.ListOfItemsDtoDataJson;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponse;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponseList;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseDelete;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoint ListOfItems")
@TestMethodOrder(OrderAnnotation.class)
class ListOfItemsControllerTest {
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private ListOfItemsDto listOfItems;
	private List<OauthUserDto> oauthUsers = new ArrayList<>();
	private List<UserDto> users = new ArrayList<>();

	@BeforeAll
	void registerUserAndList() throws Exception {
		
		UserDtoData.dataForListControllerTest().forEach(user -> {
			oauthUsers.add((OauthUserDto) this.authController.register(user).getBody());
			users.add(UserMapper.dtoOauthToDto(oauthUsers.get(oauthUsers.size() - 1)));
			oauthUsers.get(oauthUsers.size() - 1).setPassword("123");
		});
	}
	
	@DisplayName("Encontrar as listas do usuário pelo token")
	@Test
	void searchUsersLists() throws Exception {
		
		String token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		this.listOfItems = (ListOfItemsDto) RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), token, ListOfItemsDto.class);
	
		List<ListOfItemsDto> list = RequestWithResponseList.createGetRequestJson(mockMvc, "/list/by-user", token, ListOfItemsDto.class)
				.stream().map(element -> (ListOfItemsDto)element).collect(Collectors.toList());
		
		assertThat(list).hasSizeGreaterThan(0);
		assertThat(list.get(0).getNameList()).isEqualTo(this.listOfItems.getNameList());
	}
	
	
	@AfterAll
	void deleteUsersAndList() throws Exception {
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/list/" + this.listOfItems.getId());
		this.oauthUsers.forEach(user -> this.userService.deleteById(user.getId()));
	}
}
