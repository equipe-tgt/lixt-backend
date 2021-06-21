package br.com.ifsp.pi.lixt.data.business.category;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public Category save(Category category) {
		return this.categoryRepository.save(category);
	}

}
