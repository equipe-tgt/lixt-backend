package br.com.ifsp.pi.lixt.utils.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Testar e documentar tokens")
public class TokenTest {
	
	@Test
	@DisplayName("Validar criação do basic token")
	public void generateClientToken() {
		
		String token = "Basic ".concat(generateBasicAuthToken("client", "123456"));
		System.out.println(token);
		
		assertThat(token).startsWith("Basic ");
		assertThat(token.replace("Basic ", "")).isBase64();
	}
	
	private String generateBasicAuthToken(String clientID, String secretID) {
		return Base64.getEncoder().encodeToString(clientID.concat(":").concat(clientID).getBytes(StandardCharsets.ISO_8859_1));
	}

}
