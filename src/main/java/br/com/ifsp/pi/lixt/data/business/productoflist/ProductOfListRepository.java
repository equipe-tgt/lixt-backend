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
	@Query("UPDATE ProductOfList p SET p.userWhoMarkedId = null, p.isMarked = false WHERE p.listId = ?1")
	Integer cleanUserIdAtProductsOfList(Long listId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE ProductOfList p SET p.userWhoMarkedId = ?1, p.isMarked = true WHERE p.id IN (?2)")
	Integer markProducts(Long userId, List<Long> productsId);
	
}
