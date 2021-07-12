package br.com.ifsp.pi.lixt.data.dto.json;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.instantiator.CommentDtoInstantior;

public class CommentDtoDataJson {

	private static List<String> comments = new ArrayList<>();
	
	public static List<String> initializeValues(UserDto user, ProductOfListDto productOfList) {

		comments.add(CommentDtoInstantior.createCommentJson("Comentário 1", productOfList.getId(), user.getId()));
		comments.add(CommentDtoInstantior.createCommentJson("Comentário 2", productOfList.getId(), user.getId()));
		comments.add(CommentDtoInstantior.createCommentJson("Comentário 3", productOfList.getId(), user.getId()));
		
		return comments;
	}
	

}
