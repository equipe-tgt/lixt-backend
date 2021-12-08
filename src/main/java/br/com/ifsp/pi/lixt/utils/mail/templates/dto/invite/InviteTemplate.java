package br.com.ifsp.pi.lixt.utils.mail.templates.dto.invite;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.AbstractMail;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InviteTemplate extends AbstractMail {
	
	public static MailDto getTemplate(Languages language) {
		
		MailDto mailDto =  MailDto.builder()
				.title(InviteTemplateTranslators.translateTitleMail(language))
				.msgHTML(InviteTemplateHtml.getMessageHtml())
				.build();
		
		return formatBodyMessageHtml(mailDto, language);
	}

	private static MailDto formatBodyMessageHtml(MailDto mailDto, Languages language) {
		
		switch(language) {

			case PORTUGUESE:
				return translate(mailDto, InviteTemplateTranslators.toPortuguese());
			
			case ENGLISH:
			default:
				return translate(mailDto, InviteTemplateTranslators.toEnglish());
		}
	}

}
