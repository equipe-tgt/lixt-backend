package br.com.ifsp.pi.lixt.facade;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.utils.exception.DuplicatedDataException;
import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.ChooseTemplateMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.TypeMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.FormatterMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.ParametersMail;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.PasswordGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacade {
	
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final SenderMail senderMail;
	
	public User register(User user) {

		if(userService.findByEmail(user.getEmail()) != null) {
			throw new DuplicatedDataException("Email já cadastrado na plataforma");
		}
		if(userService.findByUsername(user.getUsername()) != null) {
			throw new DuplicatedDataException("Usuário já cadastrado na plataforma");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));		
		return this.userService.save(user);
	}
	
	public Integer updatePassword(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return this.userService.updatePassword(user.getEmail(), user.getPassword());
	}
	
	@Transactional
	public Integer forgetPassword(String email) {
		String password = PasswordGenerator.generateRandomPassword();
		
		Integer result = this.userService.updatePassword(
				email, passwordEncoder.encode(password)
		);
		
		if(result == 1) {
			MailDto mail = ChooseTemplateMail.chooseTemplate(TypeMail.RESET_PASSWORD);
			mail = FormatterMail.formatMail(mail, ParametersMail.formatParamsResetPassword(this.userService.findUsernameByEmail(email), password));
			mail.setRecipientTo(email);
			senderMail.sendEmail(mail);
		}
		
		return result;
	}

}
