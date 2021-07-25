package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;

public class ListOfItemsDtoInstantior {

	public static String createListJson(String name, String description) {
		
		return "{" + 
				"  \"description\": \"" + description + "\"," + 
				"  \"id\": null," + 
				"  \"listMembers\": null," + 
				"  \"nameList\": \"" + name + "\"," + 
				"  \"ownerId\": null," + 
				"  \"owner\": null," + 
				"  \"productsOfList\": null" + 
				"}";
	}
	
	public static String updateListJson(ListOfItemsDto list) {
		
		return "{" + 
				"  \"description\": \"" + list.getDescription() + "\"," + 
				"  \"id\": \"" + list.getId() + "\"," + 
				"  \"listMembers\": null," + 
				"  \"nameList\": \"" + list.getNameList() + "\"," + 
				"  \"ownerId\": null," + 
				"  \"owner\": null," + 
				"  \"productsOfList\": null" + 
				"}";
	}

}
