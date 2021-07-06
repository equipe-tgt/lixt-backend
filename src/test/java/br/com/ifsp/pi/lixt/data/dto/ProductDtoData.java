package br.com.ifsp.pi.lixt.data.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.mapper.CategoryMapper;

public class ProductDtoData {
	
	private static List<Product> products = new ArrayList<>();
	
	public static List<Product> initializeValues(CategoryDto category) {
		
		products.add(
				Product.builder()
					.name("Arroz")
					.categoryId(category.getId()).category(CategoryMapper.dtoToEntity(category))
					.measureType(MeasureType.KG).measureValue(new BigDecimal(5))
					.build()
		);
		
		products.add(
				Product.builder()
					.name("Feijão")
					.categoryId(category.getId()).category(CategoryMapper.dtoToEntity(category))
					.measureType(MeasureType.KG).measureValue(new BigDecimal(2))
					.build()
		);
		
		products.add(
				Product.builder()
					.name("Sal")
					.categoryId(category.getId()).category(CategoryMapper.dtoToEntity(category))
					.measureType(MeasureType.KG).measureValue(new BigDecimal(1))
					.build()
		);
		
		products.add(
				Product.builder()
					.name("Açúcar")
					.categoryId(category.getId()).category(CategoryMapper.dtoToEntity(category))
					.measureType(MeasureType.KG).measureValue(new BigDecimal(1))
					.build()
		);
		
		products.add(
				Product.builder()
					.name("Azeite")
					.categoryId(category.getId()).category(CategoryMapper.dtoToEntity(category))
					.measureType(MeasureType.L).measureValue(new BigDecimal(1))
					.build()
		);
		
		products.add(
				Product.builder()
					.name("Vinagre")
					.categoryId(category.getId()).category(CategoryMapper.dtoToEntity(category))
					.measureType(MeasureType.ML).measureValue(new BigDecimal(750))
					.build()
		);
		
		return products;
	}

}
