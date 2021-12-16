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
		params.put("$TUTORIAL", "Em dúvida de como dar seus primeiros passos?");
		params.put("$LINK_TEXT", "Veja este vídeo");
		params.put("$LINK", "https://youtu.be/FVS_pm66l20");
		params.put("$WARNING", "Esperamos que aproveite cada experiência, planejar um compra pode ser tão divertido quanto comprar!");

		return params;
	}
	
	public static Map<String, String> toEnglish() {
		Map<String, String> params = new HashMap<>();
		
		params.put("$TITLE", "Account Activated Successfully!");
		params.put("$RESPONSE", "You are now guaranteed access to the Lixt platform! 😀");
		params.put("$TUTORIAL", "In doubt about how to take your first steps?");
		params.put("$LINK_TEXT", "Watch this video");
		params.put("$LINK", "https://youtu.be/FVS_pm66l20");
		params.put("$WARNING", "We hope you enjoy every experience, planning a purchase can be as fun as shopping!");
		
		return params;
	}

}
