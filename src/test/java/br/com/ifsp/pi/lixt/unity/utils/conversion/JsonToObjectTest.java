package br.com.ifsp.pi.lixt.unity.utils.conversion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ifsp.pi.lixt.data.dto.ListOfItemsDtoDataJson;
import br.com.ifsp.pi.lixt.dto.ListOfItemsDto;

@SpringBootTest
class JsonToObjectTest {
	
	@Test
	void testConversion() throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ListOfItemsDto list = objectMapper.readValue(ListOfItemsDtoDataJson.initializeValues(), ListOfItemsDto.class);
		
		assertThat(list).isNotNull();
	}

}
