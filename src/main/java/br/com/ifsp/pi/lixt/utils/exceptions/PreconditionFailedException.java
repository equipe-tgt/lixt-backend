package br.com.ifsp.pi.lixt.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class PreconditionFailedException extends RuntimeException {
	
	@Getter
	private static final HttpStatus status = HttpStatus.PRECONDITION_FAILED;
	
	public PreconditionFailedException(String message) {
		super(message, null, false, false);
	}

}
