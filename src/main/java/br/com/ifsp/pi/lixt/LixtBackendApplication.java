package br.com.ifsp.pi.lixt;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import br.com.ifsp.pi.lixt.utils.mail.SmtpAuthenticator;

@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
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
	
	/*
	 * TODO
	 * 
	 * 1. Oauth2 : Definir TokenStore (inMemory ou Redis) + ExpirationTime
	 * 
	 * 2. Endpoints : Possivelmente ajustar DTOs e Mappers
	 * 
	 * 3. Analisar pontos passiveis de exceções
	 * 
	 * 4. Refatorar scripts de teste
	 * 
	 * 5. Analisar casos de segurança
	 * 
	 */

}
