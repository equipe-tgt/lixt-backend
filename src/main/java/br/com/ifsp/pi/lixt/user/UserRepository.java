package br.com.ifsp.pi.lixt.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

	List<User> findAll();
	
	@Query("select u from User u where u.email = ?1")
	User findByUsername(String username);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.activated = true where u.id = ?1")
	Integer activeAccount(Long id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.activated = false where u.id = ?1")
	Integer desactiveAccount(Long id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.password = ?1 where u.email = ?2")
	Integer updatePassword(String password, String username);
	
}