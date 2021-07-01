package br.com.ifsp.pi.lixt.data.business.comment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

}
