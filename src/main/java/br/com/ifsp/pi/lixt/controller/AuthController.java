package br.com.ifsp.pi.lixt.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
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

	@GetMapping("/redefine-password")
	@ApiOperation(value = "Validar o token de redefinição da senha")
	public String validateToken(@RequestParam(value = "token") String token, @RequestParam(defaultValue = "en-us", required = false) String language){
		return authFacade.validateToken(token, Languages.convertStringToEnum(language));
	}

	@PostMapping(path = "/form/update-password")
	@ApiOperation(value = "Recebe o novo valor da senha")
	public String receiveNewPassword(@RequestParam(value = "token") String token,
								   @RequestParam(defaultValue = "en-us", required = false) String language,
								   @RequestParam(value = "newPassword") String password) {
		return this.authFacade.saveNewPassword(token, Languages.convertStringToEnum(language), password);
	}

	@PostMapping(path = "/update-password", consumes = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "Atualizar a senha de um usuário na plataforma")
	public Integer updatePassword(@RequestBody String password) {
		return this.authFacade.updatePassword(password);
	}

	@GetMapping("/data-user")
	@ApiOperation(value = "Buscar dados não-sensíveis do usuário através do token")
	public UserDto findDataUser(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {
		UserDetails ud =  oauth2Service.findDataUser(userDetails);
		UserDto userDto = UserMapper.entityToDto(authFacade.getUserData(ud.getUsername()));
		return userDto;
	}

	@PutMapping("/save-user-preferences")
	@ApiOperation(value = "Atualizar preferencias de ordenação dos comentários globais do usuário")
	public ResponseEntity<UserDto> updateUserData(@ApiIgnore @RequestBody UserDto userDetails) {
		userDetails = authFacade.updateUserPreferences(userDetails);
		return ResponseEntity.ok(userDetails);
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