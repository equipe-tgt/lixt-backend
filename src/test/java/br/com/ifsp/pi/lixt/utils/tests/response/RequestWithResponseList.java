package br.com.ifsp.pi.lixt.utils.tests.response;

import java.util.List;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;

public abstract class RequestWithResponseList {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private RequestWithResponseList() {}
	
	public static <T> List<T> createGetRequestJson(MockMvc mockMvc, String uri, String token, Class<T> clazz) throws Exception {
		
		ResultActions listResult = 
				mockMvc.perform(ResquestBuilder.createGetRequestJson(uri, token))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String listString = listResult.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(listString, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
	}
	
	public static <T> List<T> createGetRequestJson(MockMvc mockMvc, String uri, UserDto user, Class<T> clazz) throws Exception {
		
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		String token = RequestOauth2.authenticate(mockMvc, user);
		
		ResultActions listResult = 
				mockMvc.perform(ResquestBuilder.createGetRequestJson(uri, token))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String listString = listResult.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(listString, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
	}
}
