package br.com.ifsp.pi.lixt.data.business.productoflist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.ifsp.pi.lixt.data.business.comment.Comment;

public interface ProductOfListRepository extends CrudRepository<ProductOfList, Long>, JpaSpecificationExecutor<ProductOfList> {

	@Query("SELECT c FROM ProductOfList p join p.comments c WHERE p.id = ?1")
	List<Comment> findCommentsByProductOfListId(Long id);
	
}
