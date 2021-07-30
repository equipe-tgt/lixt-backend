package br.com.ifsp.pi.lixt.instantiator;

import java.math.BigDecimal;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.mapper.CategoryMapper;

public abstract class ProductDtoInstantior {

	private ProductDtoInstantior() {}
	
	public static Product createProduct(String name, CategoryDto category, MeasureType measureType, int measureValue) {
		return Product.builder()
				.name(name)
				.categoryId(category.getId()).category(CategoryMapper.dtoToEntity(category))
				.measureType(measureType).measureValue(new BigDecimal(750))
				.build();
	}
	
}
