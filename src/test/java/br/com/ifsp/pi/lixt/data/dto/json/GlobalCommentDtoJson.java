package br.com.ifsp.pi.lixt.data.dto.json;

import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.instantiator.GlobalCommentDtoInstantior;

import java.util.ArrayList;
import java.util.List;

public abstract class GlobalCommentDtoJson {

	private GlobalCommentDtoJson() {}

	public static List<String> initializeValues(UserDto userDto, ProductOfListDto productOfListDto) {
		List<String> globalComments = new ArrayList<>();

		globalComments.add(GlobalCommentDtoInstantior.createGlobalCommentJson("Comentário global 1", userDto.getId(), productOfListDto.getProductId()));
		globalComments.add(GlobalCommentDtoInstantior.createGlobalCommentJson("Comentário global 2", userDto.getId(), productOfListDto.getProductId()));
		globalComments.add(GlobalCommentDtoInstantior.createGlobalCommentJson("Comentário global 3", userDto.getId(), productOfListDto.getProductId()));

		return globalComments;
	}
}
