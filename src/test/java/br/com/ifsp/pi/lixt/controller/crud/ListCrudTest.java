package br.com.ifsp.pi.lixt.controller.crud;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.controller.ListOfItemsController;
import br.com.ifsp.pi.lixt.controller.ProductOfListController;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints de lista e itens da lista")
public class ListCrudTest {

	@Autowired
	private ListOfItemsController listOfItemsController;
	
	@Autowired
	private ProductOfListController productOfListController;
	
	private List<ProductOfListDto> listProductsOfList;
	private ListOfItemsDto listOfItems;
	
	@BeforeAll
	public void createList() {
		
		listOfItems = this.listOfItemsController.save(
				ListOfItemsDto.builder().nameList("Lista De Teste").ownerId((long)1).description("Teste").build()
		);
		
		listProductsOfList = new ArrayList<>();
		
		assertThat(listOfItems.getId()).isNotNull();
		
		listProductsOfList.add(
				ProductOfListDto.builder()
					.productId((long)2)
					.listId(listOfItems.getId())
					.name("Arroz legal")
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(5))
					.isMarked(false)
					.build()
		);
		
		listProductsOfList.add(
				ProductOfListDto.builder()
					.productId((long)2)
					.listId(listOfItems.getId())
					.name("Arroz do bem")
					.measureType(MeasureType.KG)
					.measureValue(new BigDecimal(5))
					.isMarked(false)
					.build()
		);
		
		listProductsOfList = this.productOfListController.saveAll(listProductsOfList);
	}
	
	@Test
	@DisplayName("Testar endpoints de busca de lista")
	public void validateList() {
		assertThat(this.listOfItemsController.findById(listOfItems.getId()).getProductsOfList().size()).isEqualTo(listProductsOfList.size());
	}
	
	@AfterAll
	public void deleteList() {
		this.productOfListController.deleteById(listProductsOfList.get(0).getId());
		
		this.listOfItemsController.deleteById(listOfItems.getId());
	}
	
}
