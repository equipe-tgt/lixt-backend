package br.com.ifsp.pi.lixt.integration.barcode.v1.logger;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BarcodeLoggerService {
	
	private final BarcodeLoggerRepository barcodeLoggerRepository;
	
	public BarcodeLogger save(BarcodeLogger barcodeLogger) {
		return this.barcodeLoggerRepository.save(barcodeLogger);
	}
	
	public BarcodeLogger barcodeWasSearchedToday(String barcode) {
		return this.barcodeLoggerRepository.barcodeWasSearchedToday(barcode);
	}

	public Long findCounterOfDay() {
		return this.barcodeLoggerRepository.findCounterOfDay();
	}
	
}
