package br.com.ifsp.pi.lixt;

import br.com.ifsp.pi.lixt.utils.mail.SmtpAuthenticator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
@EnableJpaAuditing
@EnableScheduling
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
