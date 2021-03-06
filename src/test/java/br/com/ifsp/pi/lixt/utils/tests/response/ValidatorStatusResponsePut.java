package br.com.ifsp.pi.lixt.utils.tests.response;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;

public abstract class ValidatorStatusResponsePut {
	
	private ValidatorStatusResponsePut() {}
	
	public static void isOk(MockMvc mockMvc, UserDto user, String uri, String content) throws Exception {
		validatePostStatusResponse(mockMvc, user, uri, content, MockMvcResultMatchers.status().isOk());
	}
	
	public static void isForbidden(MockMvc mockMvc, UserDto user, String uri, String content) throws Exception {
		validatePostStatusResponse(mockMvc, user, uri, content, MockMvcResultMatchers.status().isForbidden());
	}
	
	public static void isPreconditionFailed(MockMvc mockMvc, UserDto user, String uri, String content) throws Exception {
		validatePostStatusResponse(mockMvc, user, uri, content, MockMvcResultMatchers.status().isPreconditionFailed());
	}

	private static void validatePostStatusResponse(MockMvc mockMvc, UserDto user, String uri, String content, ResultMatcher status) throws Exception {
		String token = RequestOauth2.authenticate(mockMvc, user);
		mockMvc.perform(ResquestBuilder.createPutRequestJson(uri,content, token)).andExpect(status);
	}
	
}
