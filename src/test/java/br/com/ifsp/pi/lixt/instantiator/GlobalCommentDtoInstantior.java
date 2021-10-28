package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.dto.GlobalCommentDto;
import br.com.ifsp.pi.lixt.utils.conversion.Operators;

public abstract class GlobalCommentDtoInstantior extends Operators {

	private GlobalCommentDtoInstantior() {}

	public static String createGlobalCommentJson(String content, Long userId, Long productId, Boolean isPublic) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(KEY_OPEN)
				.append(createJsonLine("id")).append(COMMA)
				.append(createJsonLine("content", content)).append(COMMA)
				.append(createJsonLine("userId", userId)).append(COMMA)
				.append(createJsonLine("productId", productId)).append(COMMA)
				.append(createJsonLine("isPublic", isPublic))
				.append(KEY_CLOSE);

		return stringBuilder.toString();
	}

	public static String createGlobalCommentJson(GlobalCommentDto globalCommentDto) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(KEY_OPEN)
				.append(createJsonLine("id", globalCommentDto.getId())).append(COMMA)
				.append(createJsonLine("content", globalCommentDto.getContent())).append(COMMA)
				.append(createJsonLine("userId", globalCommentDto.getUserId())).append(COMMA)
				.append(createJsonLine("productId", globalCommentDto.getProductId())).append(COMMA)
				.append(createJsonLine("isPublic", globalCommentDto.getIsPublic()))
				.append(KEY_CLOSE);

		return stringBuilder.toString();
	}
}
