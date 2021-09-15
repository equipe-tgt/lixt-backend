package br.com.ifsp.pi.lixt.data.business.productoflist;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.ifsp.pi.lixt.data.business.comment.Comment;

public interface ProductOfListRepository extends CrudRepository<ProductOfList, Long>, JpaSpecificationExecutor<ProductOfList> {

	@Query("SELECT c FROM ProductOfList p join p.comments c WHERE p.id = ?1")
	List<Comment> findCommentsByProductOfListId(Long id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE ProductOfList p SET p.userWhoMarkedId = null, p.isMarked = false, p.assignedUserId = null WHERE p.id = ?1")
	Integer cleanProductOfList(Long productOfListId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE ProductOfList p SET p.userWhoMarkedId = null, p.isMarked = false, p.assignedUserId = null WHERE p.listId = ?1")
	Integer cleanUserIdAtProductsOfList(Long listId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE ProductOfList p SET p.userWhoMarkedId = ?1, p.isMarked = true WHERE p.id = ?2")
	Integer markProduct(Long userId, Long productId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE ProductOfList p SET p.userWhoMarkedId = ?1, p.isMarked = true WHERE p.id IN (?2)")
	Integer markProducts(Long userId, List<Long> productsId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE ProductOfList p SET p.assignedUserId = ?1, p.userWhoMarkedId = null, p.isMarked = false WHERE p.id = ?2")
	Integer assignedItemToUser(Long userId, Long productOfListId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE ProductOfList p SET p.assignedUserId = ?1, p.userWhoMarkedId = ?1, p.isMarked = ?2 WHERE p.id = ?3")
	Integer assignedItemToUser(Long userId, boolean isMarked, Long productOfListId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE ProductOfList p SET p.markedAmount = ?1 WHERE p.id = ?2")
	Integer updateMarkedAmount(Integer markedAmount, Long productOfListId);
}
