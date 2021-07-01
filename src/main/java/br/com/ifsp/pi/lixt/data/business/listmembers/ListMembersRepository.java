package br.com.ifsp.pi.lixt.data.business.listmembers;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ListMembersRepository extends CrudRepository<ListMembers, Long>, JpaSpecificationExecutor<ListMembers> {

}
