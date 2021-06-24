package br.com.ifsp.pi.lixt.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.controller.ListOfItemsController;
import br.com.ifsp.pi.lixt.controller.ProductOfListController;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;

@SpringBootTest
public class ListCreateData {

	@Autowired
	private ListOfItemsController listOfItemsController;
	
	@Autowired
	private ProductOfListController productOfListController;
	
	@Test
	public void createList() {
		
		ListOfItemsDto list = this.listOfItemsController.save(
				ListOfItemsDto.builder().nameList("Lista De Teste").ownerId((long)1).description("Teste").build()
		);
		
		assertThat(list.getId()).isNotNull();
		
		this.productOfListController.save(
				ProductOfListDto.builder()
					.productId((long)2)
					.listId(list.getId())
					.name("Arroz legal")
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(5))
					.isMarked(false)
					.build()
		);
		
		this.productOfListController.save(
				ProductOfListDto.builder()
					.productId((long)2)
					.listId(list.getId())
					.name("Arroz do bem")
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(5))
					.isMarked(false)
					.build()
		);
	}
}
