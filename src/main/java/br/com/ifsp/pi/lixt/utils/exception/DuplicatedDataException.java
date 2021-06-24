package br.com.ifsp.pi.lixt.utils.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@SuppressWarnings("serial")
public class DuplicatedDataException extends RuntimeException {
	
	@Getter
	private final HttpStatus status = HttpStatus.CONFLICT;
	
	public DuplicatedDataException(String message) {
		super(message, null, false, false);
	}

}
