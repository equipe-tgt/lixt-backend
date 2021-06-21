package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;

@SpringBootTest
public class AuthControllerTest {
	
	@Autowired
	private AuthController authController;
	
	@Test
	public void registerUser() {
		
		UserDto user = UserDto.builder()
				.name("leo")
				.username("leo")
				.email("leo@gmail.com")
				.password("123")
				.build();
		
		UserDto createdUser = authController.register(user);
		
		assertThat(createdUser).isNotNull();
		assertThat(createdUser.getPassword()).isNull();
	}

}
