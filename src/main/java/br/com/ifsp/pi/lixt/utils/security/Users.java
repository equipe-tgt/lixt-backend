package br.com.ifsp.pi.lixt.utils.security;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;

public abstract class Users {

	private static UserDto getUserAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(Objects.isNull(authentication)) {
			return null;
		}
		
		return (UserDto) authentication.getPrincipal();
	}
	
	public static Long getUserId() {
		UserDto user = getUserAuthenticated();
		
		if(Objects.isNull(user)) {
			return null;
		}
		
		return user.getId();
	}
	
}
