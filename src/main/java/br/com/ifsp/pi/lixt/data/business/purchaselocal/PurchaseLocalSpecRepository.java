package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import br.com.ifsp.pi.lixt.utils.database.operations.Like;

@Repository
public class PurchaseLocalSpecRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<PurchaseLocal> findPurchasesLocalNear(PurchaseLocal purchaseLocal, double distance) {
		String sql = "";
		Map<String, Object> parameters = new HashMap<>();
		
		sql = sql.concat("SELECT id_purchase_local, st_name, point_location FROM tb_purchase_local ");
		sql = sql.concat("WHERE st_distance_sphere(:location, point_location) <= :distance ");
		
		parameters.put("location", purchaseLocal.getLocation());
		parameters.put("distance", distance);
		
		if(Objects.nonNull(purchaseLocal.getName())) {
			sql = sql.concat("AND st_name LIKE :name ");
			parameters.put("name", Like.contains(purchaseLocal.getName()));
		}
		
		sql = sql.concat("order by point_location");
		
		Query query = entityManager.createNativeQuery(sql, PurchaseLocal.class);
		parameters.forEach((k, v) -> query.setParameter(k, v));
		return query.getResultList();
	}
	
}
