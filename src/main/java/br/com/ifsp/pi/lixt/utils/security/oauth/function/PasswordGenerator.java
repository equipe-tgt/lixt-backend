package br.com.ifsp.pi.lixt.utils.security.oauth.function;

import org.apache.commons.lang3.RandomStringUtils;

public abstract class PasswordGenerator {
	
	public static String generateRandomPassword() {
		return RandomStringUtils.randomAlphanumeric(12);
	}

}
