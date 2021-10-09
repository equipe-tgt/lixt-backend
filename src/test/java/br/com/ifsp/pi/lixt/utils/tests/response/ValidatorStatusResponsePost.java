package br.com.ifsp.pi.lixt.utils.tests.response;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;

public abstract class ValidatorStatusResponsePost {
	
	private ValidatorStatusResponsePost() {}
	
	public static void isForbidden(MockMvc mockMvc, UserDto user, String uri, String content) throws Exception {
		validatePostStatusResponse(mockMvc, user, uri, content, MockMvcResultMatchers.status().isForbidden());
	}
	
	public static void isConflict(MockMvc mockMvc, UserDto user, String uri, String content) throws Exception {
		validatePostStatusResponse(mockMvc, user, uri, content, MockMvcResultMatchers.status().isConflict());
	}
	
	public static void isNotFound(MockMvc mockMvc, UserDto user, String uri, String content) throws Exception {
		validatePostStatusResponse(mockMvc, user, uri, content, MockMvcResultMatchers.status().isNotFound());
	}

	private static void validatePostStatusResponse(MockMvc mockMvc, UserDto user, String uri, String content, ResultMatcher status) throws Exception {
		String token = RequestOauth2.authenticate(mockMvc, user);
		mockMvc.perform(ResquestBuilder.createPostRequestJson(uri,content, token)).andExpect(status);
	}
}
