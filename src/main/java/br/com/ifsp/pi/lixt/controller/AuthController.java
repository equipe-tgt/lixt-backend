package br.com.ifsp.pi.lixt.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.user.User;
import br.com.ifsp.pi.lixt.user.UserService;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	
	@PostMapping("/register")
	public UserDto register(@RequestBody(required = false) User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return UserMapper.entityToDto(this.userService.save(user));
	}
	
	@GetMapping("/resource")
	public String getMessage() {
		return "Hello";
	}

}