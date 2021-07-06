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
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

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

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("username", user.getUsername());
		params.add("password", user.getPassword());
		
		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.add("Authorization", generateBasicAuthToken("client", "123456"));
		httpHeaders.add("Authorization", "basic Y2xpZW50OjEyMzQ1Ng==");
		httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
		
		ResultActions result = mockMvc.perform(post("/oauth/token").params(params).headers(httpHeaders).accept("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		token = "bearer ".concat(jsonParser.parseMap(resultString).get("access_token").toString());
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
	
	@AfterAll
	void deleteUser() {
		this.userService.deleteById(createdUser.getId());
	}

}
