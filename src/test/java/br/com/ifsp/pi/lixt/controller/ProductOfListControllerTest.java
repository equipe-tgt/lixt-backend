package br.com.ifsp.pi.lixt.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;

@SpringBootTest
public class ProductOfListControllerTest {
	
	@Autowired
	private ProductOfListController productOfListController;
	
	@Test
	public void createProductOfList() {
		
		this.productOfListController.save(
				ProductOfListDto.builder()
					.productId((long)2)
					.listId((long)1)
					.name("Arroz legal")
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(5))
					.isMarked(false)
					.build()
		);
	}

}
