package br.com.ifsp.pi.lixt.unity.utils.swagger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testar funcionalidades de swagger")
class SwaggerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Acessar swagger")
	void getSwagger() throws Exception {
		mockMvc.perform(get("/swagger-ui.html")).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
