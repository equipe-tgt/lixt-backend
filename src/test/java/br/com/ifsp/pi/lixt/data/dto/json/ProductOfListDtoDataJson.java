package br.com.ifsp.pi.lixt.data.dto.json;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.instantiator.ProductOfListDtoInstantiator;

public abstract class ProductOfListDtoDataJson {

	private ProductOfListDtoDataJson() {}

	public static List<String> initializeValues(ListOfItemsDto listOfItems, Product product) {
		List<String> productsOfList = new ArrayList<>();

		productsOfList.add(ProductOfListDtoInstantiator.createProductOfListJson(2, 0, listOfItems.getId(), "KG", "5", product.getId(), "Arroz branco"));
		productsOfList.add(ProductOfListDtoInstantiator.createProductOfListJson(2, 0, listOfItems.getId(), "KG", "5", product.getId(), "Arroz integral"));

		return productsOfList;
	}

	public static String createValue(ListOfItemsDto listOfItems, Product product) {
		return ProductOfListDtoInstantiator.createProductOfListJson(2, 0, listOfItems.getId(), "KG", "5", product.getId(), "Arroz integral");
	}

}
