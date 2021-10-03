package br.com.ifsp.pi.lixt.utils.mail.templates.dto.createaccount;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.AbstractMail;

public abstract class CreateAccountTemplate extends AbstractMail {

	private CreateAccountTemplate() {}
	
	public static MailDto getTemplate(Languages language) {
		
		MailDto mailDto = MailDto.builder()
				.title(CreateAccountTemplateTranslators.translateTitleMail(language))
				.msgHTML(CreateAccountTemplateHtml.getMessageHtml())
				.build();
		
		return formatBodyMessageHtml(mailDto, language);
	}
	
	private static MailDto formatBodyMessageHtml(MailDto mailDto, Languages language) {
		
		switch(language) {

			case PORTUGUESE:
				translate(mailDto, CreateAccountTemplateTranslators.toPortuguese());
				break;
			
			case ENGLISH:
			default:
				translate(mailDto, CreateAccountTemplateTranslators.toEnglish());
				break;
		}
		
		return mailDto;
	}
	
}
