package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.dto.ProductOfListDto;

public abstract class ProductOfListDtoInstantior {

	private ProductOfListDtoInstantior() {}
	
	public static String createProductOfListJson(int amount, Long listId, String measureType, String measureValue, Long productId, String name) {
		
		return "{" + 
				"  \"amount\": " + amount + "," + 
				"  \"assignedUserId\": null," + 
				"  \"amountComment\": null," + 
				"  \"id\": null," + 
				"  \"isMarked\": false," + 
				"  \"listId\": " + listId + "," + 
				"  \"measureType\": \"" + measureType + "\"," + 
				"  \"measureValue\": " + measureValue + "," + 
				"  \"name\": \"" + name + "\"," + 
				"  \"price\": null," + 
				"  \"product\": null," + 
				"  \"productId\": " + productId + "," + 
				"  \"userWhoMarkedId\": null" + 
				"}";
	}
	
	public static String createProductOfListJson(ProductOfListDto productOfList) {
		
		return "{" + 
				"  \"amount\": " + productOfList.getAmount() + "," + 
				"  \"assignedUserId\": " + productOfList.getAssignedUserId() + "," + 
				"  \"amountComment\": null," + 
				"  \"id\": " + productOfList.getId() + "," + 
				"  \"isMarked\": " + productOfList.getIsMarked() + "," + 
				"  \"listId\": " + productOfList.getListId() + "," + 
				"  \"measureType\": \"" + productOfList.getMeasureType() + "\"," + 
				"  \"measureValue\": " + productOfList.getMeasureValue() + "," + 
				"  \"name\": \"" + productOfList.getName() + "\"," + 
				"  \"price\": " + productOfList.getPrice() + "," + 
				"  \"product\": null," + 
				"  \"productId\": " + productOfList.getProductId() + "," + 
				"  \"userWhoMarkedId\": null" + 
				"}";
	}

}
