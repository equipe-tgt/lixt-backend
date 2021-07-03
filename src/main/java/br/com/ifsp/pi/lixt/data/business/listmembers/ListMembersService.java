package br.com.ifsp.pi.lixt.data.business.listmembers;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListMembersService {
	
	private final ListMembersRepository listMembersRepository;
	
	public ListMembers findById(Long id) {
		return this.listMembersRepository.findById(id).orElse(null);
	}
	
	public ListMembers save(ListMembers listMember) {
		return this.listMembersRepository.save(listMember);
	}
	
	public List<ListMembers> saveAll(List<ListMembers> listMembers) {
		return (List<ListMembers>) this.listMembersRepository.saveAll(listMembers);
	}
	
	public void deleteById(Long id) {
		this.listMembersRepository.deleteById(id);
	}
	
	public Long findUserIdByListMembersId(Long listMembersId) {
		return this.listMembersRepository.findUserIdByListMembersId(listMembersId);
	}

}
