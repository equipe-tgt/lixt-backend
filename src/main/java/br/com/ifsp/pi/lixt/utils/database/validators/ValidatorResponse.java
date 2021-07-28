package br.com.ifsp.pi.lixt.utils.database.validators;

public abstract class ValidatorResponse {
	
	private ValidatorResponse() {}
	
	public static boolean wasUpdated(Integer response) {
		return response == 1;
	}
	
	public static boolean wasDeleted(Integer response) {
		return response == 1;
	}

}
