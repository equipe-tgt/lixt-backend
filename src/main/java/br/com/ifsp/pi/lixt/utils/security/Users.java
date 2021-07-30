package br.com.ifsp.pi.lixt.utils.security;

import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

public abstract class Users {

	private Users() {}
	
	private static OauthUserDto getUserAuthenticated() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(Objects.isNull(authentication)) {
			return null;
		}
		
		return (OauthUserDto) authentication.getPrincipal();
	}
	
	public static Long getUserId() {
		OauthUserDto user = getUserAuthenticated();
		
		if(Objects.isNull(user)) {
			return null;
		}
		
		return user.getId();
	}
	
	public static String getEmail() {
		OauthUserDto user = getUserAuthenticated();
		
		if(Objects.isNull(user)) {
			return null;
		}
		
		return user.getEmail();
	}
	
}
