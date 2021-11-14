package br.com.ifsp.pi.lixt.data.dto.json;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.instantiator.CommentDtoInstantiator;

public abstract class CommentDtoDataJson {

	private CommentDtoDataJson() {}

	public static List<String> initializeValues(UserDto user, ProductOfListDto productOfList) {
		List<String> comments = new ArrayList<>();

		comments.add(CommentDtoInstantiator.createCommentJson("Comentário 1", productOfList.getId(), user.getId()));
		comments.add(CommentDtoInstantiator.createCommentJson("Comentário 2", productOfList.getId(), user.getId()));
		comments.add(CommentDtoInstantiator.createCommentJson("Comentário 3", productOfList.getId(), user.getId()));

		return comments;
	}


}
