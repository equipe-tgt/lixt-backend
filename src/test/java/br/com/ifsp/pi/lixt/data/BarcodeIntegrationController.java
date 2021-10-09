package br.com.ifsp.pi.lixt.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import br.com.ifsp.pi.lixt.controller.ProductController;
import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.integration.BarcodeScheduleService;
import br.com.ifsp.pi.lixt.integration.counter.BarcodeCounterSql;
import br.com.ifsp.pi.lixt.utils.database.validators.ValidatorResponse;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar casos de integração do código de barras do sistema")
@TestMethodOrder(OrderAnnotation.class)
class BarcodeIntegrationController {
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private BarcodeScheduleService barcodeScheduleService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProductService productService;
	
	@BeforeAll
	void prepareData() {
		int result = this.jdbcTemplate.update("DELETE FROM tb_counter");
		assertThat(ValidatorResponse.wasDeleted(result)).isTrue();
		
		result = this.jdbcTemplate.update("DELETE FROM tb_counter");
		assertThat(ValidatorResponse.wasDeleted(result)).isFalse();
		
		this.barcodeScheduleService.prepareCounter();
		assertThat(jdbcTemplate.update(BarcodeCounterSql.updateBarcode())).isZero();
		
		this.barcodeScheduleService.prepareCounter();
		assertThat(jdbcTemplate.update(BarcodeCounterSql.updateBarcode())).isZero();
		
		this.jdbcTemplate.update("update tb_counter set dt_updated_at = '2020-01-01 00:00:00' where st_description = 'barcode-counter'");
		this.barcodeScheduleService.prepareCounter();
		assertThat(jdbcTemplate.update(BarcodeCounterSql.updateBarcode())).isZero();
		
		this.jdbcTemplate.update("update tb_counter set dt_updated_at = '2020-01-01 00:00:00' where st_description = 'barcode-counter'");
		this.barcodeScheduleService.clearBarcodeCount();
		assertThat(jdbcTemplate.update(BarcodeCounterSql.updateBarcode())).isZero();
		
		this.barcodeScheduleService.clearBarcodeCount();
		assertThat(jdbcTemplate.update(BarcodeCounterSql.updateBarcode())).isZero();
	}
	
	@Test
	void testResquestToApiBarcode() {
		ProductDto product = this.productController.findByBarcode("7891268400014");
		ProductDto product2 = this.productController.findByBarcode("7891268400014");
		assertThat(product.getId()).isEqualTo(product2.getId());
		
		assertThrows(RuntimeException.class, () -> this.productController.findByBarcode("789126840001400"));
		
		this.jdbcTemplate.update("update tb_counter set id_count = 25 where st_description = 'barcode-counter'");
		assertNull(this.productController.findByBarcode("789126840001400"));
		
		this.productService.deleteById(product.getId());
	}
	
	@AfterAll
	void fixBarcodeCounter() {
		this.jdbcTemplate.update("update tb_counter set id_count = 1 where st_description = 'barcode-counter'");
	}

}
