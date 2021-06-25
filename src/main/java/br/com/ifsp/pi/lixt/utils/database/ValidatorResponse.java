package br.com.ifsp.pi.lixt.utils.database;

public abstract class ValidatorResponse {
	
	public static boolean successfullyUpdated(Integer response) {
		return response == 1;
	}
	
	public static boolean successfullyDeleted(Integer response) {
		return response == 1;
	}

}
