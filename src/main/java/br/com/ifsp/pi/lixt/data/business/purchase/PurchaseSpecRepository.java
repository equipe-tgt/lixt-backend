package br.com.ifsp.pi.lixt.data.business.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.ifsp.pi.lixt.dashboard.proxy.DashboardProductProxy;
import br.com.ifsp.pi.lixt.dashboard.proxy.DashboardTimeProxy;
import br.com.ifsp.pi.lixt.dashboard.request.DashboardProductFilter;
import br.com.ifsp.pi.lixt.dashboard.request.DashboardTimeFilter;
import br.com.ifsp.pi.lixt.dashboard.response.IDashboardProduct;
import br.com.ifsp.pi.lixt.dashboard.response.IDashboardTime;
import br.com.ifsp.pi.lixt.utils.database.operations.Like;

@Repository
public class PurchaseSpecRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<IDashboardTime> findDashboardTime(Long userId, DashboardTimeFilter filter) {
		String jpql = "";
		Map<String, Object> parameters = new HashMap<>();
		
		jpql = jpql.concat("SELECT SUM(ip.price) AS price, " + filter.getUnityTime().getParams("p.purchaseDate") + " AS time FROM Purchase p ");
		jpql = jpql.concat("JOIN p.purchaseLists pl JOIN pl.itemsOfPurchase ip ");
		
		jpql = jpql.concat("WHERE p.userId = :userId ");
		parameters.put("userId", userId);
		
		if(Objects.nonNull(filter.getListId())) {
			jpql = jpql.concat("AND pl.listId = :listId ");
			parameters.put("listId", filter.getListId());
		}
		if(Objects.nonNull(filter.getStartDate())) {
			jpql = jpql.concat("AND p.purchaseDate > :startDate ");
			parameters.put("startDate", filter.getStartDate());
		}
		if(Objects.nonNull(filter.getEndDate())) {
			jpql = jpql.concat("AND p.purchaseDate < :endDate ");
			parameters.put("endDate", filter.getEndDate());
		}
		
		jpql = jpql.concat("GROUP BY " + filter.getUnityTime().getParams("p.purchaseDate") + " ");
		jpql = jpql.concat("ORDER BY " + filter.getUnityTime().getParams("p.purchaseDate") + " ASC");
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		parameters.forEach(query::setParameter);
		return query.getResultList().stream().map(DashboardTimeProxy::map).collect(Collectors.toList());
	}
	
	public List<IDashboardProduct> findDashboardProduct(Long userId, DashboardProductFilter filter) {
		String jpql = "";
		Map<String, Object> parameters = new HashMap<>();
		
		jpql = jpql.concat("SELECT p.purchaseDate AS date, ip.price as price FROM Purchase p ");
		jpql = jpql.concat("JOIN p.purchaseLists pl JOIN pl.itemsOfPurchase ip ");
		
		jpql = jpql.concat("WHERE p.userId = :userId ");
		parameters.put("userId", userId);
		
		if(Objects.nonNull(filter.getMinDate())) {
			jpql = jpql.concat("AND p.purchaseDate > :startDate ");
			parameters.put("startDate", filter.getMinDate());
		}
		if(Objects.nonNull(filter.getMaxDate())) {
			jpql = jpql.concat("AND p.purchaseDate < :endDate ");
			parameters.put("endDate", filter.getMaxDate());
		}
		if(Objects.nonNull(filter.getPurchaseLocalId())) {
			jpql = jpql.concat("AND p.purchaseLocalId = :purchaseLocalId ");
			parameters.put("purchaseLocalId", filter.getPurchaseLocalId());
		}
		if(Objects.nonNull(filter.getMeasureType())) {
			jpql = jpql.concat("AND ip.purchaseLocalId = :measureType ");
			parameters.put("measureType", filter.getMeasureType());
		}
		
		if(Objects.nonNull(filter.getName()) && Objects.nonNull(filter.getBrand())) {
			jpql = jpql.concat("AND ip.name = :name ");
			parameters.put("name", Like.contains(filter.getName() + "%" + filter.getBrand()));
		}
		else if(Objects.nonNull(filter.getName())) {
			jpql = jpql.concat("AND ip.name = :name ");
			parameters.put("name", Like.contains(filter.getName()));
		}
		else if(Objects.nonNull(filter.getBrand())) {
			jpql = jpql.concat("AND ip.name = :name ");
			parameters.put("name", Like.contains(filter.getBrand()));
		}
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		parameters.forEach(query::setParameter);
		return query.getResultList().stream().map(DashboardProductProxy::map).collect(Collectors.toList());
	}

}
