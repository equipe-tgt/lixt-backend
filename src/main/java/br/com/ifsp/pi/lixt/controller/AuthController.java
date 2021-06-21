package br.com.ifsp.pi.lixt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.facade.AuthFacade;
import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthFacade authFacade;
	
	@PostMapping("/register")
	public UserDto register(@RequestBody(required = false) UserDto user) {
		return UserMapper.entityToDto(authFacade.register(UserMapper.dtoToEntity(user)));
	}
	
	@GetMapping("/resource")
	public String getMessage() {
		return "Hello";
	}

}