package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.user.User;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;

public abstract class UserMapper {
	
	public static UserDto entityToDto(User entity) {

		if(Objects.isNull(entity))
			return null;
		
		return UserDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.username(entity.getUsername())
				.email(entity.getEmail())
				.build();
	}
	
	public static User dtoToEntity(UserDto dto) {

		if(Objects.isNull(dto))
			return null;
		
		return User.builder()
				.id(dto.getId())
				.name(dto.getName())
				.username(dto.getUsername())
				.email(dto.getEmail())
				.build();
	}

}
