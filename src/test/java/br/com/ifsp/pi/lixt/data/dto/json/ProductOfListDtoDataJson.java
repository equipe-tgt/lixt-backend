package br.com.ifsp.pi.lixt.data.dto.json;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.instantiator.ProductOfListDtoInstantior;

public abstract class ProductOfListDtoDataJson {
	
	private ProductOfListDtoDataJson() {}
	
	public static List<String> initializeValues(ListOfItemsDto listOfItems, Product product) {
		List<String> productsOfList = new ArrayList<>();

		productsOfList.add(ProductOfListDtoInstantior.createProductOfListJson(2, listOfItems.getId(), "KG", "5", product.getId(), "Arroz branco"));
		productsOfList.add(ProductOfListDtoInstantior.createProductOfListJson(2, listOfItems.getId(), "KG", "5", product.getId(), "Arroz integral"));
		
		return productsOfList;
	}
	
	public static String createValue(ListOfItemsDto listOfItems, Product product) {
		return ProductOfListDtoInstantior.createProductOfListJson(2, listOfItems.getId(), "KG", "5", product.getId(), "Arroz integral");
	}

}
