package br.com.ifsp.pi.lixt.data.business.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	public Comment findById(Long id) {
		return this.commentRepository.findById(id).orElse(null);
	}
	
	public Comment save(Comment comment) {
		return this.commentRepository.save(comment);
	}
	
	public List<Comment> saveAll(List<Comment> comments) {
		return (List<Comment>) this.commentRepository.saveAll(comments);
	}
	
	public void deleteById(Long id) {
		this.commentRepository.deleteById(id);
	}

}
