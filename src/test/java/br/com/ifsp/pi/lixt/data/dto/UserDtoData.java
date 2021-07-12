package br.com.ifsp.pi.lixt.data.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.instantiator.UserDtoInstantior;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

public abstract class UserDtoData {
	
	private static List<OauthUserDto> users = new ArrayList<>();
	
	public static List<OauthUserDto> initializeValues() {
		
		users.add(UserDtoInstantior.createUser("leo", "leo", "leo_narita@hotmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste", "teste", "teste@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste3", "teste3", "teste3@gmail.com", "123"));
		
		return users;
	}

}
