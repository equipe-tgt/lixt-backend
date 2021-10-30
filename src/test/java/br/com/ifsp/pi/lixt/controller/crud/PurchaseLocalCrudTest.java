package br.com.ifsp.pi.lixt.controller.crud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.ifsp.pi.lixt.integration.geolocation.logger.GeolocationLoggerService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.locationtech.jts.io.ParseException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.controller.PurchaseLocalController;
import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints de local da compra")
@TestMethodOrder(OrderAnnotation.class)
class PurchaseLocalCrudTest {
	
	@Autowired
	private PurchaseLocalController purchaseLocalController;

	@Autowired
	private GeolocationLoggerService geolocationLoggerService;

	private PurchaseLocalDto purchaseLocal;
	
	@BeforeAll
	void createPurchaseLocal() {
		purchaseLocal = this.purchaseLocalController.save(
				PurchaseLocalDto.builder().name("Mercado Extra").latitude(23.66666).longitude(20.77777).build()
		);
		
		assertThat(purchaseLocal).isNotNull();
	}
	
	@Test
	@DisplayName("Buscar local da compra por id")
	@Order(1)
	void findPurchaseLocalById() {		
		assertThat(this.purchaseLocalController.findById(this.purchaseLocal.getId())).isNotNull();
	}
	
	@Test
	@DisplayName("Atualizar local da compra")
	@Order(2)
	void updatePurchaseLocal() {
		this.purchaseLocal.setName("Mercado Açaí");
		
		assertAll(
				() -> assertThat(this.purchaseLocalController.update(purchaseLocal, purchaseLocal.getId())).isOne(),
				() -> assertThat(this.purchaseLocalController.findById(this.purchaseLocal.getId()).getName()).isEqualTo(this.purchaseLocal.getName())
		);	
	}
	
	@Test
	@DisplayName("Atualizar local da compra com erro")
	@Order(3)
	void updatePurchaseLocalWithError() {
		this.purchaseLocal.setName("Mercado Assaí");
		assertThrows(PreconditionFailedException.class, () -> this.purchaseLocalController.update(purchaseLocal, 0l));
	}
	
	@Test
	@DisplayName("Encontrar mercados próximos")
	@Order(4)
	void findNearPurchaseLocals() throws ParseException {
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().longitude(20.77777).latitude(23.66666).build())).hasSize(1);
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().longitude(20.77778).latitude(23.66667).build())).hasSize(1);
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().longitude(20.77787).latitude(23.66676).build())).isEmpty();
		
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().name("Mercado Açaí").longitude(20.77777).latitude(23.66666).build())).hasSize(1);
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().name("Mercado Açaí").longitude(20.77778).latitude(23.66667).build())).hasSize(1);
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().name("Mercado Açaí").longitude(20.77787).latitude(23.66676).build())).isEmpty();
		
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().name("Mercado Extra").longitude(20.77777).latitude(23.66666).build())).isEmpty();
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().name("Mercado Extra").longitude(20.77778).latitude(23.66667).build())).isEmpty();
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().name("Mercado Extra").longitude(20.77787).latitude(23.66676).build())).isEmpty();
	}
	
	@Test
	@DisplayName("Criar mercado com erro")
	@Order(4)
	void createPurchaseLocalWithError() throws ParseException {
		
		assertThrows(
				PreconditionFailedException.class, 
				() -> this.purchaseLocalController.save(PurchaseLocalDto.builder().name("Mercado Extra").latitude(93.0).longitude(20.77777).build())
		);
		
		assertThrows(
				PreconditionFailedException.class, 
				() -> this.purchaseLocalController.save(PurchaseLocalDto.builder().name("Mercado Extra").latitude(-93.0).longitude(20.77777).build())
		);
		
		assertThrows(
				PreconditionFailedException.class, 
				() -> this.purchaseLocalController.save(PurchaseLocalDto.builder().name("Mercado Extra").latitude(23.66666).longitude(183.0).build())
		);
		
		assertThrows(
				PreconditionFailedException.class, 
				() -> this.purchaseLocalController.save(PurchaseLocalDto.builder().name("Mercado Extra").latitude(23.66666).longitude(-183.0).build())
		);
		
		assertThrows(
				PreconditionFailedException.class, 
				() -> this.purchaseLocalController.save(PurchaseLocalDto.builder().name("Mercado Extra").latitude(-93.0).longitude(-183.0).build())
		);
		
		assertThrows(
				PreconditionFailedException.class, 
				() -> this.purchaseLocalController.save(PurchaseLocalDto.builder().name("Mercado Extra").latitude(183.0).longitude(93.0).build())
		);
	}

	@Test
	@DisplayName("Procurar local não cadastrado e sem ponto de interesse próximo")
	@Order(5)
	void searchNearLocalNotSavedAndNotPOI() throws ParseException {
		assertThat(this.purchaseLocalController.findPurchasesLocalNear(PurchaseLocalDto.builder().latitude(24.2733562461887).longitude(16.36125626341889).build())).isEmpty();
	}

	@Test
	@DisplayName("Procurar local já cadastrado e exceder limite de requisições")
	@Order(6)
	void searchNearLocalSavedAndExceedNumberOfRequests() throws ParseException {

		Long numberOfRequests = this.geolocationLoggerService.getTotalCount();
		this.geolocationLoggerService.increaseCounter((100000-numberOfRequests));

		assertThat(this.purchaseLocalController.findPurchasesLocalNear(
				PurchaseLocalDto.builder().longitude(20.77777).latitude(23.66666).build()
		)).isNotEmpty();
		numberOfRequests += 1;

		this.geolocationLoggerService.increaseCounter(-(100000-numberOfRequests));
	}

	@Test
	@DisplayName("Procurar local já cadastrado e requisição da API com mesmo nome")
	@Order(7)
	void searchNearLocalSavedAndAPIResultWithSameName() throws ParseException {
		PurchaseLocalDto newPurchaseLocal = this.purchaseLocalController.save(
				PurchaseLocalDto.builder().name("Hirota Food Express, R. Pedro de Toledo, 591, São Paulo, São Paulo 04039, Brazil")
						.latitude(-23.598575943903683)
						.longitude(-46.64251755477454).build()
		);

		assertThat(newPurchaseLocal).isNotNull();

		assertThat(this.purchaseLocalController.findPurchasesLocalNear(
				PurchaseLocalDto.builder()
						.latitude(-23.598575943903683)
						.longitude(-46.64251755477454).build()
		)).hasSize(1);

		this.purchaseLocalController.deleteById(newPurchaseLocal.getId());
	}
	
	@AfterAll
	void delete() {
		this.purchaseLocalController.deleteById(purchaseLocal.getId());
	}
}
