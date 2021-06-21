package br.com.ifsp.pi.lixt.utils.security.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfig {
	
	private final PasswordEncoder passwordEncoder;
	
	public BeansConfig() {

		passwordEncoder = new BCryptPasswordEncoder();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return passwordEncoder;
	}

}