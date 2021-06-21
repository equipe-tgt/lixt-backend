package br.com.ifsp.pi.lixt.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;
import br.com.ifsp.pi.lixt.mapper.ListOfItemsMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
public class ListOfItemsController {
	
	private final ListOfItemsService listOfItemsService;
	
	@GetMapping("/{id}")
	public ListOfItemsDto findById(@PathVariable Long id) {
		return ListOfItemsMapper.entityToDto(this.listOfItemsService.findById(id));
	}
	
	@PostMapping
	public ListOfItemsDto save(@RequestBody(required = false) ListOfItemsDto list) {
		return ListOfItemsMapper.entityToDto(this.listOfItemsService.save(ListOfItemsMapper.dtoToEntity(list)));
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.listOfItemsService.deleteById(id);
	}

}
