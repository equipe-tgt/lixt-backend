package br.com.ifsp.pi.lixt.utils.mail.templates;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.CreateAccountTemplate;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.ForgetPasswordTemplate;

public abstract class ChooserTemplateMail {
	
	private ChooserTemplateMail() {}
	
	public static MailDto chooseTemplate(TypeMail typeMail) {
		
		switch(typeMail) {
		
			case RESET_PASSWORD:
				return ForgetPasswordTemplate.getTemplate();
				
			case CREATE_ACCOUNT:
				return CreateAccountTemplate.getTemplate();
		
			default:
				return null;
		}
	}

}
