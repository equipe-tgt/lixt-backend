package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.utils.mapper.Mapper;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

public abstract class UserMapper extends Mapper {
	
	private UserMapper() {}
	
	public static UserDto entityToDto(User entity) {

		if(Objects.isNull(entity))
			return null;
		
		return UserDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.username(entity.getUsername())
				.email(entity.getEmail())
				.globalCommentsChronOrder(entity.isGlobalCommentsChronOrder())
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
				.password(dto.getPassword())
				.globalCommentsChronOrder(dto.isGlobalCommentsChronOrder())
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
				.globalCommentsChronOrder(dtoOauth.isGlobalCommentsChronOrder())
				.build();
	}

}
