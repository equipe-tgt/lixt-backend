package br.com.ifsp.pi.lixt.integration.counter;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CounterRepository extends JpaRepository<Counter, Long> {
	
	@Query("select c.count from Counter c where c.description = ?1")
	Long findCountByDescription(String description);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update Counter c set c.count = 0, c.updatedAt = current_date where c.description = 'barcode-counter' and c.updatedAt < current_date")
	Integer clearBarcodeCounter();
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update Counter c set c.count = ?1, c.updatedAt = current_date where c.description = 'barcode-counter'")
	Integer updateCountAtBarcodeCounter(Long value);

}
