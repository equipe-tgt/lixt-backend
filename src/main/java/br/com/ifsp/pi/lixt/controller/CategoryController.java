package br.com.ifsp.pi.lixt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.data.business.category.CategoryService;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.mapper.CategoryMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionUpdateFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de categoria")
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
	
	@ApiOperation(value = "Buscar categoria por id")
	@GetMapping("/{id}")
	public CategoryDto findById(@PathVariable Long id) {
		return CategoryMapper.entityToDto(this.categoryService.findById(id));
	}
	
	@ApiOperation(value = "Buscar todas as categorias")
	@GetMapping()
	public List<CategoryDto> findAll() {
		return this.categoryService.findAll().stream().map(CategoryMapper::entityToDto).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Salvar uma categoria")
	@PostMapping
	public CategoryDto save(@RequestBody(required = false) CategoryDto category) {
		return CategoryMapper.entityToDto(this.categoryService.save(CategoryMapper.dtoToEntity(category)));
	}
	
	@ApiOperation(value = "Atualizar categoria")
	@PutMapping("/{id}")
	public CategoryDto update(@RequestBody(required = false) CategoryDto category, @PathVariable Long id) throws PreconditionUpdateFailedException {
		
		if(!category.getId().equals(id))
			throw new PreconditionUpdateFailedException("Erro ao atualizar categoria");
		
		return CategoryMapper.entityToDto(this.categoryService.save(CategoryMapper.dtoToEntity(category)));
	}
	
	@ApiOperation(value = "Deletar uma categoria")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.categoryService.deleteById(id);
	}

}
