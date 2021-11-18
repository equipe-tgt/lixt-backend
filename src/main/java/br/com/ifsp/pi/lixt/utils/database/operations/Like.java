package br.com.ifsp.pi.lixt.utils.database.operations;

import java.util.Objects;

public interface Like {
	
	public static String contains(String value) {
		return "%".concat(value).concat("%");
	}
	
	public static String startsWith(String value) {
		return "%".concat(value);
	}
	
	public static String endsWith(String value) {
		return value.concat("%");
	}
	
	public static String contains(String... values) {
		String result = "";
		
		for(String value : values) {
			if(Objects.nonNull(value) && !value.isBlank()) 
				result = "%" + value;
		}
		
		return result + "%";
	}
	
	public static String specialCharacters(String value) {
		if(Objects.isNull(value))
			return "";
		return value.replace("\\W", "_");
	}

}
