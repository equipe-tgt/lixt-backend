package br.com.ifsp.pi.lixt.data.business.comment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

	@Query("select c.userId from Comment c where c.id = ?1")
	Long findUserIdByCommentId(Long commentId);
	
}
