package br.com.ifsp.pi.lixt.utils.mail.templates.dto;

import java.util.Map;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbstractMail {
	
	protected static MailDto translate(MailDto mailDto, Map<String, String> params) {
		params.forEach((key, value) -> mailDto.setMsgHTML(mailDto.getMsgHTML().replace(key, value)));
		return mailDto;
	}

}
