package br.com.ifsp.pi.lixt.data.business.purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.ifsp.pi.lixt.dashboard.request.DashboardCategoryFilter;
import br.com.ifsp.pi.lixt.dashboard.response.IDashboardCategory;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {

	@Query("SELECT p FROM Purchase p where p.userId = ?1")
	List<Purchase> findUserPurchases(Long userId);
	
	@Query("SELECT MONTH(p.purchaseDate) as month, SUM(ip.price) as price FROM Purchase p "
			+ "JOIN p.purchaseLists pl JOIN pl.itemsOfPurchase ip JOIN ip.product pr JOIN pr.category c "
			+ "WHERE p.userId = :userId AND c.name = :#{#filter.category} AND p.purchaseDate BETWEEN :#{#filter.minDate} AND :#{#filter.maxDate} "
			+ "GROUP BY MONTH(p.purchaseDate) ORDER BY MONTH(p.purchaseDate) ASC")
	List<IDashboardCategory> findDashboardCategory(@Param("userId") Long userId, @Param("filter") DashboardCategoryFilter filter);
	
}
