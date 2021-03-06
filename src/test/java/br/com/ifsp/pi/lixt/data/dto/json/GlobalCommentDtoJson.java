package br.com.ifsp.pi.lixt.data.dto.json;

import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.instantiator.GlobalCommentDtoInstantiator;

import java.util.ArrayList;
import java.util.List;

public abstract class GlobalCommentDtoJson {

	private GlobalCommentDtoJson() {}

	public static List<String> initializeValues(UserDto userDto, ProductDto productDto) {
		List<String> globalComments = new ArrayList<>();

		globalComments.add(GlobalCommentDtoInstantiator.createGlobalCommentJson("Comentário global 1", userDto.getId(), productDto.getId(), Boolean.TRUE));
		globalComments.add(GlobalCommentDtoInstantiator.createGlobalCommentJson("Comentário global 2", userDto.getId(), productDto.getId(), Boolean.TRUE));
		globalComments.add(GlobalCommentDtoInstantiator.createGlobalCommentJson("Comentário global 3", userDto.getId(), productDto.getId(), Boolean.FALSE));

		return globalComments;
	}

	public static List<String> initializeValues(UserDto userDto, ProductOfListDto productOfListDto) {
		List<String> globalComments = new ArrayList<>();

		globalComments.add(GlobalCommentDtoInstantiator.createGlobalCommentJson("Comentário global 1", userDto.getId(), productOfListDto.getProductId(), Boolean.TRUE));
		globalComments.add(GlobalCommentDtoInstantiator.createGlobalCommentJson("Comentário global 2", userDto.getId(), productOfListDto.getProductId(), Boolean.TRUE));
		globalComments.add(GlobalCommentDtoInstantiator.createGlobalCommentJson("Comentário global 3", userDto.getId(), productOfListDto.getProductId(), Boolean.FALSE));

		return globalComments;
	}
}
