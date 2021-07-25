package br.com.ifsp.pi.lixt.data.business.list;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembers;
import br.com.ifsp.pi.lixt.data.enumeration.StatusListMember;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListOfItemsService {
	
	private final ListOfItemsRepository listOfItemsRepository;
	
	public ListOfItems findById(Long id) {
		return this.listOfItemsRepository.findById(id).orElse(null);
	}
	
	public ListOfItems save(ListOfItems list) {
		return this.listOfItemsRepository.save(list);
	}
	
	public void deleteById(Long id) {
		this.listOfItemsRepository.deleteById(id);
	}
	
	
	public List<ListOfItems> findUserLists(Long idUser) {
		return this.listOfItemsRepository.findUserLists(idUser, StatusListMember.ACCEPT);
	}
	
	public Long findOwnerIdByListId(Long listId) {
		return this.listOfItemsRepository.findOwnerIdByListId(listId);
	}
	
	public List<Long> findMembersIdsByListId(Long listId) {
		return this.listOfItemsRepository.findMembersIdsByListId(listId, StatusListMember.ACCEPT);
	}
	
	public List<ListMembers> findListMembersSentByUser(Long userId) {
		return this.listOfItemsRepository.findListMembersSentByUser(userId);
	}
	
	
	public Long findOwnerIdByListMemberId(Long listMembersId) {
		return this.listOfItemsRepository.findOwnerIdByListMemberId(listMembersId);
	}
	
	
	public Long findOwnerIdByCommentId(Long commentId) {
		return this.listOfItemsRepository.findOwnerIdByCommentId(commentId);
	}
	
	public List<Long> findMembersIdsByCommentId(Long commentId) {
		return this.listOfItemsRepository.findMembersIdsByCommentId(commentId, StatusListMember.ACCEPT);
	}
	
	
	public Long findOwnerIdByProductOfListId(Long productOfListId) {
		return this.listOfItemsRepository.findOwnerIdByProductOfListId(productOfListId);
	}
	
	public List<Long> findMembersIdsByProductOfListId(Long productOfListId) {
		return this.listOfItemsRepository.findMembersIdsByProductOfListId(productOfListId, StatusListMember.ACCEPT);
	}

}
