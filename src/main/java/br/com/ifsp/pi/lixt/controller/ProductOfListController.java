package br.com.ifsp.pi.lixt.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.data.business.productOfList.ProductOfListService;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.mapper.ProductOfListMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productOfList")
@RequiredArgsConstructor
public class ProductOfListController {
	
	private final ProductOfListService productOfListService;
	
	@GetMapping("/{id}")
	public ProductOfListDto findById(@PathVariable Long id) {
		return ProductOfListMapper.entityToDto(this.productOfListService.findById(id));
	}
	
	@PostMapping
	public ProductOfListDto save(@RequestBody(required = false) ProductOfListDto productOfListDto) {
		return ProductOfListMapper.entityToDto(this.productOfListService.save(ProductOfListMapper.dtoToEntity(productOfListDto)));
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.productOfListService.deleteById(id);
	}

}
