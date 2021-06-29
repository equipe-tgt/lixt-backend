//package br.com.ifsp.pi.lixt.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import br.com.ifsp.pi.lixt.controller.operations.AuthControllerOperations;
//import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DisplayName("Testar endpoint Auth")
//class AuthControllerTest {
//
//	@Autowired
//	private AuthControllerOperations authControllerOperationsTest;
//	
//	@Autowired
//	private MockMvc mockMvc;
//	
//	private String token;
//	private HttpHeaders httpHeaders = new HttpHeaders();
//	
//	@BeforeAll
//	void registerUser() throws Exception {
//		
//		UserDto user = UserDto.builder()
//				.name("leo")
//				.username("leo")
//				.email("leo_narita@hotmail.com")
//				.password("123")
//				.build();
//		
//		this.authControllerOperationsTest.registerUser(user);
//
//		token = this.authControllerOperationsTest.login(user);
//		httpHeaders.add("Authorization", token);		
//	}
//	
//	@Test
//	@DisplayName("Encontrar dados do usuário através do token previamente armazenado e não expirado")
//	void findDataUser() throws Exception {
//		
//		mockMvc.perform(get("/auth/data-user").headers(httpHeaders).accept("application/json;charset=UTF-8"))
//			.andExpect(MockMvcResultMatchers.status().isOk())
//			.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
//	}
//
//}
