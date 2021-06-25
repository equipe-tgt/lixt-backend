package br.com.ifsp.pi.lixt.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicatedDataException extends RuntimeException {
	
	@Getter
	private final HttpStatus status = HttpStatus.CONFLICT;
	
	public DuplicatedDataException(String message) {
		super(message, null, false, false);
	}

}
