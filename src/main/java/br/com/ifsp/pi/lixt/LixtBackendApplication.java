package br.com.ifsp.pi.lixt;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.ifsp.pi.lixt.utils.mail.SmtpAuthenticator;

@SpringBootApplication
public class LixtBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LixtBackendApplication.class, args);
	}
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-0300"));
	}
	
	@Bean
	@Qualifier("mailer")
	SmtpAuthenticator mailerAccount(@Value("${lixt.mail.username}") String username, @Value("${lixt.mail.password}") String password) {
		return new SmtpAuthenticator(username, password);
	}

}
