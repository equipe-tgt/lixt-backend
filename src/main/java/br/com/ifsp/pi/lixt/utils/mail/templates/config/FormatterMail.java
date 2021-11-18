package br.com.ifsp.pi.lixt.utils.mail.templates.config;

import java.util.Map;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;

public abstract class FormatterMail {
	
	private FormatterMail() {}
	
	public static MailDto build(MailDto mail, Map<String, String> values, String... mails) {
		values.forEach((key, value) -> mail.setMsgHTML(mail.getMsgHTML().replace(key, value)));
		mail.setRecipientTo(mails);
		return mail;
	}

}
