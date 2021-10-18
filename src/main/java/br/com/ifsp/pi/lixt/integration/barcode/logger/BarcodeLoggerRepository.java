package br.com.ifsp.pi.lixt.integration.barcode.logger;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BarcodeLoggerRepository extends JpaRepository<BarcodeLogger, Long> {
	
	@Query("select bl from BarcodeLogger bl where date(bl.createdAt) = date(current_date) and bl.barcode = ?1")
	BarcodeLogger barcodeWasSearchedToday(String barcode);
	
	@Query("select count(bl) from BarcodeLogger bl where date(bl.createdAt) = date(current_date)")
	Long findCounterOfDay();
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("delete from BarcodeLogger bl where bl.counterOfDay > ?1 and date(bl.createdAt) = date(current_date)")
	Integer deleteValuesByTest(Long minAmount);

}
