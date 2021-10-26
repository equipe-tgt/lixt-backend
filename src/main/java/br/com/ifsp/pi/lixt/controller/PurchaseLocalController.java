package br.com.ifsp.pi.lixt.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.ifsp.pi.lixt.data.business.purchaselocal.PurchaseLocal;
import org.locationtech.jts.io.ParseException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.data.business.purchaselocal.PurchaseLocalService;
import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;
import br.com.ifsp.pi.lixt.mapper.PurchaseLocalMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
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
	public PurchaseLocalDto findById(@PathVariable Long id) throws PreconditionFailedException {
		return PurchaseLocalMapper.entityToDto(this.purchaseLocalService.findById(id));
	}
	
	@ApiOperation(value = "Salvar um local de compra")
	@PostMapping
	public PurchaseLocalDto save(@RequestBody(required = false) PurchaseLocalDto purchaseLocal) throws PreconditionFailedException {
		return PurchaseLocalMapper.entityToDto(this.purchaseLocalService.save(PurchaseLocalMapper.dtoToEntity(purchaseLocal)));
	}
	
	@ApiOperation(value = "Atualizar o nome do local de compra")
	@PutMapping("/{id}")
	public Integer update(@RequestBody(required = false) PurchaseLocalDto purchaseLocal, @PathVariable Long id) throws PreconditionFailedException {
		
		if(!purchaseLocal.getId().equals(id))
			throw new PreconditionFailedException("Erro ao atualizar local de compra");
		
		return this.purchaseLocalService.updateNamePurchaseLocal(id, purchaseLocal.getName());
	}
	
	@ApiOperation(value = "Deletar um local de compra")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) throws PreconditionFailedException {
		this.purchaseLocalService.deleteById(id);
	}
	
	@ApiOperation(value = "Encontrar locais de compra a 10 metros de proximidade")
	@PostMapping("/find-near")
	public List<PurchaseLocalDto> findPurchasesLocalNear(@RequestBody(required = false) PurchaseLocalDto purchaseLocal) throws ParseException, PreconditionFailedException {
		return this.purchaseLocalService.findPurchasesLocalNear(PurchaseLocalMapper.dtoToEntity(purchaseLocal))
				.stream().map(PurchaseLocalMapper::entityToDto).collect(Collectors.toList());
	}

	@ApiOperation(value = "Encontrar o histórico de locais de compra do usuário")
	@GetMapping("/all-purchase-local-data/{userId}")
	public Map<String, List<PurchaseLocal>> getAllPuchaseLocalData(@PathVariable Long userId) {
		return this.purchaseLocalService.findAllPurchaseLocalRecords(userId);
	}
}
