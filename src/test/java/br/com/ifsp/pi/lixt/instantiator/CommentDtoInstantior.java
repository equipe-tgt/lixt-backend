package br.com.ifsp.pi.lixt.instantiator;

public class CommentDtoInstantior {

	public static String createCommentJson(String name, Long productId, Long userId) {
		
		return 	"{" + 
				"  \"content\": \"" + name +"\"," + 
				"  \"date\": \"2021-07-05T20:39:23.579Z\"," + 
				"  \"id\": null," + 
				"  \"productOfListId\": " + productId  + "," + 
				"  \"user\": null," +
				"  \"userId\": " + userId + 
				"}";
	}
	
}
