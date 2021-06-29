//package br.com.ifsp.pi.lixt.controller.operations;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.json.JacksonJsonParser;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Service;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import br.com.ifsp.pi.lixt.controller.AuthController;
//import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class AuthControllerOperations {
//	
//	@Autowired
//	private AuthController authController;
//	
//	@Autowired
//	private MockMvc mockMvc;
//	
//	public void registerUser(UserDto user) {
//		
//		UserDto createdUser = (UserDto) authController.register(user).getBody();
//		
//		assertThat(createdUser).isNotNull();
//		assertThat(createdUser.getPassword()).isNull();
//	}
//	
//	public String login(UserDto user) throws Exception {
//		
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("grant_type", "password");
//		params.add("username", user.getUsername());
//		params.add("password", user.getPassword());
//		
//		HttpHeaders httpHeaders = new HttpHeaders();
////		httpHeaders.add("Authorization", generateBasicAuthToken("client", "123456"));
//		httpHeaders.add("Authorization", "basic Y2xpZW50OjEyMzQ1Ng==");
//		httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
//		
//		ResultActions result = mockMvc.perform(post("/oauth/token").params(params).headers(httpHeaders).accept("application/json;charset=UTF-8"))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
//		
//		String resultString = result.andReturn().getResponse().getContentAsString();
//
//		JacksonJsonParser jsonParser = new JacksonJsonParser();
//		return "bearer ".concat(jsonParser.parseMap(resultString).get("access_token").toString());
//	}
//	
//	public String generateBasicAuthToken(String clientID, String secretID) {
//		return "basic ".concat(Base64.getEncoder().encodeToString(clientID.concat(":").concat(clientID).getBytes(StandardCharsets.ISO_8859_1)));
//	}
//
//}
