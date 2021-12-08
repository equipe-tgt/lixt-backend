package br.com.ifsp.pi.lixt.utils.mail.templates.dto.invite;

import java.util.HashMap;
import java.util.Map;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InviteTemplateTranslators {
	
	public static String translateTitleMail(Languages language) {
		switch(language) {
			case PORTUGUESE:
				return "Lixt - Participe da Nossa Plataforma";
			
			case ENGLISH:
			default:
				return "Lixt - Join Our Platform";
		}
	}
	
	public static Map<String, String> toPortuguese() {
		Map<String, String> params = new HashMap<>();

		params.put("$TITLE", "Venha Participar da Nossa Plataforma!");
		params.put("$HELLO", "Olá!");
		params.put("$INVITE", "Seu amigo $USERNAME ($EMAIL) está te convidando para participar de nossa plataforma!");
		params.put("$ADVICE", "Basta baixar o aplicativo no seu celular, registrar-se e começar a usar.");
		params.put("$BYE", "Vamos nessa?");		
		
		return params;
	}
	
	public static Map<String, String> toEnglish() {
		Map<String, String> params = new HashMap<>();

		params.put("$TITLE", "Come Join Our Platform!");
		params.put("$HELLO", "Olá!");
		params.put("$INVITE", "Your friend $USERNAME ($EMAIL) is inviting you to join our platform!");
		params.put("$ADVICE", "Just download the app to your mobile, register and start using.");
		params.put("$BYE", "Let's go?");
		
		return params;
	}

}
