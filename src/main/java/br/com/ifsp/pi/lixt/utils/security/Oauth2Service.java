package br.com.ifsp.pi.lixt.utils.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oauth2Service {
	
	private final TokenStore tokenStore;
	private final DefaultTokenServices tokenServices;
	
	public UserDetails findDataUser(UserDetails userDetails) {
		OauthUserDto user = (OauthUserDto) userDetails;
		user.eraseCredentials();
		return user;
	}
	
	public void revokeToken() {
		OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		String token = tokenStore.getAccessToken(auth).getValue();
		tokenServices.revokeToken(token);
	}

}
