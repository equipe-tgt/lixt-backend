package br.com.ifsp.pi.lixt.data.business.productoflist;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ProductOfListRepository extends CrudRepository<ProductOfList, Long>, JpaSpecificationExecutor<ProductOfList> {

}
