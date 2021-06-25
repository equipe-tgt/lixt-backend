package br.com.ifsp.pi.lixt.facade;

import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.utils.database.validators.ValidatorResponse;
import br.com.ifsp.pi.lixt.utils.exceptions.DuplicatedDataException;
import br.com.ifsp.pi.lixt.utils.exceptions.SendMailException;
import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.ChooseTemplateMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.TypeMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.FormatterMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.config.ParametersMail;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.PasswordGenerator;
import javassist.NotFoundException;
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
	
	public Integer updatePassword(User user) throws NotFoundException {
	
		if(Objects.isNull(this.userService.findByEmail(user.getEmail()))) {
			throw new NotFoundException("Usuário não encontrado");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return this.userService.updatePassword(user.getEmail(), user.getPassword());
	}
	
	@Transactional
	public Integer forgetPassword(String email) throws NotFoundException {
		
		User user = this.userService.findByEmail(email);
		
		if(Objects.isNull(user)) {
			throw new NotFoundException("Usuário não encontrado");
		}
		
		String password = PasswordGenerator.generateRandomPassword();
		
		Integer responseUpdate = this.userService.updatePassword(email, passwordEncoder.encode(password));
		
		if(ValidatorResponse.successfullyUpdated(responseUpdate)) {
			
			MailDto mail = ChooseTemplateMail.chooseTemplate(TypeMail.RESET_PASSWORD);
			mail = FormatterMail.formatMail(mail, ParametersMail.formatParamsResetPassword(user.getUsername(), password));
			mail.setRecipientTo(email);
			
			boolean responseSendMail = senderMail.sendEmail(mail);
			
			if(!responseSendMail) {
				throw new SendMailException();
			}
		}
		
		return responseUpdate;
	}

}
