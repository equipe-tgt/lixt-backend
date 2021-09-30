package br.com.ifsp.pi.lixt.data.business.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	@Query("select p from Product p where p.name like ?1 and (p.userId = ?2 or p.userId is null)")
	List<Product> findByName(String name, Long userId, Pageable pageable);
	
	@Query("select p from Product p where p.barcode = ?1")
	Product findByBarcode(String barcode);
	
}
