package br.com.ifsp.pi.lixt.utils.security.oauth.server;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.UserDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OauthUserDetailsService implements UserDetailsService {
	
	private final UserService userService;

	@Override
	public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userService.findByUsernameOrEmail(username);
		
		if(Objects.nonNull(user)) {
			
			return UserDto.builder()
					.id(user.getId())
					.name(user.getName())
					.username(user.getUsername())
					.email(user.getEmail())
					.password(user.getPassword())
					.enabled(true)
					.build();
		}
		
		throw new UsernameNotFoundException("usuario não encontrado");
	}

}