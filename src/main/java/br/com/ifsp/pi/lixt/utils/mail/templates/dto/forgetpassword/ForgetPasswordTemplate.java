package br.com.ifsp.pi.lixt.utils.mail.templates.dto.forgetpassword;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class ForgetPasswordTemplate {
	
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
				ForgetPasswordTemplateTranslators.translateToPortuguese()
						.forEach((key, value) -> mailDto.setMsgHTML(mailDto.getMsgHTML().replace(key, value)));
				break;
			
			case ENGLISH:
			default:
				ForgetPasswordTemplateTranslators.translateToEnglish()
						.forEach((key, value) -> mailDto.setMsgHTML(mailDto.getMsgHTML().replace(key, value)));
				break;
		}
		
		return mailDto;
	}
	
}
