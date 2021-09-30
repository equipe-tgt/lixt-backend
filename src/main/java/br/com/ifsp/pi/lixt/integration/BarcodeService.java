package br.com.ifsp.pi.lixt.integration;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
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
	private final JdbcTemplate jdbcTemplate;
	private final CounterService counterService;
	
	@PostConstruct
	public void prepareCounter() {
		Long count = jdbcTemplate.queryForObject(BarcodeCounterSql.count(), Long.class);
		
		if(count == 0)
			jdbcTemplate.update(BarcodeCounterSql.insertBarcodeCounter());
		else 
			jdbcTemplate.update(BarcodeCounterSql.updateBarcode());
	}
	
	public Map<String, Object> getProductByBarcode(String barcode) {
		Long requestsDone = this.counterService.findCountByDescription(BarcodeCounterSql.getBarcodeCounterDescription());

		if(requestsDone < MAX_AMOUNT_REQUEST) {
			this.counterService.updateCountAtBarcodeCounter(requestsDone+1);
			return this.getProductAtBarcodeApi(barcode);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> getProductAtBarcodeApi(String barcode) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Cosmos-Token", token);
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		return template.exchange(generateUrl(barcode), HttpMethod.GET, entity, Map.class).getBody();
	}
	
	private String generateUrl(String barcode) {
		return url + barcode + ".json";
	}

}
