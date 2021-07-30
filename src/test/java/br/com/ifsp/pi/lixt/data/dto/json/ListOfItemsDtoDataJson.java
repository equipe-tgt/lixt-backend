package br.com.ifsp.pi.lixt.data.dto.json;

import br.com.ifsp.pi.lixt.instantiator.ListOfItemsDtoInstantior;

public abstract class ListOfItemsDtoDataJson {

	private ListOfItemsDtoDataJson() {}
	
	public static String initializeValues() {
		return ListOfItemsDtoInstantior.createListJson("Lista para testar funcionalidades do sistema", "Lista De Teste");
	}
	
}
