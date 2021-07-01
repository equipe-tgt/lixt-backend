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

import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.mapper.ListOfItemsMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PrecoditionUpdateFailedException;
import br.com.ifsp.pi.lixt.utils.security.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de lista")
@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
public class ListOfItemsController {
	
	private final ListOfItemsService listOfItemsService;
	
	@ApiOperation(value = "Buscar lista por id")
	@GetMapping("/{id}")
	public ListOfItemsDto findById(@PathVariable Long id) {
		return ListOfItemsMapper.entityToDto(this.listOfItemsService.findById(id));
	}
	
	@ApiOperation(value = "Salvar uma lista")
	@PostMapping
	public ListOfItemsDto save(@RequestBody(required = false) ListOfItemsDto list) {
		list.setOwnerId(Users.getUserId());
		return ListOfItemsMapper.entityToDto(this.listOfItemsService.save(ListOfItemsMapper.dtoToEntity(list)));
	}
	
	@ApiOperation(value = "Atualizar lista")
	@PutMapping("/{id}")
	public ListOfItemsDto update(@RequestBody(required = false) ListOfItemsDto list, @PathVariable Long id) throws PrecoditionUpdateFailedException {
		
		if(!list.getId().equals(id))
			throw new PrecoditionUpdateFailedException("Erro ao atualizar lista");
		
		return ListOfItemsMapper.entityToDto(this.listOfItemsService.save(ListOfItemsMapper.dtoToEntity(list)));
	}
	
	@ApiOperation(value = "Deletar uma lista")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.listOfItemsService.deleteById(id);
	}
	
	@ApiOperation(value = "Buscar listas no qual o usuário possui acesso")
	@GetMapping("/by-user")
	public List<ListOfItemsDto> findUserLists() {
		return this.listOfItemsService.findUserLists(Users.getUserId())
				.stream().map(ListOfItemsMapper::entityToDto).collect(Collectors.toList());
	}

}
