package br.com.ifsp.pi.lixt.test.unity.utils.mail;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

		mailDto.setRecipientTo("leo_narita@hotmail.com");
		
		boolean response = senderMail.sendEmail(mailDto);
		
		assertThat(response).isTrue();
	}

}
