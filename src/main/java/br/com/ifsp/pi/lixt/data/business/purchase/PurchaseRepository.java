package br.com.ifsp.pi.lixt.data.business.purchase;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {

}
