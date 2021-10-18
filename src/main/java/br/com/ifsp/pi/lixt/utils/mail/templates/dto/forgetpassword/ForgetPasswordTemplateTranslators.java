package br.com.ifsp.pi.lixt.utils.mail.templates.dto.forgetpassword;

import java.util.HashMap;
import java.util.Map;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class ForgetPasswordTemplateTranslators {
	
	private ForgetPasswordTemplateTranslators() {}
	
	public static String translateTitleMail(Languages language) {
		switch(language) {
			case PORTUGUESE:
				return "Lixt - Solicitação de Redefinição de Senha";
			
			case ENGLISH:
			default:
				return "Lixt - Password Reset Request";
		}
	}
	
	public static Map<String, String> toPortuguese() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Definir uma nova senha de acesso");
		params.put("$HELLO", "Olá, $USERNAME!");
		params.put("$FIRST_INFO", "Você está recebendo esse email porque solicitou a redefinição da sua senha, para prosseguir clique no link abaixo:");
		params.put("$LINK", "Clique aqui para criar uma nova senha");
		params.put("$BYE", "Bons planejamentos e boas compras!");
		
		return params;
	}
	
	public static Map<String, String> toEnglish() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Set a new access password");
		params.put("$HELLO", "Hello $USERNAME!");
		params.put("$FIRST_INFO", "You are receiving this email because you requested to reset your password, to proceed click on the link below:");
		params.put("$LINK", "Click here to create a new password");
		params.put("$BYE", "Good planning and good shopping!");
		
		return params;
	}

}
