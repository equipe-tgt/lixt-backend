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

import br.com.ifsp.pi.lixt.dashboard.proxy.DashboardTimeProxy;
import br.com.ifsp.pi.lixt.dashboard.request.DashboardTimeFilter;
import br.com.ifsp.pi.lixt.dashboard.response.IDashboardTime;

@Repository
public class PurchaseSpecRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<IDashboardTime> findDashboardTime(Long userId, DashboardTimeFilter filter) {
		String jpql = "";
		Map<String, Object> parameters = new HashMap<>();
		
		jpql = jpql.concat("SELECT SUM(ip.price) AS price, " + filter.getUnityTime().getParams("p.purchaseDate") + " AS time FROM Purchase p ");
		jpql = jpql.concat("JOIN p.purchaseLists pl JOIN pl.itemsOfPurchase ip JOIN ip.product pr JOIN pr.category c ");
		
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

}
