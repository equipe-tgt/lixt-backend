package br.com.ifsp.pi.lixt.data.business.globalcomment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalCommentService {

	private final GlobalCommentRepository globalCommentRepository;

	public GlobalComment findById(Long id) {
		return this.globalCommentRepository.findById(id).orElse(null);
	}

	public GlobalComment save(GlobalComment globalComment) {
		return this.globalCommentRepository.save(globalComment);
	}

	public void deleteById(Long id) {
		this.globalCommentRepository.deleteById(id);
	}

	public List<GlobalComment> findByProductId(Long productId) {
		return this.globalCommentRepository.findGlobalCommentsByProductId(productId);
	}

	public List<GlobalComment> findAll() {
		return (List<GlobalComment>) this.globalCommentRepository.findAll();
	}
	
	public Integer countGlobalCommentsAtProductByUser(Long productId, Long userId) {
		return this.globalCommentRepository.countGlobalCommentsAtProductByUser(productId, userId);
	}
}
