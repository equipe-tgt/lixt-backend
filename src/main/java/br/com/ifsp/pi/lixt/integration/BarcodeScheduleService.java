package br.com.ifsp.pi.lixt.integration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.integration.counter.CounterService;
import br.com.ifsp.pi.lixt.utils.database.validators.ValidatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarcodeScheduleService {
	
	private final CounterService counterService;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void clearBarcodeCount() {
		log.info("Resetando contador barcodeCounter...");
		Integer result = counterService.clearBarcodeCounter();
		
		if(ValidatorResponse.wasUpdated(result)) 
			log.info("BarcodeCounter resetado com sucesso!");
		else
			log.error("BarcodeCounter não pôde ser resetado...");
	}

}
