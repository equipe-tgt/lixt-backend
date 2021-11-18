package br.com.ifsp.pi.lixt.utils.mail.templates.dto.createaccount;

import java.util.HashMap;
import java.util.Map;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class CreateAccountTemplateTranslators {

	private CreateAccountTemplateTranslators() {}

	public static String translateTitleMail(Languages language) {
		switch(language) {
			case PORTUGUESE:
				return "Lixt - Criação de conta";
			
			case ENGLISH:
			default:
				return "Lixt - Create account";
		}
	}
	
	public static Map<String, String> toPortuguese() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Bem-Vindo à Plataforma Lixt");
		params.put("$HELLO", "Olá, $USERNAME!");
		params.put("$GREETINGS", "Ficamos felizes em te receber no Lixt!");
		params.put("$LINK", "Antes de realizar seu primeiro acesso, clique aqui para ativar a conta");
		
		return params;
	}
	
	public static Map<String, String> toEnglish() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Welcome to Lixt Platform");
		params.put("$HELLO", "Hello $USERNAME!");
		params.put("$GREETINGS", "We are happy to welcome you to Lixt!");
		params.put("$LINK", "Before making your first login, click here to activate the account");
		
		return params;
	}
	
}
