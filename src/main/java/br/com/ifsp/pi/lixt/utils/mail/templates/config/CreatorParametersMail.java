package br.com.ifsp.pi.lixt.utils.mail.templates.config;

import java.util.HashMap;
import java.util.Map;

public abstract class CreatorParametersMail {
	
	private CreatorParametersMail() {}
	
	public static Map<String, String> createParamsResetPassword(String username, String password) {
		Map<String, String> map = new HashMap<>();
		
		map.put("$USERNAME", username);
		map.put("$PASSWORD", password);
		
		return map;
	}
	
	public static Map<String, String> createParamsCreateAccount(String username, String baseUrl, String token) {
		Map<String, String> map = new HashMap<>();
		
		map.put("$USERNAME", username);
		map.put("$BASE_URL", baseUrl);
		map.put("$TOKEN", token);
		
		return map;
	}

}
