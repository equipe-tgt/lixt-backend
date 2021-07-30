package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

public abstract class UserDtoInstantior {

	private UserDtoInstantior() {}
	
	public static OauthUserDto createUser(String name, String username, String email, String password) {
		return OauthUserDto.builder()
				.name(name)
				.username(username)
				.email(email)
				.password(password)
				.build();
	}
	
	public static OauthUserDto createUser(String username, String password) {
		return OauthUserDto.builder()
				.username(username)
				.password(password)
				.build();
	}
	
}
