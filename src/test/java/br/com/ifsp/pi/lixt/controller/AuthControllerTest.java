package br.com.ifsp.pi.lixt.controller;

import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.instantiator.UserDtoInstantiator;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponse;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponseList;
import br.com.ifsp.pi.lixt.utils.tests.response.ValidatorStatusResponseGet;
import br.com.ifsp.pi.lixt.utils.tests.response.plainvalue.ValidatorStatusResponsePostPlainValue;
import br.com.ifsp.pi.lixt.utils.views.activeaccount.ActiveAccountView;
import br.com.ifsp.pi.lixt.utils.views.errorforgotpassword.ErrorForgotPasswordView;
import br.com.ifsp.pi.lixt.utils.views.errorforgotpassword.ErrorForgotPasswordViewTranslators;
import br.com.ifsp.pi.lixt.utils.views.formnewpassword.FormNewPasswordView;
import br.com.ifsp.pi.lixt.utils.views.formnewpassword.FormNewPasswordViewTranslators;
import br.com.ifsp.pi.lixt.utils.views.invalidtoken.InvalidTokenViewHtml;
import br.com.ifsp.pi.lixt.utils.views.invalidtoken.InvalidTokenViewTranslators;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
	void registerUser() {
		user = (UserDto) authController.register(UserDtoInstantiator.createUser("user", "user", "user@hotmail.com", "123"), null).getBody();
		this.authController.activeUser(this.userService.findFirstAccesTokenById(user.getId()), null);

		assertAll(
				() -> assertThat(user).isNotNull(),
				() -> assertThat(user.getPassword()).isNull(),
				() -> assertTrue(user.isGlobalCommentsChronOrder()),
				() -> assertTrue(user.isOlderCommentsFirst())
		);
	}

	@Test
	@DisplayName("Atualizar senha")
	@Order(1)
	void updatePassword() throws Exception {
		ValidatorStatusResponsePostPlainValue.isOk(mockMvc, UserDtoInstantiator.createUser("user", "123"), "/auth/update-password", "456");
		assertNotNull(RequestOauth2.authenticate(mockMvc, UserDtoInstantiator.createUser("user", "456")));
	}

	@Test
	@DisplayName("Encontrar dados do usuário através do token previamente armazenado e não expirado")
	@Order(2)
	void findDataUser() throws Exception {
		ValidatorStatusResponseGet.isOk(mockMvc, UserDtoInstantiator.createUser("user", "456"), "/auth/data-user");
	}

	@Test
	@DisplayName("Atualizar preferencias de usuario")
	@Order(3)
	void updateUserPreferences() throws Exception {
		user.setPassword("456");
		String token = RequestOauth2.authenticate(mockMvc, user);

		user.setGlobalCommentsChronOrder(false);
		user.setOlderCommentsFirst(false);
		UserDto updatedResponse = RequestWithResponse.createPutRequestJson(mockMvc, "/auth/save-user-preferences",
				UserDtoInstantiator.createUserDtoJson(user), user, UserDto.class);

		assertAll(
				() -> assertFalse(updatedResponse.isOlderCommentsFirst()),
				() -> assertFalse(updatedResponse.isGlobalCommentsChronOrder())
		);

		UserDto updatedUser = RequestWithResponse.createGetRequestJson(mockMvc, "/auth/data-user", token, UserDto.class);
		assertAll(
				() -> assertFalse(updatedUser.isOlderCommentsFirst()),
				() -> assertFalse(updatedUser.isGlobalCommentsChronOrder())
		);

	}

	@Test
	@DisplayName("Criar um usuário já com email existente, gerando exceção")
	void creatingExistingUserUsingEmail() throws Exception {
		UserDto existingUser = UserDtoInstantiator.createUser("user", "user", "user@hotmail.com", "123");
		ResponseEntity<Object> response = authController.register(existingUser, null);

		assertAll(
				() -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT),
				() -> assertThat(response.getBody()).isEqualTo("Email já cadastrado na plataforma")
		);
	}

	@Test
	@DisplayName("Criar um usuário com username já existente, gerando exceção")
	void creatingExistingUserUsingUsername() throws Exception {
		UserDto existingUser = UserDtoInstantiator.createUser("user", "user", "tes@hotmail.com", "123");
		ResponseEntity<Object> response = authController.register(existingUser, null);

		assertAll(
				() -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT),
				() -> assertThat(response.getBody()).isEqualTo("Usuário já cadastrado na plataforma")
		);
	}

	@Test
	@DisplayName("Gerar nova senha para usuário existente")
	void newPasswordExistingUser() throws Exception {
		assertThat(authController.forgetPassword("user@hotmail.com", null).getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DisplayName("Gerar nova senha para usuário inexistente")
	void newPasswordUnexistingUser() throws Exception {
		assertThat(authController.forgetPassword("bob@email.com", null).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Solicitar cadastro de nova senha com token expirado")
	void redefinePasswordWithExpiredToken() {
		String expiredToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2MzM1MjU0MzUsImV4cCI6MTYzMzUyNjU4NywiYXVkIjoiIiwic3ViIjoidXNlckBob3RtYWlsLmNvbSJ9.DTYch2w9iLYGNnPEF7L1scw1uzFLPWDOSzzFWbIi9jU";

		String view = ErrorForgotPasswordView.getView(Languages.ENGLISH);

		for(String key : ErrorForgotPasswordViewTranslators.translateToEnglish().keySet())
			view = view.replace(key, InvalidTokenViewTranslators.toEnglish().get(key));

		assertEquals(this.authController.validateToken(expiredToken, Languages.ENGLISH.getDescription()), view);
	}

	@Test
	@DisplayName("Cadastro de nova senha com token válido e email inexistente")
	void redefinePasswordWithValidTokenAndNonexistentEmail() {

		// Expira em 06/10/2022
		String tokenWithNonexistentEmail = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2MzM1MjU0MzUsImV4cCI6MTY2NTA2NDM4NCwiYXVkIjoiIiwic3ViIjoiYmxhYmxhQGhvdG1haWwuY29tIn0.7W1tDV62L6ZVZZ9kCf1Mj_EPBHqkGFOkWnxBbL3UB5I";

		String view = ErrorForgotPasswordView.getView(Languages.ENGLISH);

		for(String key : ErrorForgotPasswordViewTranslators.translateToEnglish().keySet())
			view = view.replace(key, InvalidTokenViewTranslators.toEnglish().get(key));

		assertEquals(this.authController.validateToken(tokenWithNonexistentEmail, Languages.ENGLISH.getDescription()), view);
	}

	@Test
	@DisplayName("Solicitar formulário para cadastro de nova senha com sucesso")
	void formNewPasswordRequest() {
		// Expira em 06/10/2022
		String jwtValidToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2MzM1MjU0MzUsImV4cCI6MTY2NTA2NTQ4MywiYXVkIjoiIiwic3ViIjoidXNlckBob3RtYWlsLmNvbSJ9.8tTjMrfXU5Iiaky3NL-rXVQYIkg-aKjOjVHnlnrwavU";

		String view = FormNewPasswordView.getView(Languages.ENGLISH, jwtValidToken, "http://localhost:8080");

		for(String key : FormNewPasswordViewTranslators.translateToEnglish().keySet())
			view = view.replace(key, FormNewPasswordViewTranslators.translateToEnglish().get(key));

		assertEquals(this.authController.validateToken(jwtValidToken, Languages.ENGLISH.getDescription()).substring(0, 3681), view.substring(0, 3681));
	}

	@Test
	@DisplayName("Envio de nova senha com token expirado")
	void sendNewPasswordWithExpiredToken() {
		String jwtExpiredToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2MzM1NjM3NjUsImV4cCI6MTYzMzU2Mzk2NywiYXVkIjoiIiwic3ViIjoidXNlckBob3RtYWlsLmNvbSJ9.bkZit634RaAFwRIL0m302O_OST9DZ-A_45OUNw85HEg";

		assertEquals(this.authController.receiveNewPassword(jwtExpiredToken, Languages.ENGLISH.getDescription(), "123456789"),
				ErrorForgotPasswordView.getView(Languages.ENGLISH));
	}

	@Test
	@DisplayName("Envio de senha vazia")
	void recoveryPasswordWithEmptyString() {
		// Expira em 06/10/2022
		String jwtValidToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2MzM1MjU0MzUsImV4cCI6MTY2NTA2NTQ4MywiYXVkIjoiIiwic3ViIjoidXNlckBob3RtYWlsLmNvbSJ9.8tTjMrfXU5Iiaky3NL-rXVQYIkg-aKjOjVHnlnrwavU";

		assertEquals(this.authController.receiveNewPassword(jwtValidToken, Languages.ENGLISH.getDescription(), ""),
				ErrorForgotPasswordView.getView(Languages.ENGLISH));
	}

	@Test
	@DisplayName("Enviar nova senha com sucesso")
	void sendNewPasswordSuccessfully() throws Exception {
		// Expira em 06/10/2022
		String jwtValidToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2MzM1MjU0MzUsImV4cCI6MTY2NTA2NTQ4MywiYXVkIjoiIiwic3ViIjoidXNlckBob3RtYWlsLmNvbSJ9.8tTjMrfXU5Iiaky3NL-rXVQYIkg-aKjOjVHnlnrwavU";

		assertEquals(this.authController.receiveNewPassword(jwtValidToken, Languages.ENGLISH.getDescription(), "123456789"),
				ActiveAccountView.getView(Languages.ENGLISH));
	}

	@Test
	@DisplayName("Ativar usuário sem sucesso")
	void activeUserUnsucessfully() {
		String view = InvalidTokenViewHtml.getView();

		for(String key : InvalidTokenViewTranslators.toEnglish().keySet())
			view = view.replace(key, InvalidTokenViewTranslators.toEnglish().get(key));

		assertEquals(this.authController.activeUser("", null), view);
		assertEquals(this.authController.activeUser("abc", null), view);
		assertEquals(this.authController.activeUser(null, null), view);
	}

	@Test
	@DisplayName("Revogar token")
	void revokeToken() throws Exception {
		UserDto userTR = (UserDto) authController.register(UserDtoInstantiator.createUser("userTR", "userTR", "userTR@hotmail.com", "123"), null).getBody();
		userTR.setPassword("123");
		this.authController.activeUser(this.userService.findFirstAccesTokenById(userTR.getId()), null);

		String token = RequestOauth2.authenticate(mockMvc, userTR);
		assertNotNull(RequestWithResponseList.createGetRequestJson(mockMvc, "/category", token, CategoryDto.class));
		ValidatorStatusResponseGet.isOk(mockMvc, userTR, "/auth/revoke-token");
		mockMvc.perform(ResquestBuilder.createDeleteRequestJson("/category", token)).andExpect(MockMvcResultMatchers.status().isUnauthorized());

		this.userService.deleteById(userTR.getId());
	}

	@AfterAll
	void deleteUser() {

		this.userService.deleteById(user.getId());
	}

}
