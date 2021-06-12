package br.com.ifsp.pi.lixt.utils.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import br.com.ifsp.pi.lixt.utils.security.oauth.OauthManager;
import br.com.ifsp.pi.lixt.utils.security.oauth.server.OauthUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	
	private final OauthManager oauthManager;
	private final PasswordEncoder passwordEncoder;
	private final OauthUserDetailsService userDetailsService;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(oauthManager).userDetailsService(userDetailsService);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
        	.withClient("client")
        	.authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials").scopes("all")
        	.refreshTokenValiditySeconds(300000)
        	.secret(passwordEncoder.encode("123456"))
        	.accessTokenValiditySeconds(50000);
	}

}
