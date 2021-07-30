package br.com.ifsp.pi.lixt.utils.validators;

import java.util.Objects;

public abstract class ValidatorData {
	
	private ValidatorData() {}
	
	public static boolean validateString(String value) {
		return Objects.nonNull(value) && !value.isBlank();
	}

}
