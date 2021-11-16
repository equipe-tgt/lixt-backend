package br.com.ifsp.pi.lixt.utils.security.oauth.objects;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.user.User;

public abstract class OauthUserMapper {
	
	private OauthUserMapper() {}
	
	public static OauthUserDto entityToDto(User entity) {

		if(Objects.isNull(entity))
			return null;
		
		return OauthUserDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.username(entity.getUsername())
				.email(entity.getEmail())
				.globalCommentsChronOrder(entity.isGlobalCommentsChronOrder())
				.build();
	}
	
	public static User dtoToEntity(OauthUserDto dto) {

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

}
