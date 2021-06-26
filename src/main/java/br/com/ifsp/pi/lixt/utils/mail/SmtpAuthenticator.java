package br.com.ifsp.pi.lixt.utils.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import br.com.ifsp.pi.lixt.utils.validators.ValidatorData;

public class SmtpAuthenticator extends Authenticator {
	
	private String username;
	private String password;
	
	public SmtpAuthenticator(String username, String password) {
	    super();
	    this.username = username;
	    this.password = password;
	}
	
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
	    
		if (ValidatorData.validateString(username) && ValidatorData.validateString(password)) {
	        return new PasswordAuthentication(username, password);
	    }
	
	    return null;
	}
}