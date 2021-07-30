package br.com.ifsp.pi.lixt.utils.tests.requests;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class ResquestBuilderPlainValue {
	
	private ResquestBuilderPlainValue() {}
	
	public static RequestBuilder createGetRequestPlainValue(String uri, String token) {
		return MockMvcRequestBuilders.get(uri)
				.header("Authorization", token)
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.accept("application/json;charset=UTF-8");
	}
	
	public static RequestBuilder createPostRequestPlainValue(String uri, String content, String token) {
		return MockMvcRequestBuilders.post(uri)
				.content(content)
				.header("Authorization", token)
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.accept(MediaType.APPLICATION_JSON);
	}

}
