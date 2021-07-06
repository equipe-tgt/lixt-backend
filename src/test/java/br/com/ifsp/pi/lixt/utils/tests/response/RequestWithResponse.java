package br.com.ifsp.pi.lixt.utils.tests.response;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;

public class RequestWithResponse {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object createPostRequestJson(MockMvc mockMvc, String uri, String content, String token, Class clazz) throws Exception {
				
		ResultActions listResult = 
				mockMvc.perform(ResquestBuilder.createPostRequestJson(uri, content, token))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String listString = listResult.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(listString, clazz);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object createGetRequestJson(MockMvc mockMvc, String uri, String token, Class clazz) throws Exception {
				
		ResultActions listResult = 
				mockMvc.perform(ResquestBuilder.createGetRequestJson(uri, token))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String listString = listResult.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(listString, clazz);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object createPostRequestJson(MockMvc mockMvc, String uri, String content, OauthUserDto user, Class clazz) throws Exception {
		
		String token = RequestOauth2.authenticate(mockMvc, user);
		
		ResultActions listResult = 
				mockMvc.perform(ResquestBuilder.createPostRequestJson(uri, content, token))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String listString = listResult.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(listString, clazz);
	}

}
