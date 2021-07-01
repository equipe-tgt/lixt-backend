package br.com.ifsp.pi.lixt.data.business.list;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.ifsp.pi.lixt.data.enumeration.StatusListMember;

public interface ListOfItemsRepository extends CrudRepository<ListOfItems, Long>, JpaSpecificationExecutor<ListOfItems> {

	@Query("select l from ListOfItems l join l.listMembers lm  where l.ownerId = ?1 or (lm.userId = ?1 and lm.statusListMember = ?2)")
	List<ListOfItems> findUserLists(Long idUser, StatusListMember status);
	
}
