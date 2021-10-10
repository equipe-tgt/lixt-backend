package br.com.ifsp.pi.lixt.facade;

import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import br.com.ifsp.pi.lixt.utils.security.jwt.JwtService;
import br.com.ifsp.pi.lixt.utils.views.errorforgotpassword.ErrorForgotPasswordView;
import br.com.ifsp.pi.lixt.utils.views.formnewpassword.FormNewPasswordView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.utils.database.validators.ValidatorResponse;
import br.com.ifsp.pi.lixt.utils.exceptions.DuplicatedDataException;
import br.com.ifsp.pi.lixt.utils.exceptions.NotFoundException;
import br.com.ifsp.pi.lixt.utils.exceptions.SendMailException;
import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.mail.templates.TypeMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.FormatterMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.CreatorParametersMail;
import br.com.ifsp.pi.lixt.utils.security.Users;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.SecurityGenerator;
import br.com.ifsp.pi.lixt.utils.views.activeaccount.ActiveAccountView;
import br.com.ifsp.pi.lixt.utils.views.invalidtoken.InvalidTokenView;
import lombok.RequiredArgsConstructor;

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
			throw new DuplicatedDataException("Email já cadastrado na plataforma");
		}
		if(userService.findByUsername(user.getUsername()) != null) {
			throw new DuplicatedDataException("Usuário já cadastrado na plataforma");
		}
		
		user.setFirstAccessToken(SecurityGenerator.generateToken(user.getEmail()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User userCreated = this.userService.save(user);
		
		MailDto mail = TypeMail.CREATE_ACCOUNT.apply(language);
		Map<String, String> params = CreatorParametersMail.createAccount(user.getUsername(), baseUrl, user.getFirstAccessToken(), language);
		mail = FormatterMail.build(mail, params);
		mail.setRecipientTo(user.getEmail());

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
			throw new NotFoundException("Usuário não encontrado");
		}

		String token = this.jwtService.createJwtToken(email);

		MailDto mail = TypeMail.RESET_PASSWORD.apply(language);
		Map<String, String> params = CreatorParametersMail.resetPassword(user.getUsername(), baseUrl, token, language);
		mail = FormatterMail.build(mail, params);
		mail.setRecipientTo(email);

		boolean responseSendMail = senderMail.sendEmail(mail);

		if(!responseSendMail) {
			throw new SendMailException();
		}

		return HttpStatus.OK.value();
	}

	public String validateToken(String token, Languages language) {

		try {
			String email = this.jwtService.getSubjectByJwtToken(token);
			var user = this.userService.findByUsernameOrEmail(email);
			
			if(Objects.isNull(user)) {
				throw new NotFoundException("Email não encontrado.");
			}

			String newToken = this.jwtService.createJwtToken(email);
			return FormNewPasswordView.getView(language, newToken, baseUrl);

		} catch (Exception e) {
			return ErrorForgotPasswordView.getView(language);
		}
	}

	public String saveNewPassword(String token, Languages language, String newPassword) {

		try {
			String email = this.jwtService.getSubjectByJwtToken(token);
			var user = this.userService.findByUsernameOrEmail(email);
			
			if(Objects.isNull(user) || newPassword.length() < 8) {
				throw new NotFoundException("Email não encontrado.");
			}

			Integer result = this.userService.updatePassword(user.getEmail(), passwordEncoder.encode(newPassword));

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

}
