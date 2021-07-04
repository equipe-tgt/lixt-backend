package br.com.ifsp.pi.lixt.data.business.listmembers;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ListMembersRepository extends CrudRepository<ListMembers, Long>, JpaSpecificationExecutor<ListMembers> {

	@Query("select lm.userId from ListMembers lm where lm.id = ?1")
	Long findUserIdByListMembersId(Long listMembersId);
	
}
