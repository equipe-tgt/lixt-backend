package br.com.ifsp.pi.lixt.data.business.user;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

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
	
	public User save(User user) {
		return this.userRepository.save(user);
	}
	
	public void deleteById(Long id) {
		this.userRepository.deleteById(id);
	}
	
	public Integer activeAccount(String email, String password) {
		return this.userRepository.updatePassword(password, email);
	}
	
	public Integer activeAccount(Long id) {
		return this.userRepository.activeAccount(id);
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
	
}