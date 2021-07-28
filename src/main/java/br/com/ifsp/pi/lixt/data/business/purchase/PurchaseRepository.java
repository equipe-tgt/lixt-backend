package br.com.ifsp.pi.lixt.data.business.purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {

	@Query("SELECT p FROM Purchase p where p.userId = ?1")
	List<Purchase> findUserPurchases(Long userId);
	
}
