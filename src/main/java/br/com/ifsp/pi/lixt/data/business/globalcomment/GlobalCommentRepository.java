package br.com.ifsp.pi.lixt.data.business.globalcomment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GlobalCommentRepository extends CrudRepository<GlobalComment, Long>, JpaSpecificationExecutor<GlobalComment> {
	@Query("SELECT gc FROM GlobalComment gc where gc.productId = ?1")
	List<GlobalComment> findGlobalCommentsByProductId(Long productId);

	@Query("SELECT gc FROM GlobalComment gc where gc.userId = ?1 and gc.productId = ?2")
	List<GlobalComment> findGlobalCommentsByUserIdAndProductId(Long userId, Long productId);
	
	@Query("select count(gc) from GlobalComment gc where gc.productId = ?1 and gc.userId = ?2")
	Integer countGlobalCommentsAtProductByUser(Long productId, Long userId);
	
}
