package br.com.ifsp.pi.lixt.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import br.com.ifsp.pi.lixt.controller.ProductController;
import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.integration.barcode.logger.BarcodeLogger;
import br.com.ifsp.pi.lixt.integration.barcode.logger.BarcodeLoggerRepository;
import br.com.ifsp.pi.lixt.utils.database.validators.ValidatorResponse;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar casos de integração do código de barras do sistema")
@TestMethodOrder(OrderAnnotation.class)
class BarcodeIntegrationControllerTest {
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BarcodeLoggerRepository barcodeLoggerRepository;
	
	@Test
	void testResquestToApiBarcode() {
		Long requests = barcodeLoggerRepository.findCounterOfDay();
		
		ProductDto product = this.productController.findByBarcode("7891268400014");
		ProductDto product2 = this.productController.findByBarcode("7891268400014");
		assertThat(product.getId()).isEqualTo(product2.getId());
		
		assertNull(this.productController.findByBarcode("789126840001400"));
		assertNull(this.productController.findByBarcode("789126840001400"));
				
		for(Integer i = requests.intValue() + 3; i <= 25; i++) {
			this.barcodeLoggerRepository.save(BarcodeLogger.builder().counterOfDay(i.longValue()).build());
		}
		
		assertThat(this.barcodeLoggerRepository.findCounterOfDay()).isEqualTo(25);
		
		assertNull(this.productController.findByBarcode("7891268400014000"));
		
		this.productService.deleteById(product.getId());
		assertThat(ValidatorResponse.wasDeleted(this.barcodeLoggerRepository.deleteValuesByTest(requests))).isTrue();
		assertThat(ValidatorResponse.wasDeleted(this.barcodeLoggerRepository.deleteValuesByTest(requests))).isFalse();
	}

}
