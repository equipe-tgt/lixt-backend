package br.com.ifsp.pi.lixt.data.business.list;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ListOfItemsRepository extends CrudRepository<ListOfItems, Long>, JpaSpecificationExecutor<ListOfItems> {

}
