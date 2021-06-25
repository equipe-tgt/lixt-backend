package br.com.ifsp.pi.lixt.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class SendMailException extends RuntimeException {
	
	@Getter
	private final HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
	
	public SendMailException() {
		super("Não foi possível enviar o email. Tente seguir os procedimentos novamente", null, false, false);
	}

}
