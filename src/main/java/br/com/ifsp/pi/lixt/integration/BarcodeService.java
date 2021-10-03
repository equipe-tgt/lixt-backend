package br.com.ifsp.pi.lixt.integration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.ifsp.pi.lixt.integration.counter.BarcodeCounterSql;
import br.com.ifsp.pi.lixt.integration.counter.CounterService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BarcodeService {
	
	@Value("${lixt.barcode.token}") String token;
	@Value("${lixt.barcode.url}") String url;
	
	private static final Long MAX_AMOUNT_REQUEST = Long.parseLong("25");
	
	private final RestTemplate template = new RestTemplate();
	private final CounterService counterService;
	
	public Map<String, Object> getProductByBarcode(String barcode) {
		Long requestsDone = this.counterService.findCountByDescription(BarcodeCounterSql.getBarcodeCounterDescription());

		if(requestsDone < MAX_AMOUNT_REQUEST) {
			this.counterService.updateCountAtBarcodeCounter(requestsDone+1);
			return this.doRequest(barcode);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> doRequest(String barcode) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Cosmos-Token", token);
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		return template.exchange(generateUrl(barcode), HttpMethod.GET, entity, Map.class).getBody();
	}
	
	private String generateUrl(String barcode) {
		return url + barcode + ".json";
	}

}
