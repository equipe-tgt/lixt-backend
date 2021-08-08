package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
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
import br.com.ifsp.pi.lixt.dto.ListMembersDto;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.dto.specific.InviteDto;
import br.com.ifsp.pi.lixt.instantiator.ListOfItemsDtoInstantior;
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
@DisplayName("Testar endpoint ListOfItems e ListMembersController")
@TestMethodOrder(OrderAnnotation.class)
class ListOfItemsControllerTest {
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private List<ListOfItemsDto> listsOfItems = new ArrayList<>();
	private List<UserDto> oauthUsers = new ArrayList<>();
	private List<ListMembersDto> listMembers = new ArrayList<>();
	private String token;
	
	@BeforeAll
	void registerUserAndList() throws Exception {
		
		UserDtoData.dataForListControllerTest().forEach(user -> {
			oauthUsers.add((UserDto) this.authController.register(user).getBody());
			oauthUsers.get(oauthUsers.size() - 1).setPassword("123");
			this.authController.activeUser(this.userService.findFirstAccesTokenById(oauthUsers.get(oauthUsers.size() - 1).getId()));
		});
		
		for(UserDto oauthUser : oauthUsers) {
			token = RequestOauth2.authenticate(mockMvc, oauthUser);
			
			listsOfItems.add(
					(ListOfItemsDto) RequestWithResponse.createPostRequestJson(mockMvc, "/list", ListOfItemsDtoDataJson.initializeValues(), token, ListOfItemsDto.class)
			);
		}
		
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));

		for(int i=1; i<5; i++) {
			listMembers.add(
					(ListMembersDto) RequestWithResponse.createPostRequestJson(mockMvc, "/membersList/send-invite/" + this.listsOfItems.get(0).getId(), oauthUsers.get(i).getUsername(), token, ListMembersDto.class)
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
				(ListMembersDto) RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/accept-invite/" + this.listMembers.get(1).getId(), token, ListMembersDto.class)
		);
		
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(3));
		listMembers.set(
				2, 
				(ListMembersDto) RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/reject-invite/" + this.listMembers.get(2).getId(), token, ListMembersDto.class)
		);
		
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(4));
		listMembers.set(
				3, 
				(ListMembersDto) RequestWithResponse.createGetRequestJson(mockMvc, "/membersList/reject-invite/" + this.listMembers.get(3).getId(), token, ListMembersDto.class)
		);
		
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/membersList/" + listMembers.get(3).getId());
		this.listMembers.remove(3);
	}
	
	@DisplayName("Encontrar as listas do usuário pelo token")
	@Test
	@Order(1)
	void searchUsersLists() throws Exception {
		
		List<Integer> expectedValues = Arrays.asList(1, 2, 2, 1, 1, 1, 1);
		
		for(int i=0; i<oauthUsers.size(); i++) {
			token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(i));
			
			List<ListOfItemsDto> list = RequestWithResponseList.createGetRequestJson(mockMvc, "/list/by-user", token, ListOfItemsDto.class)
					.stream().map(element -> (ListOfItemsDto)element).collect(Collectors.toList());
			
			assertThat(list).hasSizeGreaterThan(0);
			assertThat(list).hasSize(expectedValues.get(i));
		}
	}
	
	@DisplayName("Deletar lista com erro")
	@Test
	@Order(2)
	void deleteListWithError() throws Exception {
		for(int i=1; i<oauthUsers.size(); i++) {
			ValidatorStatusResponseDelete.isForbidden(mockMvc, oauthUsers.get(i), "/list/" + this.listsOfItems.get(0).getId());
		}
	}
	
	@DisplayName("Atualizar lista")
	@Test
	@Order(3)
	void updateList() throws Exception {
		this.listsOfItems.get(0).setDescription("MUDANDO DESC");
		String content = ListOfItemsDtoInstantior.updateListJson(this.listsOfItems.get(0));
		ValidatorStatusResponsePut.isOk(mockMvc, oauthUsers.get(0), "/list/" + this.listsOfItems.get(0).getId(), content);
		
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		ListOfItemsDto list = (ListOfItemsDto) RequestWithResponse.createGetRequestJson(mockMvc, "/list/" + this.listsOfItems.get(0).getId(), token, ListOfItemsDto.class);
		assertThat(list.getDescription()).isEqualTo("MUDANDO DESC");
	}
	
	@DisplayName("Atualizar lista com erro forbidden")
	@Test
	@Order(4)
	void updateListWithErrorForbidden() throws Exception {
		this.listsOfItems.get(0).setDescription("MUDANDO DESC 2");
		String content = ListOfItemsDtoInstantior.updateListJson(this.listsOfItems.get(0));

		for(int i=1; i<oauthUsers.size(); i++) {
			ValidatorStatusResponsePut.isForbidden(mockMvc, oauthUsers.get(i), "/list/" + this.listsOfItems.get(0).getId(), content);
		}
	}
	
	@DisplayName("Atualizar lista com erro")
	@Test
	@Order(4)
	void updateListWithError() throws Exception {
		this.listsOfItems.get(0).setDescription("MUDANDO DESC 3");
		String content = ListOfItemsDtoInstantior.updateListJson(this.listsOfItems.get(0));

		for(int i=1; i<oauthUsers.size(); i++) {
			ValidatorStatusResponsePut.isPreconditionFailed(mockMvc, oauthUsers.get(i), "/list/" + 0, content);
		}
	}
	
	@DisplayName("Sair da lista com erro")
	@Test
	@Order(5)
	void getoutListWithError() throws Exception {
		for(int i=2; i<oauthUsers.size(); i++) {
			ValidatorStatusResponseDelete.isForbidden(mockMvc, oauthUsers.get(i), "/membersList/" + this.listMembers.get(0).getId());
		}
	}
	
	@DisplayName("Alterar status do convite com erro")
	@Test
	@Order(6)
	void alterStatusWithError() throws Exception {
		for(int i=2; i<oauthUsers.size(); i++) {
			ValidatorStatusResponseGet.isForbidden(mockMvc, oauthUsers.get(i), "/membersList/accept-invite/" + this.listMembers.get(0).getId());
		}
	}
	
	@DisplayName("Enviar convite com erro")
	@Test
	@Order(7)
	void sendInviteWithError() throws Exception {
		ValidatorStatusResponsePost.isForbidden(mockMvc, oauthUsers.get(1), "/membersList/send-invite/" + this.listsOfItems.get(0).getId(), "teste10");
		ValidatorStatusResponsePost.isNotFound(mockMvc, oauthUsers.get(0), "/membersList/send-invite/" + this.listsOfItems.get(0).getId(), "non-existente");
		ValidatorStatusResponsePost.isConflict(mockMvc, oauthUsers.get(0), "/membersList/send-invite/" + this.listsOfItems.get(0).getId(), "teste5");
	}
	
	@DisplayName("Buscar convites enviados e recebidos")
	@Test
	@Order(8)
	void findInvites() throws Exception {
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		
		List<InviteDto> invitesSent = RequestWithResponseList.createGetRequestJson(mockMvc, "/membersList/sent", token, InviteDto.class)
				.stream().map(element -> (InviteDto)element).collect(Collectors.toList());
		
		List<InviteDto> invitesReceived = RequestWithResponseList.createGetRequestJson(mockMvc, "/membersList/received", token, InviteDto.class)
				.stream().map(element -> (InviteDto)element).collect(Collectors.toList());	
		
		assertThat(invitesSent).hasSize(3);
		assertThat(invitesReceived).isEmpty();
	}
	
	@DisplayName("Sair da lista")
	@Test
	@Order(9)
	void getoutList() throws Exception {
		token = RequestOauth2.authenticate(mockMvc, oauthUsers.get(0));
		ListMembersDto invite = (ListMembersDto) RequestWithResponse.createPostRequestJson(mockMvc, "/membersList/send-invite/" + this.listsOfItems.get(0).getId(), oauthUsers.get(6).getUsername(), token, ListMembersDto.class);
		
		ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(6), "/membersList/" + invite.getId());
	}
	
	@AfterAll
	void deleteUsersAndList() throws Exception {
		for(ListMembersDto listMember: listMembers)
			ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(0), "/membersList/" + listMember.getId());
		
		for(int i=0; i<oauthUsers.size(); i++) {
			ValidatorStatusResponseDelete.isOk(mockMvc, oauthUsers.get(i), "/list/" + this.listsOfItems.get(i).getId());
		}
		
		this.oauthUsers.forEach(user -> this.userService.deleteById(user.getId()));
	}
}
