package br.com.ifsp.pi.lixt.data.business.purchaseLocal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseLocalRepository extends CrudRepository<PurchaseLocal, Long>, JpaSpecificationExecutor<PurchaseLocal> {

}
