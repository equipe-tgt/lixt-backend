package br.com.ifsp.pi.lixt.data.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

public abstract class UserDtoData {
	
	private static List<OauthUserDto> users = new ArrayList<>();
	
	public static List<OauthUserDto> initializeValues() {
		
		users.add(OauthUserDto.builder()
				.name("leo")
				.username("leo")
				.email("leo_narita@hotmail.com")
				.password("123")
				.build()
		);
		
		users.add(OauthUserDto.builder()
				.name("teste")
				.username("teste")
				.email("teste@gmail.com")
				.password("123")
				.build()
		);
		
		users.add(OauthUserDto.builder()
				.name("teste3")
				.username("teste3")
				.email("teste3@gmail.com")
				.password("123")
				.build()
		);
		
		return users;
	}

}
