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

import br.com.ifsp.pi.lixt.data.business.product.ProductService;
import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping("/{id}")
	public ProductDto findById(@PathVariable Long id) {
		return ProductMapper.entityToDto(this.productService.findById(id));
	}
	
	@PostMapping
	public ProductDto save(@RequestBody(required = false) ProductDto product) {
		return ProductMapper.entityToDto(this.productService.save(ProductMapper.dtoToEntity(product)));
	}
	
	@PostMapping("/save-all")
	public List<ProductDto> saveAll(@RequestBody(required = false) List<ProductDto> products) {
		return this.productService.saveAll(products.stream().map(ProductMapper::dtoToEntity).collect(Collectors.toList()))
				.stream().map(ProductMapper::entityToDto).collect(Collectors.toList());
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.productService.deleteById(id);
	}
	
	@GetMapping("/by-name/{name}")
	public List<ProductDto> findByName(@PathVariable String name) {
		return this.productService.findByName(name).stream().map(ProductMapper::entityToDto).collect(Collectors.toList());
	}

}
