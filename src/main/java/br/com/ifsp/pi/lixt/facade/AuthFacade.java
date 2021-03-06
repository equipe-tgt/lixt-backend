package br.com.ifsp.pi.lixt.facade;

import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.utils.database.validators.ValidatorResponse;
import br.com.ifsp.pi.lixt.utils.exceptions.DuplicatedDataException;
import br.com.ifsp.pi.lixt.utils.exceptions.NotFoundException;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import br.com.ifsp.pi.lixt.utils.exceptions.SendMailException;
import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.mail.templates.TypeMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.CreatorParametersMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.FormatterMail;
import br.com.ifsp.pi.lixt.utils.security.Users;
import br.com.ifsp.pi.lixt.utils.security.jwt.JwtService;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.SecurityGenerator;
import br.com.ifsp.pi.lixt.utils.views.activeaccount.ActiveAccountView;
import br.com.ifsp.pi.lixt.utils.views.errorforgotpassword.ErrorForgotPasswordView;
import br.com.ifsp.pi.lixt.utils.views.formnewpassword.FormNewPasswordView;
import br.com.ifsp.pi.lixt.utils.views.invalidtoken.InvalidTokenView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthFacade {

	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final SenderMail senderMail;
	private final JwtService jwtService;

	@Value("${lixt.base.url}") String baseUrl;

	@Transactional
	public User register(User user, Languages language) {

		if(userService.findByEmail(user.getEmail()) != null) {
			throw new DuplicatedDataException("Email j?? cadastrado na plataforma");
		}
		if(userService.findByUsername(user.getUsername()) != null) {
			throw new DuplicatedDataException("Usu??rio j?? cadastrado na plataforma");
		}

		user.setFirstAccessToken(SecurityGenerator.generateToken(user.getEmail()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User userCreated = this.userService.save(user);

		MailDto mail = TypeMail.CREATE_ACCOUNT.apply(language);
		Map<String, String> params = CreatorParametersMail.createAccount(user.getUsername(), baseUrl, user.getFirstAccessToken(), language);
		FormatterMail.build(mail, params, user.getEmail());

		boolean responseSendMail = senderMail.sendEmail(mail);

		if(!responseSendMail) {
			throw new SendMailException();
		}

		return userCreated;
	}

	public Integer updatePassword(String password) {
		return this.userService.updatePassword(Users.getEmail(), passwordEncoder.encode(password));
	}

	@Transactional
	public Integer forgetPassword(String email, Languages language) {

		var user = this.userService.findByUsernameOrEmail(email);

		if(Objects.isNull(user)) {
			throw new NotFoundException("Usu??rio n??o encontrado");
		}

		String token = this.jwtService.createJwtToken(email);
		user.setResetPasswordToken(token);
		this.userService.save(user);

		MailDto mail = TypeMail.RESET_PASSWORD.apply(language);
		Map<String, String> params = CreatorParametersMail.resetPassword(user.getUsername(), baseUrl, token, language);
		FormatterMail.build(mail, params, email);

		boolean responseSendMail = senderMail.sendEmail(mail);

		if(!responseSendMail) {
			throw new SendMailException();
		}

		return HttpStatus.OK.value();
	}

	public String validateToken(String token, Languages language) {

		try {
			String email = this.jwtService.getSubjectByJwtToken(token);
			var user = this.userService.findByEmailAndToken(email, token);

			if(Objects.isNull(user)) {
				throw new NotFoundException("Solicita????o n??o encontrado.");
			}

			return FormNewPasswordView.getView(language, token, baseUrl);

		} catch (Exception e) {
			return ErrorForgotPasswordView.getView(language);
		}
	}

	public String saveNewPassword(String token, Languages language, String newPassword) {

		try {
			String email = this.jwtService.getSubjectByJwtToken(token);
			var user = this.userService.findByUsernameOrEmail(email);

			if(newPassword.length() < 8)
				throw new PreconditionFailedException("Senha n??o suportada");

			Integer result = this.userService.updatePasswordByToken(user.getEmail(), passwordEncoder.encode(newPassword), token);

			if(ValidatorResponse.wasUpdated(result))
				return ActiveAccountView.getView(language);
			else
				return ErrorForgotPasswordView.getView(language);
		} catch (Exception e) {
			return ErrorForgotPasswordView.getView(language);
		}
	}

	public String activeUser(String token, Languages language) {
		Integer result = this.userService.activeAccount(token);

		if(ValidatorResponse.wasUpdated(result))
			return ActiveAccountView.getView(language);
		else
			return InvalidTokenView.getView(language);
	}


	public UserDto updateUserPreferences(UserDto userDto) {
		userDto = userService.saveOlderCommentsFirst(userDto);
		return userService.saveGlobalCommentsPreferences(userDto);
	}

	public User getUserData(String username) {
		return userService.findByUsernameOrEmail(username);
	}
}
