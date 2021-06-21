package br.com.ifsp.pi.lixt.facade;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacade {
	
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	
	public User register(User user) {

		if(userService.findByEmail(user.getEmail()) != null) {
			
		}
		if(userService.findByUsername(user.getUsername()) != null) {
			
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));		
		return this.userService.save(user);
	}

}
