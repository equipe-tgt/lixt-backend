package br.com.ifsp.pi.lixt.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembersService;
import br.com.ifsp.pi.lixt.dto.ListMembersDto;
import br.com.ifsp.pi.lixt.mapper.ListMembersMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PrecoditionUpdateFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de membros da lista")
@RestController
@RequestMapping("/membersList")
@RequiredArgsConstructor
public class ListMembersController {
	
	private final ListMembersService listMembersService;
	
	@ApiOperation(value = "Buscar membro da lista por id")
	@GetMapping("/{id}")
	public ListMembersDto findById(@PathVariable Long id) {
		return ListMembersMapper.entityToDto(this.listMembersService.findById(id));
	}
	
	@ApiOperation(value = "Salvar um membro da lista")
	@PostMapping
	public ListMembersDto save(@RequestBody(required = false) ListMembersDto listMembers) {
		return ListMembersMapper.entityToDto(this.listMembersService.save(ListMembersMapper.dtoToEntity(listMembers)));
	}
	
	@ApiOperation(value = "Atualizar membro da lista")
	@PutMapping("/{id}")
	public ListMembersDto update(@RequestBody(required = false) ListMembersDto listMembers, @PathVariable Long id) throws PrecoditionUpdateFailedException {
		
		if(!listMembers.getId().equals(id))
			throw new PrecoditionUpdateFailedException("Erro ao atualizar lista");
		
		return ListMembersMapper.entityToDto(this.listMembersService.save(ListMembersMapper.dtoToEntity(listMembers)));
	}
	
	@ApiOperation(value = "Deletar uma membro da lista")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.listMembersService.deleteById(id);
	}

}
