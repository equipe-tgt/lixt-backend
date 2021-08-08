package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.dto.UserDto;

public abstract class UserDtoInstantior {

	private UserDtoInstantior() {}
	
	public static UserDto createUser(String name, String username, String email, String password) {
		return UserDto.builder()
				.name(name)
				.username(username)
				.email(email)
				.password(password)
				.build();
	}
	
	public static UserDto createUser(String username, String password) {
		return UserDto.builder()
				.username(username)
				.password(password)
				.build();
	}
	
}
