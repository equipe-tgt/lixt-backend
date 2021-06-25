package br.com.ifsp.pi.lixt.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	
	@Getter
	private final HttpStatus status = HttpStatus.NOT_FOUND;
	
	public NotFoundException(String message) {
		super(message, null, false, false);
	}

}
