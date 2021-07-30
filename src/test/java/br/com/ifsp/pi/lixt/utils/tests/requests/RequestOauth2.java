package br.com.ifsp.pi.lixt.utils.tests.requests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

public abstract class RequestOauth2 {
	
	private static JacksonJsonParser jsonParser = new JacksonJsonParser();
	
	private static final String CLIENT_ID = "client";
	private static final String SECRET_ID = "123456";
	
	private RequestOauth2() {}
	
	public static String authenticate(MockMvc mockMvc, OauthUserDto user) throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.add("grant_type", "password");
		params.add("username", user.getUsername());
		params.add("password", user.getPassword());
		
		ResultActions result = mockMvc.perform(post("/oauth/token").params(params).headers(getHeaders()).accept("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String resultString = result.andReturn().getResponse().getContentAsString();
		return "bearer ".concat(jsonParser.parseMap(resultString).get("access_token").toString());
	}
	
	public static String refreshToken(MockMvc mockMvc, String refreshToken) throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.add("grant_type", "refresh_token");
		params.add("refresh_token", refreshToken);
		
		ResultActions result = mockMvc.perform(post("/oauth/token").params(params).headers(getHeaders()).accept("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String resultString = result.andReturn().getResponse().getContentAsString();
		return "bearer ".concat(jsonParser.parseMap(resultString).get("access_token").toString());
	}
	
	public static HttpHeaders getHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		
		httpHeaders.add("Authorization", "basic " + generateBasicAuthToken(CLIENT_ID, SECRET_ID));
		httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
		
		return httpHeaders;
	}

	private static String generateBasicAuthToken(String clientID, String secretID) {
		return Base64.getEncoder().encodeToString(clientID.concat(":").concat(secretID).getBytes(StandardCharsets.ISO_8859_1));
	}

}
