package br.com.ifsp.pi.lixt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.facade.AuthFacade;
import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.DuplicatedDataException;
import br.com.ifsp.pi.lixt.utils.exceptions.NotFoundException;
import br.com.ifsp.pi.lixt.utils.exceptions.SendMailException;
import br.com.ifsp.pi.lixt.utils.mail.templates.Languages;
import br.com.ifsp.pi.lixt.utils.security.Oauth2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "Controller de autenticação, registro e busca de dados do usuário")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthFacade authFacade;
	private final Oauth2Service oauth2Service;
	
	@PostMapping("/register")
	@ApiOperation(value = "Registrar usuário na plataforma")
	public ResponseEntity<Object> register(@RequestBody(required = false) UserDto user, @RequestParam(defaultValue = "en-us", required = false) String language) {
		try {
			return ResponseEntity.ok(UserMapper.entityToDto(authFacade.register(UserMapper.dtoToEntity(user), Languages.convertStringToEnum(language))));
		} catch (DuplicatedDataException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch(SendMailException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@PostMapping(value = "/forget-password", consumes = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "Solicitação de cadastro de nova senha")
	public ResponseEntity<Integer> forgetPassword(@RequestBody String email, @RequestParam(defaultValue = "en-us", required = false) String language) {
		try {
			return ResponseEntity.ok(this.authFacade.forgetPassword(email, Languages.convertStringToEnum(language)));
		} catch (NotFoundException e) {
			return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
		} catch(SendMailException e) {
			return new ResponseEntity<>(0, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@PostMapping(path = "/update-password", consumes = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "Atualizar a senha de um usuário na plataforma")
	public Integer updatePassword(@RequestBody String password) {
		return this.authFacade.updatePassword(password);
	}
	
	@GetMapping("/data-user")
	@ApiOperation(value = "Buscar dados não-sensíveis do usuário através do token")
	public UserDetails findDataUser(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {
		return oauth2Service.findDataUser(userDetails);
	}
	
	@GetMapping("/active-user")
	@ApiOperation(value = "Ativar conta de usuário na plataforma")
	public String activeUser(@RequestParam(value = "token") String token, @RequestParam(defaultValue = "en-us", required = false) String language) {
		return authFacade.activeUser(token, Languages.convertStringToEnum(language));
	}
	
	@GetMapping("/revoke-token")
	@ApiOperation(value = "Remover tokens ao realizar logoff")
	public void revokeToken() {
		oauth2Service.revokeToken();
	}
	
}