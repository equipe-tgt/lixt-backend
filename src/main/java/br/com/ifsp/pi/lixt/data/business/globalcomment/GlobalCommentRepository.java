package br.com.ifsp.pi.lixt.data.business.globalcomment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GlobalCommentRepository extends CrudRepository<GlobalComment, Long>, JpaSpecificationExecutor<GlobalComment> {

	List<GlobalComment> findGlobalCommentsByProductId(Long productId);

	List<GlobalComment> findGlobalCommentsByUserId(Long userId);
	
	@Query("select count(gc) from GlobalComment gc where gc.productId = ?1 and gc.userId = ?2")
	Integer countGlobalCommentsAtProductByUser(Long productId, Long userId);
	
}
