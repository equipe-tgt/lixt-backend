package br.com.ifsp.pi.lixt.utils.mail.templates;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Languages {
	
	ENGLISH("en-us"),
	PORTUGUESE("pt-br");
	
	private String description;
	
	public static Languages convertStringToEnum(String value) {
		
		if(Objects.isNull(value))
			return Languages.ENGLISH;
		
		for(Languages language : Languages.values()) {
			if(language.getDescription().equals(value)) {
				return language;
			}
		}
		
		return Languages.ENGLISH;
	}

}
