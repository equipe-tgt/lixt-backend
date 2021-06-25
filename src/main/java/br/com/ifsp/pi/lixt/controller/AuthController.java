package br.com.ifsp.pi.lixt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.facade.AuthFacade;
import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.utils.exception.SendMailException;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
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
	
	@PostMapping("/forget-password/{email}")
	@ApiOperation(value = "Gera uma nova senha de um usuário na plataforma que será enviado por email")
	public ResponseEntity<Integer> forgetPassword(@PathVariable String email) {
		try {
			return new ResponseEntity<>(this.authFacade.forgetPassword(email), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
		}
		catch(SendMailException e) {
			return new ResponseEntity<>(0, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@PostMapping("/update-password")
	@ApiOperation(value = "Atualizar a senha de um usuário na plataforma")
	public ResponseEntity<Integer> updatePassword(@RequestBody(required = false) UserDto user) {
		try {
			return new ResponseEntity<>(this.authFacade.updatePassword(UserMapper.dtoToEntity(user)), HttpStatus.OK);
		}
		catch(NotFoundException e) {
			return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/data-user")
	@ApiOperation(value = "Buscar dados não-sensíveis do usuário através do token")
	public UserDetails findDataUser(@AuthenticationPrincipal UserDetails userDetails) {
		UserDto user = (UserDto) userDetails;
		user.eraseCredentials();
		return user;
	}
}