package br.com.ifsp.pi.lixt.utils.mail.templates.config;

import java.util.HashMap;
import java.util.Map;

import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;

public abstract class CreatorParametersMail {
	
	private CreatorParametersMail() {}
	
	public static Map<String, String> resetPassword(String username, String baseUrl, String token, Languages language) {
		Map<String, String> map = new HashMap<>();
		
		map.put("$USERNAME", username);
		map.put("$LANGUAGE", language.getDescription());
		map.put("$BASE_URL", baseUrl);
		map.put("$TOKEN", token);

		return map;
	}
	
	public static Map<String, String> createAccount(String username, String baseUrl, String token, Languages language) {
		Map<String, String> map = new HashMap<>();
		
		map.put("$USERNAME", username);
		map.put("$LANGUAGE", language.getDescription());
		map.put("$BASE_URL", baseUrl);
		map.put("$TOKEN", token);
		
		return map;
	}

}
