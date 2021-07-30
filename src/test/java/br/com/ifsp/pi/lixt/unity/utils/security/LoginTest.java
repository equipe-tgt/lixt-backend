package br.com.ifsp.pi.lixt.unity.utils.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.ifsp.pi.lixt.controller.AuthController;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.instantiator.UserDtoInstantior;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar funcionalidades de login")
@TestMethodOrder(OrderAnnotation.class)
class LoginTest {

	@Autowired
	private AuthController authController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private JacksonJsonParser jsonParser = new JacksonJsonParser();
	private OauthUserDto user;
	
	@BeforeAll
	void registerUser() throws Exception {
		user = (OauthUserDto) authController.register(UserDtoInstantior.createUser("user2", "user2", "user2@hotmail.com", "123")).getBody();
		
		assertAll(
				() -> assertThat(user).isNotNull(),
				() -> assertThat(user.getPassword()).isNull()
		);
		
		user.setPassword("123");
	}
	
	@Test
	@DisplayName("Autenticação corretamente por email")
	void loginWithEmail() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.add("grant_type", "password");
		params.add("username", user.getEmail());
		params.add("password", user.getPassword());
		
		mockMvc.perform(post("/oauth/token").params(params).headers(RequestOauth2.getHeaders()).accept("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("Autenticação corretamente por username")
	void loginWithUsername() throws Exception {
		assertNotNull(RequestOauth2.authenticate(mockMvc, user));
	}
	
	@Test
	@DisplayName("Erro na autenticação")
	void loginWithError() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.add("grant_type", "password");
		params.add("username", "failed");
		params.add("password", "failed");
		
		mockMvc.perform(post("/oauth/token").params(params).headers(RequestOauth2.getHeaders()).accept("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	@DisplayName("Erro na autenticação com senha incorreta")
	void loginWithPasswordError() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.add("grant_type", "password");
		params.add("username", user.getUsername());
		params.add("password", "failed");
		
		mockMvc.perform(post("/oauth/token").params(params).headers(RequestOauth2.getHeaders()).accept("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@DisplayName("Atualizar token")
	void refreshToken() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.add("grant_type", "password");
		params.add("username", user.getUsername());
		params.add("password", user.getPassword());
		
		ResultActions result = mockMvc.perform(post("/oauth/token").params(params).headers(RequestOauth2.getHeaders()).accept("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String refreshToken = jsonParser.parseMap(result.andReturn().getResponse().getContentAsString()).get("refresh_token").toString();
		String newToken = RequestOauth2.refreshToken(mockMvc, refreshToken);
		
		mockMvc.perform(ResquestBuilder.createGetRequestJson("/auth/data-user", newToken)).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@AfterAll
	void deleteUser() {
		this.userService.deleteById(user.getId());
	}
	
}

