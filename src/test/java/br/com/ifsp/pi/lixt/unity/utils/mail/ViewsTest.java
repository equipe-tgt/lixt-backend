package br.com.ifsp.pi.lixt.unity.utils.mail;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.views.activeaccount.ActiveAccountView;
import br.com.ifsp.pi.lixt.utils.views.activeaccount.ActiveAccountViewTranslators;
import br.com.ifsp.pi.lixt.utils.views.invalidtoken.InvalidTokenView;
import br.com.ifsp.pi.lixt.utils.views.invalidtoken.InvalidTokenViewTranslators;

@SpringBootTest
@DisplayName("Testar aplicação de template de email")
public class ViewsTest {
		
	@Test
	@DisplayName("Formatar view de ActiveUser internacionalizado")
	void createAccountTemplate() {
		
		String view = ActiveAccountView.getView(Languages.convertStringToEnum("pt-br"));
		for(String value : ActiveAccountViewTranslators.translateToPortuguese().values())
			assertThat(view).contains(value);
		
		view = ActiveAccountView.getView(Languages.convertStringToEnum("en-us"));
		for(String value : ActiveAccountViewTranslators.translateToEnglish().values())
			assertThat(view).contains(value);
		
		view = ActiveAccountView.getView(Languages.convertStringToEnum(null));
		for(String value : ActiveAccountViewTranslators.translateToEnglish().values())
			assertThat(view).contains(value);
		
		view = ActiveAccountView.getView(Languages.convertStringToEnum("jp"));
		for(String value : ActiveAccountViewTranslators.translateToEnglish().values())
			assertThat(view).contains(value);
	}
	
	@Test
	@DisplayName("Formatar view de InvalidToken internacionalizado")
	void resetPasswordTemplate() {
		
		String view = InvalidTokenView.getView(Languages.convertStringToEnum("pt-br"));
		for(String value : InvalidTokenViewTranslators.translateToPortuguese().values())
			assertThat(view).contains(value);
		
		view = InvalidTokenView.getView(Languages.convertStringToEnum("en-us"));
		for(String value : InvalidTokenViewTranslators.translateToEnglish().values())
			assertThat(view).contains(value);
		
		view = InvalidTokenView.getView(Languages.convertStringToEnum(null));
		for(String value : InvalidTokenViewTranslators.translateToEnglish().values())
			assertThat(view).contains(value);
		
		view = InvalidTokenView.getView(Languages.convertStringToEnum("jp"));
		for(String value : InvalidTokenViewTranslators.translateToEnglish().values())
			assertThat(view).contains(value);
	}

}