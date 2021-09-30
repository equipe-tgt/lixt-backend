package br.com.ifsp.pi.lixt.integration.counter;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CounterService {
	
	private final CounterRepository counterRepository;
	
	public Long findCountByDescription(String description) {
		return this.counterRepository.findCountByDescription(description);
	}
	
	public Integer clearBarcodeCounter() {
		return this.counterRepository.clearBarcodeCounter();
	}
	
	public Integer updateCountAtBarcodeCounter(Long value) {
		return this.counterRepository.updateCountAtBarcodeCounter(value);
	}

}
