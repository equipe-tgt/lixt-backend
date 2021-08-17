package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.utils.conversion.Operators;

public abstract class ListOfItemsDtoInstantior extends Operators {

	private ListOfItemsDtoInstantior() {}
	
	public static String createListJson(String name, String description) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder = stringBuilder.append(KEY_OPEN)
				.append(createJsonLine("description", description)).append(COMMA)
				.append(createJsonLine("listMembers")).append(COMMA)				
				.append(createJsonLine("id")).append(COMMA)
				.append(createJsonLine("nameList", name)).append(COMMA)
				.append(createJsonLine("ownerId")).append(COMMA)
				.append(createJsonLine("owner")).append(COMMA)
				.append(createJsonLine("productsOfList"))
				.append(KEY_CLOSE);
		
		return stringBuilder.toString();
	}
	
	public static String updateListJson(ListOfItemsDto list) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder = stringBuilder.append(KEY_OPEN)
				.append(createJsonLine("description", list.getDescription())).append(COMMA)
				.append(createJsonLine("listMembers")).append(COMMA)				
				.append(createJsonLine("id", list.getId())).append(COMMA)
				.append(createJsonLine("nameList", list.getNameList())).append(COMMA)
				.append(createJsonLine("ownerId")).append(COMMA)
				.append(createJsonLine("owner")).append(COMMA)
				.append(createJsonLine("productsOfList"))
				.append(KEY_CLOSE);
		
		return stringBuilder.toString();
	}

}
