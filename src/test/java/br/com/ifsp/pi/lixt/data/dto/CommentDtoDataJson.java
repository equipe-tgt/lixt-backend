package br.com.ifsp.pi.lixt.data.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.dto.UserDto;

public class CommentDtoDataJson {

	private static List<String> comments = new ArrayList<>();
	
	public static List<String> initializeValues(UserDto user, ProductOfListDto productOfList) {

		comments.add(
				"{" + 
				"  \"content\": \"Comentário 1\"," + 
				"  \"id\": null," + 
				"  \"productOfListId\": " + productOfList.getId() + "," + 
				"  \"user\": null," + 
				"  \"userId\": " + user.getId() + 
				"}"
		);
		
		comments.add(
				"{" + 
				"  \"content\": \"Comentário 2\"," + 
				"  \"id\": null," + 
				"  \"productOfListId\": " + productOfList.getId() + "," + 
				"  \"user\": null," + 
				"  \"userId\": " + user.getId() + 
				"}"
		);
		
		comments.add(
				"{" + 
				"  \"content\": \"Comentário 3\"," + 
				"  \"date\": \"2021-07-05T20:39:23.579Z\"," + 
				"  \"id\": null," + 
				"  \"productOfListId\": " + productOfList.getId()  + "," + 
				"  \"user\": null," + 
				"  \"userId\": " + user.getId() + 
				"}"
		);
		
		return comments;
	}
}
