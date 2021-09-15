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

import br.com.ifsp.pi.lixt.dto.CommentDto;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.facade.ProductOfListFacade;
import br.com.ifsp.pi.lixt.mapper.CommentMapper;
import br.com.ifsp.pi.lixt.mapper.ProductOfListMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de produto da lista")
@RestController
@RequestMapping("/productOfList")
@RequiredArgsConstructor
public class ProductOfListController {
	
	private final ProductOfListFacade productOfListFacade;
	
	@ApiOperation(value = "Buscar produto da lista por id")
	@GetMapping("/{id}")
	public ProductOfListDto findById(@PathVariable Long id) {
		return ProductOfListMapper.entityToDto(this.productOfListFacade.findById(id));
	}
	
	@ApiOperation(value = "Salvar um produto da lista")
	@PostMapping
	public ProductOfListDto save(@RequestBody(required = false) ProductOfListDto productOfList) {
		return ProductOfListMapper.entityToDto(this.productOfListFacade.save(ProductOfListMapper.dtoToEntity(productOfList)));
	}
	
	@ApiOperation(value = "Atualizar produto da lista")
	@PutMapping("/{id}")
	public ProductOfListDto update(@RequestBody(required = false) ProductOfListDto productOfList, @PathVariable Long id) throws PreconditionFailedException {
		return ProductOfListMapper.entityToDto(this.productOfListFacade.update(ProductOfListMapper.dtoToEntity(productOfList), id));
	}
	
	@ApiOperation(value = "Deletar um produto da lista")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.productOfListFacade.deleteById(id);
	}
	
	@ApiOperation(value = "Buscar comentários pertencentes ao produto da lista por id")
	@GetMapping("/{id}/comments")
	public List<CommentDto> findCommentsByProductOfListId(@PathVariable Long id) {
		return this.productOfListFacade.findCommentsByProductOfListId(id).stream().map(CommentMapper::entityToDto).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Marcar produto da lista")
	@GetMapping("/mark/{productOfListId}")
	public Integer markProduct(@PathVariable Long productOfListId) {
		return this.productOfListFacade.markProduct(productOfListId);
	}
	
	@ApiOperation(value = "Limpar dados de compra no produto da lista")
	@GetMapping("/clean/{productOfListId}")
	public Integer cleanProductOfList(@PathVariable Long productOfListId) {
		return this.productOfListFacade.cleanProductOfList(productOfListId);
	}
	
	@ApiOperation(value = "Atribuir ao usuário solicitante o item a ser comprado, retornando se foi aprovado essa atribuição ou não")
	@GetMapping("/{id}/assigned-to-me")
	public Integer assignedItemToMe(@PathVariable Long id) {
		return this.productOfListFacade.assignedItemToMe(id);
	}
	
	@ApiOperation(value = "Atribuir ao usuário informado o item a ser comprado")
	@GetMapping("/{productOfListId}/assigned-to/{userId}")
	public Integer assignedItemToUser(@PathVariable Long productOfListId, @PathVariable Long userId) {
		return this.productOfListFacade.assignedItemToUser(userId, productOfListId);
	}

	@ApiOperation(value = "Atualizar o valor do total de produtos marcados")
	@PutMapping("/{id}/mark-amount/{amount}")
	public Integer updateMarkedAmount(@PathVariable Integer amount, @PathVariable Long id) {
		return this.productOfListFacade.updateMarkedAmount(amount, id);
	}

}
