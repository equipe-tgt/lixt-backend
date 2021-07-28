package br.com.ifsp.pi.lixt.data.business.itemofpurchase;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ItemOfPurchaseRepository extends CrudRepository<ItemOfPurchase, Long>, JpaSpecificationExecutor<ItemOfPurchase> {

}
