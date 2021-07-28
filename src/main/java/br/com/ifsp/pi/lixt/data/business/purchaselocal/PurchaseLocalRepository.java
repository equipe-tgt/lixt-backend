package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import java.util.List;

import javax.transaction.Transactional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseLocalRepository extends CrudRepository<PurchaseLocal, Long>, JpaSpecificationExecutor<PurchaseLocal> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update PurchaseLocal p set p.name = ?2 where p.id = ?1")
	Integer updateNamePurchaseLocal(Long id, String name);
	
	@Query(value = "SELECT id_purchase_local, st_name, point_location FROM tb_purchase_local p "
			+ "WHERE st_distance_sphere(?1, point_location) <= ?2", nativeQuery = true)
	List<PurchaseLocal> findPurchasesLocalNear(Point localization, double distance);
	
}
