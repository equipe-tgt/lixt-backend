package br.com.ifsp.pi.lixt.utils.database.operations;

public abstract class Like {
	
	private Like() {}
	
	public static String contains(String value) {
		return "%".concat(value).concat("%");
	}
	
	public static String startsWith(String value) {
		return "%".concat(value);
	}
	
	public static String endsWith(String value) {
		return value.concat("%");
	}

}
