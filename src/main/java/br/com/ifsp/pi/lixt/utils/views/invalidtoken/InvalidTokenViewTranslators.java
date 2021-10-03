package br.com.ifsp.pi.lixt.utils.views.invalidtoken;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class InvalidTokenViewTranslators {
	
	public static Map<String, String> toPortuguese() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Página Não Encontrada!");
		params.put("$RESPONSE", "Não foi possível acessar ao conteúdo solicitado! 😞");
		params.put("$WARNING", "Recomendamos que acesse o aplicativo realizando login ou cadastra-se a partir do mesmo.");

		return params;
	}
	
	public static Map<String, String> toEnglish() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Page not found!");
		params.put("$RESPONSE", "Could not access requested content! 😞");
		params.put("$WARNING", "We recommend that you access the application by logging in or registering from it.");
		
		return params;
	}

}
