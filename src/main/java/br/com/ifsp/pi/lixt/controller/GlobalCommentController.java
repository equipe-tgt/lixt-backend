package br.com.ifsp.pi.lixt.controller;

import br.com.ifsp.pi.lixt.dto.GlobalCommentDto;
import br.com.ifsp.pi.lixt.facade.GlobalCommentFacade;
import br.com.ifsp.pi.lixt.mapper.GlobalCommentMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(value = "Controller de comentários globais")
@RestController
@RequestMapping("/globalComment")
@RequiredArgsConstructor
public class GlobalCommentController {

	private final GlobalCommentFacade globalCommentFacade;
	
	@ApiOperation(value = "Buscar comentário global por id")
	@GetMapping("/{id}")
	public GlobalCommentDto findById(@PathVariable Long id) {
		return GlobalCommentMapper.entityToDto(this.globalCommentFacade.findById(id));
	}

	@ApiOperation(value = "Salvar um comentário global")
	@PostMapping
	public GlobalCommentDto save(@RequestBody(required=false) GlobalCommentDto globalCommentDto) {
		return GlobalCommentMapper.entityToDto(this.globalCommentFacade.save(GlobalCommentMapper.dtoToEntity(globalCommentDto)));
	}

	@ApiOperation(value = "Atualizar comentário global")
	@PutMapping
	public GlobalCommentDto update(@RequestBody(required = false) GlobalCommentDto globalCommentDto) {
		return GlobalCommentMapper.entityToDto(this.globalCommentFacade.update(GlobalCommentMapper.dtoToEntity(globalCommentDto)));
	}

	@ApiOperation(value = "Deletar comentário global")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.globalCommentFacade.deleteById(id);
	}
}
