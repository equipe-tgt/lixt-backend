package br.com.ifsp.pi.lixt.utils.security.oauth;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import br.com.ifsp.pi.lixt.utils.security.oauth.entity.Oauth2TableGenerator;

@Configuration
public class BeansConfig {
	
	private final PasswordEncoder passwordEncoder;
	private final TokenStore tokenStore;
	private final DefaultTokenServices tokenServices;

	public BeansConfig(DataSource dataSource, Oauth2TableGenerator oauth2TableGenerator) {

		passwordEncoder = new BCryptPasswordEncoder();
		tokenStore = new JdbcTokenStore(dataSource);
		tokenServices = new DefaultTokenServices();

		tokenServices.setTokenStore(tokenStore);
		tokenServices.setAccessTokenValiditySeconds(60 * 60);
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setReuseRefreshToken(true);
		tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 90);
		
		oauth2TableGenerator.createOauth2Tables();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return passwordEncoder;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return tokenStore;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
	    return tokenServices;
	}

}