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

import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfListService;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.mapper.ProductOfListMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PrecoditionUpdateFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de produto da lista")
@RestController
@RequestMapping("/productOfList")
@RequiredArgsConstructor
public class ProductOfListController {
	
	private final ProductOfListService productOfListService;
	
	@ApiOperation(value = "Buscar produto da lista por id")
	@GetMapping("/{id}")
	public ProductOfListDto findById(@PathVariable Long id) {
		return ProductOfListMapper.entityToDto(this.productOfListService.findById(id));
	}
	
	@ApiOperation(value = "Salvar um produto da lista")
	@PostMapping
	public ProductOfListDto save(@RequestBody(required = false) ProductOfListDto productOfListDto) {
		return ProductOfListMapper.entityToDto(this.productOfListService.save(ProductOfListMapper.dtoToEntity(productOfListDto)));
	}
	
	@ApiOperation(value = "Salvar vários produtos da lista")
	@PostMapping("/save-all")
	public List<ProductOfListDto> saveAll(@RequestBody(required = false) List<ProductOfListDto> productsOfList) {
		return this.productOfListService.saveAll(productsOfList.stream().map(ProductOfListMapper::dtoToEntity).collect(Collectors.toList()))
				.stream().map(ProductOfListMapper::entityToDto).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Atualizar produto da lista")
	@PutMapping("/{id}")
	public ProductOfListDto update(@RequestBody(required = false) ProductOfListDto productOfListDto, @PathVariable Long id) throws PrecoditionUpdateFailedException {
		
		if(!productOfListDto.getId().equals(id))
			throw new PrecoditionUpdateFailedException("Erro ao atualizar produto da lista");
		
		return ProductOfListMapper.entityToDto(this.productOfListService.save(ProductOfListMapper.dtoToEntity(productOfListDto)));
	}
	
	@ApiOperation(value = "Deletar um produto da lista")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.productOfListService.deleteById(id);
	}

}
