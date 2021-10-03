package br.com.ifsp.pi.lixt.facade;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import br.com.ifsp.pi.lixt.utils.security.jwt.JwtConfig;
import br.com.ifsp.pi.lixt.utils.security.jwt.JwtSecretKey;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
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
import br.com.ifsp.pi.lixt.utils.mail.templates.ChooserTemplateMail;
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

	private final JwtConfig jwtConfig;
	private final JwtSecretKey jwtSecretKey;
	
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
		
		MailDto mail = ChooserTemplateMail.chooseTemplate(TypeMail.CREATE_ACCOUNT, language);
		Map<String, String> params = CreatorParametersMail.createParamsCreateAccount(user.getUsername(), baseUrl, user.getFirstAccessToken(), language);
		mail = FormatterMail.formatMail(mail, params);
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
		
		var user = this.userService.findByEmail(email);
		
		if(Objects.isNull(user)) {
			throw new NotFoundException("Usuário não encontrado");
		}

		String token = JWT.create()
				.withSubject(email)
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getTokenExpirationAfterMillis()))
				.sign(jwtSecretKey.secretKey());

		MailDto mail = ChooserTemplateMail.chooseTemplate(TypeMail.RESET_PASSWORD, language);
		Map<String, String> params = CreatorParametersMail.createParamsResetPassword(user.getUsername(), baseUrl, token, language);
		mail = FormatterMail.formatMail(mail, params);
		mail.setRecipientTo(email);

		boolean responseSendMail = senderMail.sendEmail(mail);

		if(!responseSendMail) {
			throw new SendMailException();
		}
		
		return HttpStatus.OK.value();
	}

	public Integer validateToken(String token, Languages language) {

		try {
			JWTVerifier verifier = JWT.require(jwtSecretKey.secretKey()).build();
			DecodedJWT decodedJWT = verifier.verify(token);

			String email = decodedJWT.getSubject();
			var user = this.userService.findByEmail(email);
			if(Objects.isNull(user)) {
				throw new NotFoundException("Email não encontrado.");
			}

		} catch (Exception e) {
			return HttpStatus.FORBIDDEN.value();
		}
		return HttpStatus.OK.value();
	}
	
	public String activeUser(String token, Languages language) {
		Integer result = this.userService.activeAccount(token);
		
		if(ValidatorResponse.wasUpdated(result))
			return ActiveAccountView.getView(language);
		else
			return InvalidTokenView.getView(language);
	}

}
