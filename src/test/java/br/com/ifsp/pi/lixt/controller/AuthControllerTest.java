package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.instantiator.UserDtoInstantior;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseGet;
import br.com.ifsp.pi.lixt.utils.tests.response.plainvalue.ValidatorStatusResponsePostPlainValue;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoint Auth")
@TestMethodOrder(OrderAnnotation.class)
class AuthControllerTest {

	@Autowired
	private AuthController authController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	private UserDto user;
	
	@BeforeAll
	void registerUser() throws Exception {
		user = (UserDto) authController.register(UserDtoInstantior.createUser("user", "user", "user@hotmail.com", "123")).getBody();
		this.authController.activeUser(this.userService.findFirstAccesTokenById(user.getId()));
		
		assertAll(
				() -> assertThat(user).isNotNull(),
				() -> assertThat(user.getPassword()).isNull()
		);
	}
	
	@Test
	@DisplayName("Atualizar senha")
	@Order(1)
	void updatePassword() throws Exception {
		ValidatorStatusResponsePostPlainValue.isOk(mockMvc, UserDtoInstantior.createUser("user", "123"), "/auth/update-password", "456");
		assertNotNull(RequestOauth2.authenticate(mockMvc, UserDtoInstantior.createUser("user", "456")));
	}
	
	@Test
	@DisplayName("Encontrar dados do usuário através do token previamente armazenado e não expirado")
	@Order(2)
	void findDataUser() throws Exception {
		ValidatorStatusResponseGet.isOk(mockMvc, UserDtoInstantior.createUser("user", "456"), "/auth/data-user");
	}
	
	@Test
	@DisplayName("Criar um usuário já com email existente, gerando exceção")
	void creatingExistingUserUsingEmail() throws Exception {
		UserDto existingUser = UserDtoInstantior.createUser("user", "user", "user@hotmail.com", "123");
		ResponseEntity<Object> response = authController.register(existingUser);
		
		assertAll(
				() -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT),
				() -> assertThat(response.getBody()).isEqualTo("Email já cadastrado na plataforma")
		);
	}
	
	@Test
	@DisplayName("Criar um usuário com username já existente, gerando exceção")
	void creatingExistingUserUsingUsername() throws Exception {
		UserDto existingUser = UserDtoInstantior.createUser("user", "user", "tes@hotmail.com", "123");
		ResponseEntity<Object> response = authController.register(existingUser);
		
		assertAll(
				() -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT),
				() -> assertThat(response.getBody()).isEqualTo("Usuário já cadastrado na plataforma")
		);	
	}
	
	@Test
	@DisplayName("Gerar nova senha para usuário existente")
	void newPasswordExistingUser() throws Exception {
		assertThat(authController.forgetPassword("user@hotmail.com").getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Gerar nova senha para usuário inexistente")
	void newPasswordUnexistingUser() throws Exception {
		assertThat(authController.forgetPassword("bob@email.com").getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@AfterAll
	void deleteUser() {
		this.userService.deleteById(user.getId());
	}

}
