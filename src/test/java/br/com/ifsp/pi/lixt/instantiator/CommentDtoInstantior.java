package br.com.ifsp.pi.lixt.instantiator;

public class CommentDtoInstantior {

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
	
}
