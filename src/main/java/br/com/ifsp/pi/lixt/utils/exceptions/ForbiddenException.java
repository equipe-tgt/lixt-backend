package br.com.ifsp.pi.lixt.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
	
	@Getter
	private static final HttpStatus status = HttpStatus.FORBIDDEN;
	
	public ForbiddenException() {
		super("Você não possui permissão para acessar esse recurso", null, false, false);
	}

}
