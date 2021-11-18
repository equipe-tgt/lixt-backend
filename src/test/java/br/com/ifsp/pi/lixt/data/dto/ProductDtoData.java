package br.com.ifsp.pi.lixt.data.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantiator;

public class ProductDtoData {

	private ProductDtoData() {}

	public static List<Product> initializeValues(CategoryDto category) {
		List<Product> products = new ArrayList<>();

		products.add(ProductDtoInstantiator.createProduct("Arroz", category, MeasureType.KG, 5));
		products.add(ProductDtoInstantiator.createProduct("Feijão", category, MeasureType.KG, 2));
		products.add(ProductDtoInstantiator.createProduct("Sal", category, MeasureType.KG, 1));
		products.add(ProductDtoInstantiator.createProduct("Açúcar", category, MeasureType.KG, 1));
		products.add(ProductDtoInstantiator.createProduct("Azeite", category, MeasureType.L, 750));

		return products;
	}

}
