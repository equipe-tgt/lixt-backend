package br.com.ifsp.pi.lixt.data.business.category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public Category findById(Long id) {
		return this.categoryRepository.findById(id).orElse(null);
	}
	
	public Category save(Category category) {
		return this.categoryRepository.save(category);
	}
	
	public List<Category> saveAll(List<Category> categories) {
		return (List<Category>) this.categoryRepository.saveAll(categories);
	}
	
	public void deleteById(Long id) {
		this.categoryRepository.deleteById(id);
	}

}
