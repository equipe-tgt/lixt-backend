package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseLocalRepository extends CrudRepository<PurchaseLocal, Long>, JpaSpecificationExecutor<PurchaseLocal> {

	@Query(value = "SELECT id_purchase_local, st_name, point_location FROM tb_purchase_local p "
			+ "WHERE st_distance_sphere(?1, point_location) <= ?2", nativeQuery = true)
	List<PurchaseLocal> findPurchasesLocalNear(Point localization, double distance);
	
}
