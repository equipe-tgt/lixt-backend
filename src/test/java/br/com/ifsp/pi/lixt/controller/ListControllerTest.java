package br.com.ifsp.pi.lixt.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;

@SpringBootTest
public class ListControllerTest {
	
	@Autowired
	private ListOfItemsController listOfItemsController;
	
	@Test
	public void createList() {
		
		ListOfItemsDto list = this.listOfItemsController.save(
				ListOfItemsDto.builder().nameList("Lista De Teste").ownerId((long)1).description("Teste").build()
		);
		
		assertThat(list.getId()).isNotNull();
	}

}
