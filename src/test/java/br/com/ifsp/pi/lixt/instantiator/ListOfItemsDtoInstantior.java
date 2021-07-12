package br.com.ifsp.pi.lixt.instantiator;

public class ListOfItemsDtoInstantior {

	public static String createListJson(String name, String description) {
		
		return "{" + 
				"  \"description\": \"" + description + "\"," + 
				"  \"id\": null," + 
				"  \"listMembers\": null," + 
				"  \"nameList\": \"" + name + "\"," + 
				"  \"ownerId\": null," + 
				"  \"productsOfList\": null" + 
				"}";
	}

}
