package br.com.ifsp.pi.lixt.user;

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
	
	public User findById(Long id) {
		return this.userRepository.findById(id).orElse(null);
	}
	
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	public User save(User user) {
		return this.userRepository.save(user);
	}
	
	public Integer activeAccount(Long id) {
		return this.userRepository.activeAccount(id);
	}
	
	public Integer desactiveAccount(Long id) {
		return this.userRepository.desactiveAccount(id);
	}
	
}