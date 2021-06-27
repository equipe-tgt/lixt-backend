package br.com.ifsp.pi.lixt.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class PrecoditionUpdateFailedException extends RuntimeException {
	
	@Getter
	private final HttpStatus status = HttpStatus.PRECONDITION_FAILED;
	
	public PrecoditionUpdateFailedException(String message) {
		super(message, null, false, false);
	}

}
