package br.com.ifsp.pi.lixt.data.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;

public class ProductOfListDtoDataJson {
	
	private static List<String> productsOfList = new ArrayList<>();
	
	public static List<String> initializeValues(ListOfItemsDto listOfItems, Product product) {

		productsOfList.add(
				"{" + 
				"  \"amount\": 2," + 
				"  \"assignedUserId\": null," + 
				"  \"comments\": null," + 
				"  \"id\": null," + 
				"  \"isMarked\": false," + 
				"  \"listId\": " + listOfItems.getId() + "," + 
				"  \"measureType\": \"KG\"," + 
				"  \"measureValue\": 5," + 
				"  \"name\": \"Arroz branco\"," + 
				"  \"price\": null," + 
				"  \"product\": null," + 
				"  \"productId\": " + product.getId() + "," + 
				"  \"userWhoMarkedId\": null" + 
				"}"
		);
		
		productsOfList.add(
				"{" + 
				"  \"amount\": 2," + 
				"  \"assignedUserId\": null," + 
				"  \"comments\": null," + 
				"  \"id\": null," + 
				"  \"isMarked\": false," + 
				"  \"listId\": " + listOfItems.getId() + "," + 
				"  \"measureType\": \"KG\"," + 
				"  \"measureValue\": 5," + 
				"  \"name\": \"Arroz integral\"," + 
				"  \"price\": null," + 
				"  \"product\": null," + 
				"  \"productId\": " + product.getId() + "," + 
				"  \"userWhoMarkedId\": null" + 
				"}"		
		);
		
		return productsOfList;
	}

}
