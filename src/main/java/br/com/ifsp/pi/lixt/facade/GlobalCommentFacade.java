package br.com.ifsp.pi.lixt.facade;

import br.com.ifsp.pi.lixt.data.business.comment.Comment;
import br.com.ifsp.pi.lixt.data.business.globalComment.GlobalComment;
import br.com.ifsp.pi.lixt.data.business.globalComment.GlobalCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalCommentFacade {

    private final GlobalCommentService globalCommentService;

    public List<GlobalComment> findAll() {
        return this.globalCommentService.findAll();
    }

    public GlobalComment findById(Long id) {
        return this.globalCommentService.findById(id);
    }

    public GlobalComment save(GlobalComment globalComment) {
        return this.globalCommentService.save(globalComment);
    }

    public GlobalComment update(GlobalComment globalComment) {
        return this.globalCommentService.save(globalComment);
    }

    public void deleteById(Long id) {
        this.globalCommentService.deleteById(id);
    }

    public List<GlobalComment> findAllByUserId(Long userId) {
        return this.globalCommentService.findByUserId(userId);
    }
}
