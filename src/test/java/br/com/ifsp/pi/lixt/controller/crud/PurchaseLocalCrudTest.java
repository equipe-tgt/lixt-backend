package br.com.ifsp.pi.lixt.controller.crud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.controller.PurchaseLocalController;
import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;

@SpringBootTest
public class PurchaseLocalCrudTest {
	
	@Autowired
	private PurchaseLocalController purchaseLocalController;
	
	@Test
	public void createPurchaseLocal() {
		this.purchaseLocalController.save(PurchaseLocalDto.builder().name("Mercado Extra").latitude(23.66666).longitude(20.77777).build());
	}

}
