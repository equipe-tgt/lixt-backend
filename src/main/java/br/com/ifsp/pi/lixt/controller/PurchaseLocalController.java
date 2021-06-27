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

import br.com.ifsp.pi.lixt.data.business.purchaseLocal.PurchaseLocalService;
import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;
import br.com.ifsp.pi.lixt.mapper.PurchaseLocalMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de locais de compra")
@RestController
@RequestMapping("/purchaseLocal")
@RequiredArgsConstructor
public class PurchaseLocalController {
	
	private final PurchaseLocalService purchaseLocalService;

	@ApiOperation(value = "Buscar local de compra por id")
	@GetMapping("/{id}")
	public PurchaseLocalDto findById(@PathVariable Long id) {
		return PurchaseLocalMapper.entityToDto(this.purchaseLocalService.findById(id));
	}
	
	@ApiOperation(value = "Salvar um local de compra")
	@PostMapping
	public PurchaseLocalDto save(@RequestBody(required = false) PurchaseLocalDto purchaseLocal) {
		return PurchaseLocalMapper.entityToDto(this.purchaseLocalService.save(PurchaseLocalMapper.dtoToEntity(purchaseLocal)));
	}
	
	@ApiOperation(value = "Salvar vários locais de compra")
	@PostMapping("/save-all")
	public List<PurchaseLocalDto> saveAll(@RequestBody(required = false) List<PurchaseLocalDto> purchasesLocal) {
		return this.purchaseLocalService.saveAll(purchasesLocal.stream().map(PurchaseLocalMapper::dtoToEntity).collect(Collectors.toList()))
				.stream().map(PurchaseLocalMapper::entityToDto).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Deletar um local de compra")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.purchaseLocalService.deleteById(id);
	}
	
}
