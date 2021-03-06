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
	
	public void deleteById(Long id) {
		this.listMembersRepository.deleteById(id);
	}
	
	public ListMembers findByListIdAndUserId(Long listId, Long userId) {
		return this.listMembersRepository.findByListIdAndUserId(listId, userId);
	}
	
	public Long findUserIdByListMembersId(Long listMembersId) {
		return this.listMembersRepository.findUserIdByListMembersId(listMembersId);
	}
	
	public List<ListMembers> findListMembersReceviedByUser(Long userId) {
		return this.listMembersRepository.findListMembersReceviedByUser(userId);
	}

}
