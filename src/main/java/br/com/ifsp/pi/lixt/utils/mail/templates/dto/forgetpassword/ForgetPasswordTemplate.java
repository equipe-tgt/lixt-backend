package br.com.ifsp.pi.lixt.utils.mail.templates.dto.forgetpassword;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.AbstractMail;

public abstract class ForgetPasswordTemplate extends AbstractMail {
	
	private ForgetPasswordTemplate() {}
	
	public static MailDto getTemplate(Languages language) {
		
		MailDto mailDto =  MailDto.builder()
				.title(ForgetPasswordTemplateTranslators.translateTitleMail(language))
				.msgHTML(ForgetPasswordTemplateHtml.getMessageHtml())
				.build();
		
		return formatBodyMessageHtml(mailDto, language);
	}

	private static MailDto formatBodyMessageHtml(MailDto mailDto, Languages language) {
		
		switch(language) {

			case PORTUGUESE:
				return translate(mailDto, ForgetPasswordTemplateTranslators.toPortuguese());
			
			case ENGLISH:
			default:
				return translate(mailDto, ForgetPasswordTemplateTranslators.toEnglish());
		}
	}
	
}
