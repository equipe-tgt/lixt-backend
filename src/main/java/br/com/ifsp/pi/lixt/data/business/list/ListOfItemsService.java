package br.com.ifsp.pi.lixt.data.business.list;

import java.util.List;

import org.springframework.stereotype.Service;

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
	
	public List<ListOfItems> saveAll(List<ListOfItems> lists) {
		return (List<ListOfItems>) this.listOfItemsRepository.saveAll(lists);
	}
	
	public void deleteById(Long id) {
		this.listOfItemsRepository.deleteById(id);
	}
	
	public List<ListOfItems> findUserLists(Long idUser) {
		return this.listOfItemsRepository.findUserLists(idUser, StatusListMember.ACCEPT);
	}

}
