package br.com.ifsp.pi.lixt.data.business.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	public User findByUsernameOrEmail(String username) {
		return this.userRepository.findByUsernameOrEmail(username);
	}
	
	public User findByEmailAndToken(String email, String token) {
		return this.userRepository.findByEmailAndToken(email, token);
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
	
	public Integer updatePasswordByToken(String email, String password, String token) {
		return this.userRepository.updatePasswordByToken(password, email, token);
	}	
	
	public String findFirstAccesTokenById(Long id) {
		return this.userRepository.findFirstAccesTokenById(id);
	}
	
	public String findResetPasswordTokenById(Long id) {
		return this.userRepository.findResetPasswordTokenById(id);
	}
	
}