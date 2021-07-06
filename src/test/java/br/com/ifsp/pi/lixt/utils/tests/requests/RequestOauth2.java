package br.com.ifsp.pi.lixt.utils.tests.requests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

public class RequestOauth2 {
	
	private static JacksonJsonParser jsonParser = new JacksonJsonParser();
	
	public static String authenticate(MockMvc mockMvc, OauthUserDto user) throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.add("grant_type", "password");
		params.add("username", user.getUsername());
		params.add("password", user.getPassword());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		httpHeaders.add("Authorization", "basic Y2xpZW50OjEyMzQ1Ng==");
		httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
		
		ResultActions result = mockMvc.perform(post("/oauth/token").params(params).headers(httpHeaders).accept("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String resultString = result.andReturn().getResponse().getContentAsString();
		return "bearer ".concat(jsonParser.parseMap(resultString).get("access_token").toString());
	}


}
