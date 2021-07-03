package br.com.ifsp.pi.lixt.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifsp.pi.lixt.dto.ListMembersDto;
import br.com.ifsp.pi.lixt.facade.ListMembersFacade;
import br.com.ifsp.pi.lixt.mapper.ListMembersMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(value = "Controller de membros da lista")
@RestController
@RequestMapping("/membersList")
@RequiredArgsConstructor
public class ListMembersController {
	
	private final ListMembersFacade listMembersFacade;
	
	@ApiOperation(value = "Enviar um convite para ser membro da lista", consumes = MediaType.TEXT_PLAIN_VALUE)
	@PostMapping("/send-invite/{listId}")
	public ListMembersDto sendInvite(@PathVariable Long listId, @RequestBody String username) {
		return ListMembersMapper.entityToDto(this.listMembersFacade.sendInvite(listId, username));
	}
	
	@ApiOperation(value = "Aceitar um convite para ser membro da lista")
	@GetMapping("/accept-invite/{listMembersId}")
	public ListMembersDto acceptInvite(@PathVariable Long listMembersId) throws RuntimeException {
		return ListMembersMapper.entityToDto(this.listMembersFacade.acceptInvite(listMembersId));
	}
	
	@ApiOperation(value = "Recusar um convite para ser membro da lista")
	@GetMapping("/reject-invite/{listMembersId}")
	public ListMembersDto rejectInvite(@PathVariable Long listMembersId) throws RuntimeException {
		return ListMembersMapper.entityToDto(this.listMembersFacade.rejectInvite(listMembersId));
	}
	
	@ApiOperation(value = "Deletar uma membro da lista")
	@DeleteMapping("/{listMembersId}")
	public void removeUserAtList(@PathVariable Long listMembersId) throws RuntimeException {
		this.listMembersFacade.removeUserAtList(listMembersId);
	}

}
