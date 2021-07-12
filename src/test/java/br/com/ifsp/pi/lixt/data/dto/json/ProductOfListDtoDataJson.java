package br.com.ifsp.pi.lixt.data.dto.json;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.instantiator.ProductOfListDtoInstantior;

public class ProductOfListDtoDataJson {
	
	private static List<String> productsOfList = new ArrayList<>();
	
	public static List<String> initializeValues(ListOfItemsDto listOfItems, Product product) {
		
		productsOfList.add(ProductOfListDtoInstantior.createProductOfListJson(2, listOfItems.getId(), "KG", "5", product.getId(), "Arroz branco"));
		productsOfList.add(ProductOfListDtoInstantior.createProductOfListJson(2, listOfItems.getId(), "KG", "5", product.getId(), "Arroz integral"));
		
		return productsOfList;
	}

}
