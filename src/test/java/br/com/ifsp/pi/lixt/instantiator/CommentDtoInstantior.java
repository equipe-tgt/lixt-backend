package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.dto.CommentDto;

public abstract class CommentDtoInstantior {

	private CommentDtoInstantior() {}
	
	public static String createCommentJson(String name, Long productId, Long userId) {
		
		return 	"{" + 
				"  \"content\": \"" + name +"\"," + 
				"  \"date\": null," + 
			//	"  \"date\": \"2021-07-26T23:02:05.021662900\"," + 
				"  \"id\": null," + 
				"  \"productOfListId\": " + productId  + "," + 
				"  \"user\": null," +
				"  \"userId\": " + userId + 
				"}";
	}
	
	public static String createCommentJson(CommentDto comment) {
		
		return 	"{" + 
				"  \"content\": \"" + comment.getContent() +"\"," + 
				"  \"date\": \"" + comment.getDate() +"\"," + 
				"  \"id\": \"" + comment.getId() +"\"," + 
				"  \"productOfListId\": " + comment.getProductOfListId()  + "," + 
				"  \"user\": null," +
				"  \"userId\": " + comment.getUserId() + 
				"}";
	}
	
}
