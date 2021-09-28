package br.com.ifsp.pi.lixt.utils.mail.templates.dto.createaccount;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class CreateAccountTemplate {

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
				CreateAccountTemplateTranslators.translateToPortuguese()
						.forEach((key, value) -> mailDto.setMsgHTML(mailDto.getMsgHTML().replace(key, value)));
				break;
			
			case ENGLISH:
			default:
				CreateAccountTemplateTranslators.translateToEnglish()
						.forEach((key, value) -> mailDto.setMsgHTML(mailDto.getMsgHTML().replace(key, value)));
				break;
		}
		
		return mailDto;
	}
	
}
