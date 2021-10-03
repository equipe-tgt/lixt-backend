package br.com.ifsp.pi.lixt.data.business.globalcomment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GlobalCommentRepository extends CrudRepository<GlobalComment, Long>, JpaSpecificationExecutor<GlobalComment> {

    List<GlobalComment> findGlobalCommentsByProductId(Long productId);

    List<GlobalComment> findGlobalCommentsByUserId(Long userId);
}
