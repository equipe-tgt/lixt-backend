package br.com.ifsp.pi.lixt.utils.tests.requests;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class ResquestBuilder {
	
	private ResquestBuilder() {}
	
	public static RequestBuilder createGetRequestJson(String uri, String token) {
		return MockMvcRequestBuilders.get(uri)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept("application/json;charset=UTF-8");
	}
	
	public static RequestBuilder createPostRequestJson(String uri, String content, String token) {
		return MockMvcRequestBuilders.post(uri)
				.content(content)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept("application/json;charset=UTF-8");
	}
	
	public static RequestBuilder createPutRequestJson(String uri, String content, String token) {
		return MockMvcRequestBuilders.put(uri)
				.content(content)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept("application/json;charset=UTF-8");
	}
	
	public static RequestBuilder createDeleteRequestJson(String uri, String token) {
		return MockMvcRequestBuilders.delete(uri)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept("application/json;charset=UTF-8");
	}

}
