package br.com.ifsp.pi.lixt.utils.mail.templates;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeMail {
	
	RESET_PASSWORD(1);
	
	private int id;

}
