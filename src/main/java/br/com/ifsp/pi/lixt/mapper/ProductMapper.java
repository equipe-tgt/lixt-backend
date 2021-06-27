package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.dto.ProductDto;

public abstract class ProductMapper {

	public static ProductDto entityToDto(Product entity) {

		if(Objects.isNull(entity))
			return null;
		
		return ProductDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.userId(entity.getUserId())
				.categoryId(entity.getCategoryId())
				.barcode(entity.getBarcode())
				.measureType(entity.getMeasureType())
				.measureValue(entity.getMeasureValue())
				.category(CategoryMapper.entityToDto(entity.getCategory()))
				.build();
	}
	
	public static Product dtoToEntity(ProductDto dto) {

		if(Objects.isNull(dto))
			return null;
		
		return Product.builder()
				.id(dto.getId())
				.name(dto.getName())
				.userId(dto.getUserId())
				.categoryId(dto.getCategoryId())
				.barcode(dto.getBarcode())
				.measureType(dto.getMeasureType())
				.measureValue(dto.getMeasureValue())
				.category(CategoryMapper.dtoToEntity(dto.getCategory()))
				.build();
	}
}
