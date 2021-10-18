package br.com.ifsp.pi.lixt.utils.security.oauth.function;

import java.util.Base64;
import java.util.UUID;

public abstract class SecurityGenerator {
	
	private SecurityGenerator() {}
	
	public static String generateToken(String key) {
		return Base64.getEncoder().encodeToString(UUID.nameUUIDFromBytes(key.getBytes()).toString().getBytes());
	}

}
