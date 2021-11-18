package br.com.ifsp.pi.lixt.data.business.productoflist;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.comment.Comment;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductOfListService {
	
	private final ProductOfListRepository productOfListRepository;
	
	public ProductOfList findById(Long id) {
		return this.productOfListRepository.findById(id).orElse(null);
	}
	
	public ProductOfList save(ProductOfList productOfLists) {
		return this.productOfListRepository.save(productOfLists);
	}
	
	public void deleteById(Long id) {
		this.productOfListRepository.deleteById(id);
	}
	
	public List<Comment> findCommentsByProductOfListId(Long id) {
		return this.productOfListRepository.findCommentsByProductOfListId(id);
	}
	
	public Integer cleanProductOfList(Long productOfListId) {
		return this.productOfListRepository.cleanProductOfList(productOfListId);
	}
	
	public Integer cleanUserIdAtProductsOfList(Long listId) {
		return this.productOfListRepository.cleanUserIdAtProductsOfList(listId);
	}
	
	public Integer markProduct(Long userId, Long productId) {
		return this.productOfListRepository.markProduct(userId, productId);
	}
	
	public Integer assignedItemToUser(Long userId, Long productOfListId) {
		return this.productOfListRepository.assignedItemToUser(userId, productOfListId);
	}
	
	public Integer assignedItemToUser(Long userId, boolean isMarked, Long productOfListId) {
		return this.productOfListRepository.assignedItemToUser(userId, isMarked, productOfListId);
	}

	public Integer updateMarkedAmount(Integer markedAmount, Long productOfListId) {
		return this.productOfListRepository.updateMarkedAmount(markedAmount, productOfListId);
	}

}
