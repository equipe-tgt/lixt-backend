package br.com.ifsp.pi.lixt.utils.mail.templates.config;

import java.util.Map;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;

public abstract class FormatterMail {
	
	private FormatterMail() {}
	
	public static MailDto formatMail(MailDto mail, Map<String, String> values) {
		values.forEach((key, value) -> mail.setMsgHTML(mail.getMsgHTML().replace(key, value)));
		return mail;
	}

}
