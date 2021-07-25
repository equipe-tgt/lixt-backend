package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
import org.springframework.test.web.servlet.MockMvc;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.instantiator.UserDtoInstantior;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
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
	
	private OauthUserDto user;
	
	@BeforeAll
	void registerUser() throws Exception {
		user = (OauthUserDto) authController.register(UserDtoInstantior.createUser("user", "user", "user@hotmail.com", "123")).getBody();
		
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
	}
	
	@Test
	@DisplayName("Encontrar dados do usuário através do token previamente armazenado e não expirado")
	@Order(2)
	void findDataUser() throws Exception {
		ValidatorStatusResponseGet.isOk(mockMvc, UserDtoInstantior.createUser("user", "456"), "/auth/data-user");
	}
	
	@Test
	@DisplayName("Criar um usuário já existente, gerando exceção")
	void creatingExistingUser() throws Exception {
		OauthUserDto existingUser = UserDtoInstantior.createUser("user", "user", "user@hotmail.com", "123");
		assertThat(authController.register(existingUser).getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
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
