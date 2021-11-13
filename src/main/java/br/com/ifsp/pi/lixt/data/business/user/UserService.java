package br.com.ifsp.pi.lixt.data.business.user;

import br.com.ifsp.pi.lixt.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public List<User> findAll() {
		return this.userRepository.findAll();
	}
	
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	public User findByUsernameOrEmail(String username) {
		return this.userRepository.findByUsernameOrEmail(username);
	}

	public User findById(Long id) {
		return this.userRepository.findUserById(id);
	}
	
	public User save(User user) {
		return this.userRepository.save(user);
	}
	
	public void deleteById(Long id) {
		this.userRepository.deleteById(id);
	}
	
	public Integer activeAccount(String token) {
		return this.userRepository.activeAccount(token);
	}
	
	public Integer desactiveAccount(Long id) {
		return this.userRepository.desactiveAccount(id);
	}
	
	public Integer updatePassword(String email, String password) {
		return this.userRepository.updatePassword(password, email);
	}
	
	public String findUsernameByEmail(String email) {
		return this.userRepository.findUsernameByEmail(email);
	}
	
	public String findFirstAccesTokenById(Long id) {
		return this.userRepository.findFirstAccesTokenById(id);
	}

	public UserDto saveGlobalCommentsPreferences(UserDto userDto) {
		this.userRepository.saveGlobalCommentsPreferences(userDto.getId(), userDto.isGlobalCommentsChronOrder());
		return userDto;
	}

	public UserDto saveOlderCommentsFirst(UserDto userDto) {
		this.userRepository.saveOlderCommentsFirst(userDto.getId(), userDto.isOlderCommentsFirst());
		return userDto;
	}
}