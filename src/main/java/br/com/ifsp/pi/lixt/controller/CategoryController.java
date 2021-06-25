package br.com.ifsp.pi.lixt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.data.business.category.CategoryService;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
	
	@GetMapping("/{id}")
	public CategoryDto findById(@PathVariable Long id) {
		return CategoryMapper.entityToDto(this.categoryService.findById(id));
	}
	
	@PostMapping
	public CategoryDto save(@RequestBody(required = false) CategoryDto category) {
		return CategoryMapper.entityToDto(this.categoryService.save(CategoryMapper.dtoToEntity(category)));
	}
	
	@PostMapping("/save-all")
	public List<CategoryDto> saveAll(@RequestBody(required = false) List<CategoryDto> lists) {
		return this.categoryService.saveAll(lists.stream().map(CategoryMapper::dtoToEntity).collect(Collectors.toList()))
				.stream().map(CategoryMapper::entityToDto).collect(Collectors.toList());
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.categoryService.deleteById(id);
	}

}
