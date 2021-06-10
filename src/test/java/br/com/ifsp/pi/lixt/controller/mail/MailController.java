package br.com.ifsp.pi.lixt.controller.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;

@SpringBootTest
public class MailController {
	
	@Autowired
	private SenderMail senderMail;
	
	@Test
	public void encontrarAutoresDosLivros() {
		
		MailDto mailDto = MailDto.builder()
				.title("TESTE EMAIL")
				.msgHTML("ENVIADO COM SUCESSO").build();

		mailDto.setRecipientTo("leo_narita@hotmail.com");
		
		senderMail.sendEmail(mailDto);
	}

}
