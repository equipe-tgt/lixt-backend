package br.com.ifsp.pi.lixt.utils.tests.response;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;

public class ValidatorStatusResponseGet {
	
	public static void isOk(MockMvc mockMvc, OauthUserDto user, String uri) throws Exception {
		validateGetStatusResponse(mockMvc, user, uri, MockMvcResultMatchers.status().isOk());
	}
	
	public static void isForbidden(MockMvc mockMvc, OauthUserDto user, String uri) throws Exception {
		validateGetStatusResponse(mockMvc, user, uri, MockMvcResultMatchers.status().isForbidden());
	}

	private static void validateGetStatusResponse(MockMvc mockMvc, OauthUserDto user, String uri, ResultMatcher status) throws Exception {
		String token = RequestOauth2.authenticate(mockMvc, user);
		mockMvc.perform(ResquestBuilder.createGetRequestJson(uri, token)).andExpect(status);
	}
	
}