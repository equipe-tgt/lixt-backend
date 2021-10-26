package br.com.ifsp.pi.lixt.integration.barcode;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.product.ProductRepository;
import br.com.ifsp.pi.lixt.integration.barcode.logger.BarcodeLoggerService;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.mapper.logger.BarcodeLoggerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarcodeService {
	
	@Value("${lixt.barcode.token}") String token;
	@Value("${lixt.barcode.url}") String url;
	
	private static final Long MAX_AMOUNT_REQUEST = Long.parseLong("25");
	
	private final RestTemplate template = new RestTemplate();
	private final ProductRepository productRepository;
	private final BarcodeLoggerService barcodeLoggerService;
	
	public Product findByBarcode(String barcode) {
		var product = productRepository.findByBarcode(barcode);
		
		if(Objects.nonNull(product))
			return product;
		
		var barcodeLogger = this.barcodeLoggerService.barcodeWasSearchedToday(barcode);
		
		if(Objects.nonNull(barcodeLogger))
			return null;
		
		Long requestsDone = this.barcodeLoggerService.findCounterOfDay();

		if(requestsDone < MAX_AMOUNT_REQUEST) {
			try {
				product = this.productRepository.save(ProductMapper.modelIntoApiParams(this.doRequest(barcode)));
				this.barcodeLoggerService.save(BarcodeLoggerMapper.build(requestsDone + 1, barcode, product.getId()));
				log.info("Produto " + product.getName() + " (código de barras: " + barcode + ") cadastrado na plataforma. [" + (requestsDone+1) + "/25]");
				return product;
			}
			catch(Exception e) {
				this.barcodeLoggerService.save(BarcodeLoggerMapper.build(requestsDone + 1, barcode));
				log.error("Produto (código de barras: " + barcode + ") não encontrado. [" + (requestsDone+1) + "/25]");
				return null;
			}
		}
		
		log.warn("Produto não pôde ser buscado. [" + requestsDone + "/25]");
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
