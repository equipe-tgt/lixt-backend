package br.com.ifsp.pi.lixt.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.facade.AuthFacade;
import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de autenticação, registro e busca de dados do usuário")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthFacade authFacade;
	
	@PostMapping("/register")
	@ApiOperation(value = "Registrar usuário na plataforma")
	public UserDto register(@RequestBody(required = false) UserDto user) {
		return UserMapper.entityToDto(authFacade.register(UserMapper.dtoToEntity(user)));
	}
	
	@GetMapping("/data-user")
	@ApiOperation(value = "Buscar dados não-sensíveis do usuário através do token")
	public UserDetails findDataUser(@AuthenticationPrincipal UserDetails userDetails) {
		UserDto user = (UserDto) userDetails;
		user.eraseCredentials();
		return user;
	}
}