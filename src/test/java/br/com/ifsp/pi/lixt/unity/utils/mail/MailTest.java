package br.com.ifsp.pi.lixt.unity.utils.mail;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.SenderMail;

@SpringBootTest
@DisplayName("Testar serviço de email")
class MailTest {
	
	@Autowired
	private SenderMail senderMail;
	
	@Test
	@DisplayName("Enviar email sem erros")
	void sendMail() {
		
		MailDto mailDto = MailDto.builder()
				.title("TESTE EMAIL")
				.msgHTML("ENVIADO COM SUCESSO").build();

		mailDto.setRecipientTo("fabio.mendes@aluno.ifsp.edu.br");
		
		assertTrue(senderMail.sendEmail(mailDto));
	}
	
	@Test
	@DisplayName("Enviar email sem erros com todos os dados")
	void sendMailComplete() throws FileNotFoundException {
		File template = ResourceUtils.getFile("classpath:email/reset-password.html");
		
		MailDto mailDto = MailDto.builder()
				.title("TESTE EMAIL")
				.recipientsCC(Arrays.asList("leo_narita@hotmail.com"))
				.recipientsBCC(Arrays.asList("leo_narita@hotmail.com"))
				.files(Arrays.asList(template))
				.msgHTML("ENVIADO COM SUCESSO").build();

		mailDto.setRecipientTo("leo_narita@hotmail.com");
		
		assertTrue(senderMail.sendEmail(mailDto));
	}
	
	@Test
	@DisplayName("Enviar email com erro")
	void sendMailWithError() {
		
		assertFalse(senderMail.sendEmail(
				MailDto.builder().title("TESTE EMAIL").msgHTML("").build()
		));
		
		assertFalse(senderMail.sendEmail(
				MailDto.builder().title("").msgHTML("ENVIADO COM SUCESSO").build()
		));
		
		assertFalse(senderMail.sendEmail(
				MailDto.builder().title("").msgHTML("").build()
		));
		
		assertFalse(senderMail.sendEmail(
				MailDto.builder().title("TESTE EMAIL").msgHTML("ENVIADO COM SUCESSO").recipientsTO(null).build()
		));
		
		assertFalse(senderMail.sendEmail(
				MailDto.builder().title("TESTE EMAIL").msgHTML("ENVIADO COM SUCESSO").recipientsTO(Collections.emptyList()).build()
		));
	}

}
