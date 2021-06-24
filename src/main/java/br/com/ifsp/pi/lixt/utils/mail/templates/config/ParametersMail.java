package br.com.ifsp.pi.lixt.utils.mail.templates.config;

import java.util.HashMap;
import java.util.Map;

public abstract class ParametersMail {
	
	public static Map<String, String> formatParamsResetPassword(String username, String password) {
		Map<String, String> map = new HashMap<>();
		
		map.put("$USERNAME", username);
		map.put("$PASSWORD", password);
		
		return map;
	}

}
