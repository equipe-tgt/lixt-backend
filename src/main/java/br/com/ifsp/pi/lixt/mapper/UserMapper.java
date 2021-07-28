package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

public abstract class UserMapper {
	
	private UserMapper() {}
	
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
	
	public static UserDto dtoOauthToDto(OauthUserDto dtoOauth) {
		
		if(Objects.isNull(dtoOauth))
			return null;
		
		return UserDto.builder()
				.id(dtoOauth.getId())
				.name(dtoOauth.getName())
				.username(dtoOauth.getUsername())
				.email(dtoOauth.getEmail())
				.build();
	}

}
