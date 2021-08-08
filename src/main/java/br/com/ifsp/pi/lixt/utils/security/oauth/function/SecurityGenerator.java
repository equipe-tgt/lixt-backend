package br.com.ifsp.pi.lixt.utils.security.oauth.function;

import java.util.Base64;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public abstract class SecurityGenerator {
		
	private static final int LENGTH_PASSWORD = 12;
	
	private SecurityGenerator() {}

	public static String generateRandomPassword() {
		return RandomStringUtils.randomAlphanumeric(LENGTH_PASSWORD);
	}
	
	public static String generateToken(String key) {
		return Base64.getEncoder().encodeToString(UUID.nameUUIDFromBytes(key.getBytes()).toString().getBytes());
	}

}
