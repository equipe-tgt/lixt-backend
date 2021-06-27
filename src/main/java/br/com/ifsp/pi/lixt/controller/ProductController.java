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

import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PrecoditionUpdateFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de produto")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@ApiOperation(value = "Buscar produto por id")
	@GetMapping("/{id}")
	public ProductDto findById(@PathVariable Long id) {
		return ProductMapper.entityToDto(this.productService.findById(id));
	}
	
	@ApiOperation(value = "Salvar um produto")
	@PostMapping
	public ProductDto save(@RequestBody(required = false) ProductDto product) {
		return ProductMapper.entityToDto(this.productService.save(ProductMapper.dtoToEntity(product)));
	}
	
	@ApiOperation(value = "Salvar vários produtos")
	@PostMapping("/save-all")
	public List<ProductDto> saveAll(@RequestBody(required = false) List<ProductDto> products) {
		return this.productService.saveAll(products.stream().map(ProductMapper::dtoToEntity).collect(Collectors.toList()))
				.stream().map(ProductMapper::entityToDto).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Atualizar produto")
	@PutMapping("/{id}")
	public ProductDto update(@RequestBody(required = false) ProductDto product, @PathVariable Long id) throws PrecoditionUpdateFailedException {
		
		if(!product.getId().equals(id))
			throw new PrecoditionUpdateFailedException("Erro ao atualizar produto");
		
		return ProductMapper.entityToDto(this.productService.save(ProductMapper.dtoToEntity(product)));
	}
	
	@ApiOperation(value = "Deletar produto por id")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.productService.deleteById(id);
	}
	
	@ApiOperation(value = "Encontrar produtos a partir de possibilidades de nomes")
	@GetMapping("/by-name/{name}")
	public List<ProductDto> findByName(@PathVariable String name) {
		return this.productService.findByName(name).stream().map(ProductMapper::entityToDto).collect(Collectors.toList());
	}

}
