package br.com.ifsp.pi.lixt.data.business.list;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ListOfItemsRepository extends CrudRepository<ListOfItems, Long>, JpaSpecificationExecutor<ListOfItems> {

	@Query("select l from ListOfItems l where l.ownerId = ?1")
	List<ListOfItems> findUserLists(Long idUser);
	
}
