package br.com.ifsp.pi.lixt.controller.crud;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.controller.PurchaseLocalController;
import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;

@SpringBootTest
@DisplayName("Testar endpoints de local da compra")
class PurchaseLocalCrudTest {
	
	@Autowired
	private PurchaseLocalController purchaseLocalController;
	
	@Test
	@DisplayName("Testar armazenamento do local da compra")
	void createPurchaseLocal() {
		PurchaseLocalDto purchaseLocal = this.purchaseLocalController.save(
				PurchaseLocalDto.builder().name("Mercado Extra").latitude(23.66666).longitude(20.77777).build()
		);
		
		assertThat(purchaseLocal).isNotNull();
	}

}
