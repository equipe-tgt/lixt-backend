package br.com.ifsp.pi.lixt.unity.utils.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.utils.mail.MailDto;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.mail.templates.TypeMail;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.createaccount.CreateAccountTemplateTranslators;
import br.com.ifsp.pi.lixt.utils.mail.templates.dto.forgetpassword.ForgetPasswordTemplateTranslators;

@SpringBootTest
@DisplayName("Testar aplicação de template de email")
public class MailTemplateTest {
		
	@Test
	@DisplayName("Formatar email de criar conta internacionalizado")
	void createAccountTemplate() {
		
		MailDto mail = TypeMail.CREATE_ACCOUNT.apply(Languages.convertStringToEnum("pt-br"));
		assertEquals(mail.getTitle(), CreateAccountTemplateTranslators.translateTitleMail(Languages.convertStringToEnum("pt-br")));
		for(String value : CreateAccountTemplateTranslators.toPortuguese().values())
			assertThat(mail.getMsgHTML()).contains(value);
		
		mail = TypeMail.CREATE_ACCOUNT.apply(Languages.convertStringToEnum("en-us"));
		assertEquals(mail.getTitle(), CreateAccountTemplateTranslators.translateTitleMail(Languages.convertStringToEnum("en-us")));
		for(String value : CreateAccountTemplateTranslators.toEnglish().values())
			assertThat(mail.getMsgHTML()).contains(value);
		
		mail = TypeMail.CREATE_ACCOUNT.apply(Languages.convertStringToEnum(null));
		assertEquals(mail.getTitle(), CreateAccountTemplateTranslators.translateTitleMail(Languages.convertStringToEnum(null)));
		for(String value : CreateAccountTemplateTranslators.toEnglish().values())
			assertThat(mail.getMsgHTML()).contains(value);
		
		mail = TypeMail.CREATE_ACCOUNT.apply(Languages.convertStringToEnum("jp"));
		assertEquals(mail.getTitle(), CreateAccountTemplateTranslators.translateTitleMail(Languages.convertStringToEnum("js")));
		for(String value : CreateAccountTemplateTranslators.toEnglish().values())
			assertThat(mail.getMsgHTML()).contains(value);
	}
	
	@Test
	@DisplayName("Formatar email de resetar senha internacionalizado")
	void resetPasswordTemplate() {
		
		MailDto mail = TypeMail.RESET_PASSWORD.apply(Languages.convertStringToEnum("pt-br"));
		assertEquals(mail.getTitle(), ForgetPasswordTemplateTranslators.translateTitleMail(Languages.convertStringToEnum("pt-br")));
		for(String value : ForgetPasswordTemplateTranslators.toPortuguese().values())
			assertThat(mail.getMsgHTML()).contains(value);
		
		mail = TypeMail.RESET_PASSWORD.apply(Languages.convertStringToEnum("en-us"));
		assertEquals(mail.getTitle(), ForgetPasswordTemplateTranslators.translateTitleMail(Languages.convertStringToEnum("en-us")));
		for(String value : ForgetPasswordTemplateTranslators.toEnglish().values())
			assertThat(mail.getMsgHTML()).contains(value);
		
		mail = TypeMail.RESET_PASSWORD.apply(Languages.convertStringToEnum(null));
		assertEquals(mail.getTitle(), ForgetPasswordTemplateTranslators.translateTitleMail(Languages.convertStringToEnum(null)));
		for(String value : ForgetPasswordTemplateTranslators.toEnglish().values())
			assertThat(mail.getMsgHTML()).contains(value);
		
		mail = TypeMail.RESET_PASSWORD.apply(Languages.convertStringToEnum("jp"));
		assertEquals(mail.getTitle(), ForgetPasswordTemplateTranslators.translateTitleMail(Languages.convertStringToEnum("jp")));
		for(String value : ForgetPasswordTemplateTranslators.toEnglish().values())
			assertThat(mail.getMsgHTML()).contains(value);
	}

}
