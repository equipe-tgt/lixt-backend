package br.com.ifsp.pi.lixt.integration.barcode;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.integration.barcode.v2.counter.BarcodeCounterSql;
import br.com.ifsp.pi.lixt.utils.database.validators.ValidatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarcodeScheduleService {
	
	private final JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void prepareCounter() {
		this.jdbcTemplate.execute(BarcodeCounterSql.createBarcodeCounterTable());
		this.jdbcTemplate.update(BarcodeCounterSql.insertIfIsEmpty());		
		this.jdbcTemplate.update(BarcodeCounterSql.updateIfLate());		
	}
	
	@Scheduled(cron = "0 0 0 * * *")
	public void clearBarcodeCount() {
		log.info("Resetando contador barcodeCounter...");
		Integer result = this.jdbcTemplate.update(BarcodeCounterSql.updateBarcode());
		
		if(ValidatorResponse.wasUpdated(result)) 
			log.info("BarcodeCounter resetado com sucesso!");
		else
			log.error("BarcodeCounter não pôde ser resetado...");
	}

}
