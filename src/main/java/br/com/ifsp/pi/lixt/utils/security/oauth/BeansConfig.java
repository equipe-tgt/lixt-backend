package br.com.ifsp.pi.lixt.utils.security.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class BeansConfig {
	
	private final PasswordEncoder passwordEncoder;
	private final TokenStore tokenStore;
	private final DefaultTokenServices tokenServices;
	
	public BeansConfig(RedisConnectionFactory redisConnectionFactory) {

		passwordEncoder = new BCryptPasswordEncoder();
		tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenServices = new DefaultTokenServices();

		tokenServices.setTokenStore(tokenStore);
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setAccessTokenValiditySeconds(60 * 60);
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