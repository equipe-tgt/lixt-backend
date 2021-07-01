package br.com.ifsp.pi.lixt.utils.security.oauth;

import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserAuthenticated;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.security.oauth.server.OauthUserDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OauthManager implements AuthenticationManager {
	
	private final OauthUserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		OauthUserDto user = userDetailsService.loadUserByUsername(authentication.getName());
		
		if(Objects.nonNull(user) && passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword())) {
			return new UserAuthenticated(user);
		}
		else {
			throw new BadCredentialsException("Credenciais inválidas");
		}
	}

}