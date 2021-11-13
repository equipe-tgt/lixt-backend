package br.com.ifsp.pi.lixt.data.business.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

	List<User> findAll();
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	@Query("select u from User u where u.activated = true and (u.email = ?1 or u.username = ?1)")
	User findByUsernameOrEmail(String username);

	@Query("select u from User u where u.id = ?1")
	User findUserById(Long id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.activated = true, u.firstAccessToken = null where u.firstAccessToken = ?1")
	Integer activeAccount(String token);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.activated = false where u.id = ?1")
	Integer desactiveAccount(Long id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.password = ?1 where u.email = ?2")
	Integer updatePassword(String password, String email);
	
	@Query("select u.username from User u where u.email = ?1")
	String findUsernameByEmail(String email);
	
	@Query("select u.firstAccessToken from User u where u.id = ?1")
	String findFirstAccesTokenById(Long id);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.globalCommentsChronOrder = ?2 where u.id = ?1")
	void saveGlobalCommentsPreferences(Long id, boolean preference);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.olderCommentsFirst = ?2 where u.id = ?1")
	void saveOlderCommentsFirst(Long id, boolean olderCommentsFirst);
}