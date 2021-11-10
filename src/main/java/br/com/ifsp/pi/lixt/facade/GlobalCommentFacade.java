package br.com.ifsp.pi.lixt.facade;

import java.time.LocalDateTime;

import br.com.ifsp.pi.lixt.data.business.globalcomment.GlobalComment;
import br.com.ifsp.pi.lixt.data.business.globalcomment.GlobalCommentService;
import br.com.ifsp.pi.lixt.data.business.user.User;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.utils.exceptions.ForbiddenException;
import br.com.ifsp.pi.lixt.utils.security.Users;
import br.com.ifsp.pi.lixt.utils.security.oauth.function.ValidatorAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlobalCommentFacade {

	private final GlobalCommentService globalCommentService;
	private final UserService userService;

	public List<GlobalComment> findAll() {
		return this.globalCommentService.findAll();
	}

	public GlobalComment findById(Long globalCommentId) {
		return this.globalCommentService.findById(globalCommentId);
	}

	public GlobalComment save(GlobalComment globalComment) {
		globalComment.setDate(LocalDateTime.now());
		globalComment.setUserId(Users.getUserId());

		User user = this.userService.findById(Users.getUserId());
		globalComment.setUser(user);
		return this.globalCommentService.save(globalComment);
	}

	public GlobalComment update(GlobalComment globalComment) {
		if(!ValidatorAccess.canAcces(globalComment.getUserId()))
			throw new ForbiddenException();

		return this.globalCommentService.save(globalComment);
	}

	public void deleteById(Long id) {
		this.globalCommentService.deleteById(id);
	}

}
