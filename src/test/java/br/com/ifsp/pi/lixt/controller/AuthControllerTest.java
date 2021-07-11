package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoint Auth")
class AuthControllerTest {

	@Autowired
	private AuthController authController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	private String token;
	private OauthUserDto createdUser;
	
	@BeforeAll
	void registerUser() throws Exception {
		
		OauthUserDto user = OauthUserDto.builder()
				.name("user")
				.username("user")
				.email("user@hotmail.com")
				.password("123")
				.build();
		
		createdUser = (OauthUserDto) authController.register(user).getBody();
		
		assertThat(createdUser).isNotNull();
		assertThat(createdUser.getPassword()).isNull();

		token = RequestOauth2.authenticate(mockMvc, user);
	}
	
	@Test
	@DisplayName("Encontrar dados do usuário através do token previamente armazenado e não expirado")
	void findDataUser() throws Exception {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", token);
		
		mockMvc.perform(get("/auth/data-user").headers(httpHeaders).accept("application/json;charset=UTF-8"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	@DisplayName("Criar um usuário já existente")
	void creatingExistingUser() throws Exception {
		OauthUserDto existingUser = OauthUserDto.builder()
				.name("user")
				.username("user")
				.email("user@hotmail.com")
				.password("123")
				.build();
		
		ResponseEntity<Object> response = authController.register(existingUser);
		
		assertThat(HttpStatus.CONFLICT.equals(response.getStatusCode()));
		
	}
	
	@Test
	@DisplayName("Atualizar senha")
	void updatePassword() throws Exception{
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", token);
		
		mockMvc.perform(post("/auth/update-password").headers(httpHeaders).contentType(MediaType.TEXT_PLAIN_VALUE).content("456")
				.accept("application/json;charset=UTF-8"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

	}
	
	@Test
	@DisplayName("Gerar nova senha para usuário existente")
	void newPasswordExistingUser() throws Exception {
		assertThat(HttpStatus.OK.equals(authController.forgetPassword("user@hotmail.com").getStatusCode()));
	}
	
	@Test
	@DisplayName("Gerar nova senha para usuário inexistente")
	void newPasswordUnexistingUser() throws Exception {
		assertThat(HttpStatus.NOT_FOUND.equals(authController.forgetPassword("bob@email.com").getStatusCode()));
	}
	
	/*@Test
	@DisplayName("Atualizar senha de usuário não encontrado")
	void updatePasswordforUnexistingUser() throws Exception{
		OauthUserDto user = OauthUserDto.builder()
				.name("ana")
				.username("ana")
				.email("ana@hotmail.com")
				.password("789")
				.build();
		
		String fakeToken = RequestOauth2.authenticate(mockMvc, user);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", fakeToken);
		
		mockMvc.perform(post("/auth/update-password")
				.headers(httpHeaders)
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content("101"))
				.andExpect(status().isNotFound());
	}*/
	
	@Test
	@DisplayName("Buscar dados do usuário através do token")
	void findDataUserTest() throws Exception{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", token);
		
		mockMvc.perform(get("/auth/data-user").headers(httpHeaders).accept("application/json;charset=UTF-8"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
	}
	
	@AfterAll
	void deleteUser() {
		this.userService.deleteById(createdUser.getId());
	}

}
