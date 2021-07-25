package br.com.ifsp.pi.lixt.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.dto.CommentDto;
import br.com.ifsp.pi.lixt.facade.CommentFacade;
import br.com.ifsp.pi.lixt.mapper.CommentMapper;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionUpdateFailedException;
import br.com.ifsp.pi.lixt.utils.security.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de comentário")
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentFacade commentFacade;

	@ApiOperation(value = "Buscar comentário por id")
	@GetMapping("/{id}")
	public CommentDto findById(@PathVariable Long id) {
		return CommentMapper.entityToDto(this.commentFacade.findById(id));
	}
	
	@ApiOperation(value = "Salvar um comentário")
	@PostMapping
	public CommentDto save(@RequestBody(required = false) CommentDto comment) {
		comment.setUserId(Users.getUserId());
		return CommentMapper.entityToDto(this.commentFacade.save(CommentMapper.dtoToEntity(comment)));
	}
	
	@ApiOperation(value = "Atualizar comentário")
	@PutMapping("/{id}")
	public CommentDto update(@RequestBody(required = false) CommentDto comment, @PathVariable Long id) throws PreconditionUpdateFailedException {
		return CommentMapper.entityToDto(this.commentFacade.update(CommentMapper.dtoToEntity(comment), id));
	}
	
	@ApiOperation(value = "Deletar um comentário")
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.commentFacade.deleteById(id);
	}
	
}
