package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.dto.CommentDto;
import br.com.ifsp.pi.lixt.utils.conversion.Operators;

public abstract class CommentDtoInstantior extends Operators {

	private CommentDtoInstantior() {}
	
	public static String createCommentJson(String name, Long productId, Long userId) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder = stringBuilder.append(KEY_OPEN)
				.append(createJsonLine("content", name)).append(COMMA)
				.append(createJsonLine("date")).append(COMMA)				
//				.append(createJsonLine("date", "2021-07-26T23:02:05.021662900")).append(COMMA)
				.append(createJsonLine("id")).append(COMMA)
				.append(createJsonLine("productOfListId", productId)).append(COMMA)
				.append(createJsonLine("user")).append(COMMA)
				.append(createJsonLine("userId", userId))
				.append(KEY_CLOSE);
		
		return stringBuilder.toString();
	}
	
	public static String createCommentJson(CommentDto comment) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder = stringBuilder.append(KEY_OPEN)
				.append(createJsonLine("content", comment.getContent())).append(COMMA)
				.append(createJsonLine("date")).append(COMMA)				
				.append(createJsonLine("id", comment.getId())).append(COMMA)
				.append(createJsonLine("productOfListId", comment.getProductOfListId())).append(COMMA)
				.append(createJsonLine("user")).append(COMMA)
				.append(createJsonLine("userId", comment.getUserId()))
				.append(KEY_CLOSE);
		
		return stringBuilder.toString();
	}
	
}
