package br.com.ifsp.pi.lixt.utils.mail.templates;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.createaccount.CreateAccountTemplate;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.forgetpassword.ForgetPasswordTemplate;

public abstract class ChooserTemplateMail {
	
	private ChooserTemplateMail() {}
	
	public static MailDto chooseTemplate(TypeMail typeMail, Languages language) {
		
		switch(typeMail) {
		
			case RESET_PASSWORD:
				return ForgetPasswordTemplate.getTemplate(language);
				
			case CREATE_ACCOUNT:
				return CreateAccountTemplate.getTemplate(language);
		
			default:
				return null;
		}
	}

}
