package br.com.ifsp.pi.lixt.data.business.comment;

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
	
	public void deleteById(Long id) {
		this.commentRepository.deleteById(id);
	}
	
	public Long findUserIdByCommentId(Long commentId) {
		return this.commentRepository.findUserIdByCommentId(commentId);
	}

}
