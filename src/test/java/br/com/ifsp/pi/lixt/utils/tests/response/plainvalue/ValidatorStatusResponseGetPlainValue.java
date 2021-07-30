package br.com.ifsp.pi.lixt.utils.tests.response.plainvalue;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilderPlainValue;

public abstract class ValidatorStatusResponseGetPlainValue {

	private ValidatorStatusResponseGetPlainValue() {}
	
	public static void isOk(MockMvc mockMvc, OauthUserDto user, String uri) throws Exception {
		validatePostStatusResponse(mockMvc, user, uri, MockMvcResultMatchers.status().isOk());
	}

	private static void validatePostStatusResponse(MockMvc mockMvc, OauthUserDto user, String uri, ResultMatcher status) throws Exception {
		String token = RequestOauth2.authenticate(mockMvc, user);
		mockMvc.perform(ResquestBuilderPlainValue.createGetRequestPlainValue(uri, token));
	}
}
