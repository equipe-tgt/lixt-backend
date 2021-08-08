package br.com.ifsp.pi.lixt.data.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.instantiator.UserDtoInstantior;

public abstract class UserDtoData {
	
	private UserDtoData() {}
	
	public static List<UserDto> initializeValues() {
		List<UserDto> users = new ArrayList<>();
		
		users.add(UserDtoInstantior.createUser("teste1", "teste1", "teste1@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste2", "teste2", "teste2@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste3", "teste3", "teste3@gmail.com", "123"));
		
		return users;
	}
	
	public static List<UserDto> dataForListControllerTest() {
		List<UserDto> users = new ArrayList<>();
		
		users.add(UserDtoInstantior.createUser("teste4", "teste4", "teste4@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste5", "teste5", "teste5@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste6", "teste6", "teste6@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste7", "teste7", "teste7@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste8", "teste8", "teste8@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste9", "teste9", "teste9@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste10", "teste10", "teste10@gmail.com", "123"));
		
		return users;
	}
	
	public static List<UserDto> dataForProductOfListControllerTest() {
		List<UserDto> users = new ArrayList<>();
		
		users.add(UserDtoInstantior.createUser("teste11", "teste11", "teste11@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste12", "teste12", "teste12@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste13", "teste13", "teste13@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste14", "teste14", "teste14@gmail.com", "123"));
		users.add(UserDtoInstantior.createUser("teste15", "teste15", "teste15@gmail.com", "123"));
		
		return users;
	}

}
