package br.com.ifsp.pi.lixt.utils.security.oauth.function;

import org.apache.commons.lang3.RandomStringUtils;

public abstract class PasswordGenerator {
		
	private static final int LENGTH_PASSWORD = 12;
	
	private PasswordGenerator() {}

	public static String generateRandomPassword() {
		return RandomStringUtils.randomAlphanumeric(LENGTH_PASSWORD);
	}

}
