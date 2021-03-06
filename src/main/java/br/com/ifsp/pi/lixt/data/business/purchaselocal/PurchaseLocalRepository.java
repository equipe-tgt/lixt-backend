package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseLocalRepository extends CrudRepository<PurchaseLocal, Long>, JpaSpecificationExecutor<PurchaseLocal> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update PurchaseLocal p set p.name = ?2 where p.id = ?1")
	Integer updateNamePurchaseLocal(Long id, String name);
	
	@Query("select distinct pl from Purchase p join p.purchaseLocal pl where p.userId = ?1 order by p.purchaseDate desc")
	List<PurchaseLocal> findByUser(Long userId);
	
}
