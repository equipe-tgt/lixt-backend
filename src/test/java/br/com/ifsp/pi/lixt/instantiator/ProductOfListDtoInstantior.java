package br.com.ifsp.pi.lixt.instantiator;

public class ProductOfListDtoInstantior {

	public static String createProductOfListJson(int amount, Long listId, String measureType, String measureValue, Long productId, String name) {
		
		return "{" + 
				"  \"amount\": " + amount + "," + 
				"  \"assignedUserId\": null," + 
				"  \"comments\": null," + 
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

}
