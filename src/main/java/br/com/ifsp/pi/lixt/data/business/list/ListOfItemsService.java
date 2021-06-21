package br.com.ifsp.pi.lixt.data.business.list;

import org.springframework.stereotype.Service;

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

}
