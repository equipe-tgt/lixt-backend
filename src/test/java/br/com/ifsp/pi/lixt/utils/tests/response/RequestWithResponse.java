package br.com.ifsp.pi.lixt.utils.tests.response;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.ifsp.pi.lixt.dto.UserDto;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.requests.ResquestBuilder;

public abstract class RequestWithResponse {

	private static ObjectMapper objectMapper = new ObjectMapper();

	private RequestWithResponse() {}

	public static <T> T createPostRequestJson(MockMvc mockMvc, String uri, String content, String token, Class<T> clazz) throws Exception {

		ResultActions listResult =
				mockMvc.perform(ResquestBuilder.createPostRequestJson(uri, content, token))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

		String listString = listResult.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(listString, clazz);
	}

	public static <T> T createGetRequestJson(MockMvc mockMvc, String uri, String token, Class<T> clazz) throws Exception {

		ResultActions listResult =
				mockMvc.perform(ResquestBuilder.createGetRequestJson(uri, token))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

		String listString = listResult.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(listString, clazz);
	}

	public static <T> T createPostRequestJson(MockMvc mockMvc, String uri, String content, UserDto user, Class<T> clazz) throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		String token = RequestOauth2.authenticate(mockMvc, user);

		ResultActions listResult =
				mockMvc.perform(ResquestBuilder.createPostRequestJson(uri, content, token))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

		String listString = listResult.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(listString, clazz);
	}

	public static <T> T createPutRequestJson(MockMvc mockMvc, String uri, String content, UserDto user, Class<T> clazz) throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		String token = RequestOauth2.authenticate(mockMvc, user);

		ResultActions listResult =
				mockMvc.perform(ResquestBuilder.createPutRequestJson(uri, content, token))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

		String listString = listResult.andReturn().getResponse().getContentAsString();
		return objectMapper.readValue(listString, clazz);
	}

}
