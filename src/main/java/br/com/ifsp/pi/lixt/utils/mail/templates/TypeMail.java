package br.com.ifsp.pi.lixt.utils.mail.templates;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.createaccount.CreateAccountTemplate;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.forgetpassword.ForgetPasswordTemplate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeMail {
	
	RESET_PASSWORD {
		@Override
		public MailDto apply(Languages language) {
			return ForgetPasswordTemplate.getTemplate(language);
		}
	},
	
	CREATE_ACCOUNT {
		@Override
		public MailDto apply(Languages language) {
			return CreateAccountTemplate.getTemplate(language);
		}
	};
	
	public abstract MailDto apply(Languages language);

}
