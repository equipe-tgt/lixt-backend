package br.com.ifsp.pi.lixt.utils.mail.templates;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.createaccount.CreateAccountTemplate;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.forgetpassword.ForgetPasswordTemplate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeMail {
	
	RESET_PASSWORD(1) {
		@Override
		public MailDto apply(Languages language) {
			return ForgetPasswordTemplate.getTemplate(language);
		}
	},
	
	CREATE_ACCOUNT(2) {
		@Override
		public MailDto apply(Languages language) {
			return CreateAccountTemplate.getTemplate(language);
		}
	};
	
	private int id;
	
	public abstract MailDto apply(Languages language);

}
