package br.com.ifsp.pi.lixt.utils.views.activeaccount;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ActiveAccountViewTranslators {
	
	public static Map<String, String> toPortuguese() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Conta Ativada com Sucesso!");
		params.put("$RESPONSE", "Agora você tem acesso garantido à plataforma Lixt! 😀");
		params.put("$WARNING", "Esperamos que aproveite cada experiência, planejar um compra pode ser tão divertido quanto comprar!");

		return params;
	}
	
	public static Map<String, String> toEnglish() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Account Activated Successfully!");
		params.put("$RESPONSE", "You are now guaranteed access to the Lixt platform! 😀");
		params.put("$WARNING", "We hope you enjoy every experience, planning a purchase can be as fun as shopping!");
		
		return params;
	}

}
