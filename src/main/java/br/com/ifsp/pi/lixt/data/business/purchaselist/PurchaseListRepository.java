package br.com.ifsp.pi.lixt.data.business.purchaselist;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseListRepository extends CrudRepository<PurchaseList, Long>, JpaSpecificationExecutor<PurchaseList> {

}
