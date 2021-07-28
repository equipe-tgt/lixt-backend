package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.category.Category;
import br.com.ifsp.pi.lixt.dto.CategoryDto;

public abstract class CategoryMapper {
	
	private CategoryMapper() {}
	
	public static CategoryDto entityToDto(Category entity) {

		if(Objects.isNull(entity))
			return null;
		
		return CategoryDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.build();
	}
	
	public static Category dtoToEntity(CategoryDto dto) {

		if(Objects.isNull(dto))
			return null;
		
		return Category.builder()
				.id(dto.getId())
				.name(dto.getName())
				.build();
	}

}
