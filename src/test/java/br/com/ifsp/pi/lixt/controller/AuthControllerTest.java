package br.com.ifsp.pi.lixt.controller;

import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.instantiator.UserDtoInstantiator;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;
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
	void registerUser() throws Exception {
		user = (UserDto) authController.register(UserDtoInstantiator.createUser("user", "user", "user@hotmail.com", "123"), null).getBody();
		this.authController.activeUser(this.userService.findFirstAccesTokenById(user.getId()), null);

		assertAll(
				() -> assertThat(user).isNotNull(),
				() -> assertThat(user.getPassword()).isNull()
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
	@DisplayName("Processo de resetar senha")
	void newPasswordExistingUser() throws Exception {
		assertThat(authController.forgetPassword("user@hotmail.com", null).getStatusCode()).isEqualTo(HttpStatus.OK);
		String token = this.userService.findResetPasswordTokenById(user.getId());

		String view1 = FormNewPasswordView.getView(Languages.ENGLISH, token, "http://localhost:8080");
		for(String key : FormNewPasswordViewTranslators.toEnglish().keySet())
			view1 = view1.replace(key, FormNewPasswordViewTranslators.toEnglish().get(key));

		String view2 = ErrorForgotPasswordView.getView(Languages.ENGLISH);
		for(String key : ErrorForgotPasswordViewTranslators.toEnglish().keySet())
			view2 = view2.replace(key, InvalidTokenViewTranslators.toEnglish().get(key));

		assertThat(authController.validateToken(token, null)).isEqualTo(view1);
		assertThat(authController.receiveNewPassword(token, null, "")).isEqualTo(ErrorForgotPasswordView.getView(Languages.ENGLISH));
		assertThat(authController.receiveNewPassword(token + "aaa", null, "")).isEqualTo(ErrorForgotPasswordView.getView(Languages.ENGLISH));
		assertThat(authController.receiveNewPassword(token, null, "12345678")).isEqualTo(ActiveAccountView.getView(Languages.ENGLISH));

		assertThat(authController.validateToken(token, null)).isEqualTo(view2);
		assertThat(authController.receiveNewPassword(token, null, "12345678")).isEqualTo(ErrorForgotPasswordView.getView(Languages.ENGLISH));

		assertThat(authController.forgetPassword("k@hotmail.com", null).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Ativar usuário sem sucesso")
	void activeUserUncessfully() {
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
		this.userService.desactiveAccount(user.getId());
		this.userService.deleteById(user.getId());
	}

}