package br.com.ifsp.pi.lixt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.dto.PurchaseDto;
import br.com.ifsp.pi.lixt.facade.PurchaseFacade;
import br.com.ifsp.pi.lixt.mapper.PurchaseMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de compras")
@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {
	
	private final PurchaseFacade purchaseFacade;
	
	@ApiOperation(value = "Buscar compra por id")
	@GetMapping("/{id}")
	public PurchaseDto findById(@PathVariable Long id) {
		return PurchaseMapper.entityToDto(this.purchaseFacade.findById(id));
	}
	
	@ApiOperation(value = "Salvar uma compra")
	@PostMapping
	public PurchaseDto save(@RequestBody(required = false) PurchaseDto purchase) {
		return PurchaseMapper.entityToDto(this.purchaseFacade.save(purchase));
	}
	
	@ApiOperation(value = "Buscar compras realizadas pelo usuário")
	@GetMapping("/by-user")
	public List<PurchaseDto> findUserLists() {
		return this.purchaseFacade.findUserPurchases().stream().map(PurchaseMapper::entityToDto).collect(Collectors.toList());
	}

}
