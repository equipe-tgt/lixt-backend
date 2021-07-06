package br.com.ifsp.pi.lixt.utils.tests.requests;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ResquestBuilderPlainValue {
	
	public static RequestBuilder createPostRequestPlainValue(String uri, String content, String token) {
		return MockMvcRequestBuilders.post(uri)
				.content(content)
				.header("Authorization", token)
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.accept(MediaType.APPLICATION_JSON);
	}

}
