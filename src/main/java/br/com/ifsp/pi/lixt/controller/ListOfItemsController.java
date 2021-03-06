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

import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.facade.ListOfItemsFacade;
import br.com.ifsp.pi.lixt.mapper.ListOfItemsMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de lista")
@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
public class ListOfItemsController {
	
	private final ListOfItemsFacade listOfItemsFacade;
	
	@ApiOperation(value = "Buscar lista por id")
	@GetMapping("/{id}")
	public ListOfItemsDto findById(@PathVariable Long id) {
		return ListOfItemsMapper.entityToDto(this.listOfItemsFacade.findById(id));
	}
	
	@ApiOperation(value = "Salvar uma lista")
	@PostMapping
	public ListOfItemsDto save(@RequestBody(required = false) ListOfItemsDto list) {
		return ListOfItemsMapper.entityToDto(this.listOfItemsFacade.save(ListOfItemsMapper.dtoToEntity(list)));
	}
	
	@ApiOperation(value = "Atualizar lista")
	@PutMapping("/{id}")
	public ListOfItemsDto update(@RequestBody(required = false) ListOfItemsDto list, @PathVariable Long id) throws PreconditionFailedException {
		return ListOfItemsMapper.entityToDto(this.listOfItemsFacade.update(ListOfItemsMapper.dtoToEntity(list), id));
	}
	
	@ApiOperation(value = "Deletar uma lista")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.listOfItemsFacade.deleteById(id);
	}
	
	@ApiOperation(value = "Buscar listas no qual o usu??rio possui acesso")
	@GetMapping("/by-user")
	public List<ListOfItemsDto> findUserLists() {
		return this.listOfItemsFacade.findUserLists().stream().map(ListOfItemsMapper::entityToDto).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Limpar itens marcados na lista")
	@GetMapping("/{id}/clean-marked-itens")
	public Integer cleanUserIdAtProductsOfList(@PathVariable Long id) {
		return this.listOfItemsFacade.cleanUserIdAtProductsOfList(id);
	}

}
