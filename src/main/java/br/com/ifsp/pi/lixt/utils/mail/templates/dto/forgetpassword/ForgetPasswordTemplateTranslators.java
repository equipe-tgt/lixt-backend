package br.com.ifsp.pi.lixt.utils.mail.templates.dto.forgetpassword;

import java.util.HashMap;
import java.util.Map;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class ForgetPasswordTemplateTranslators {
	
	private ForgetPasswordTemplateTranslators() {}
	
	public static String translateTitleMail(Languages language) {
		switch(language) {
			case PORTUGUESE:
				return "Lixt - Alteração de Senha";
			
			case ENGLISH:
			default:
				return "Lixt - Change password";
		}
	}
	
	public static Map<String, String> translateToPortuguese() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Senha Resetada com Sucesso!");
		params.put("$HELLO", "Olá, $USERNAME!");
		params.put("$FIRST_INFO", "Sua nova senha de acesso do Lixt é: ");
		params.put("$WARNING", "É altamente recomendado que a altere após acessar a plataforma para melhor proteger sua conta.");
		params.put("$BYE", "Bons planejamentos e boas compras!");
		
		return params;
	}
	
	public static Map<String, String> translateToEnglish() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Password Reset Successfully!");
		params.put("$HELLO", "Hello $USERNAME!");
		params.put("$FIRST_INFO", "Your new Lixt access password is: ");
		params.put("$WARNING", "It is highly recommended that you change it after accessing the platform to better protect your account.");
		params.put("$BYE", "Good planning and good shopping!");
		
		return params;
	}

}
