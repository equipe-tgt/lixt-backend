package br.com.ifsp.pi.lixt.data.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantior;

public class ProductDtoData {
	
	private static List<Product> products = new ArrayList<>();
	
	public static List<Product> initializeValues(CategoryDto category) {
		
		products.add(ProductDtoInstantior.createProduct("Arroz", category, MeasureType.KG, 5));
		products.add(ProductDtoInstantior.createProduct("Feijão", category, MeasureType.KG, 2));
		products.add(ProductDtoInstantior.createProduct("Sal", category, MeasureType.KG, 1));
		products.add(ProductDtoInstantior.createProduct("Açúcar", category, MeasureType.KG, 1));
		products.add(ProductDtoInstantior.createProduct("Azeite", category, MeasureType.L, 750));
		
		return products;
	}

}
