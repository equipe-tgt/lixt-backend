package br.com.ifsp.pi.lixt.utils.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.utils.security.oauth.function.PasswordGenerator;

@SpringBootTest
public class GeneratePasswordTest {
	
	@Test
	public void generatePassword() {
		System.out.println(PasswordGenerator.generateRandomPassword());
		System.out.println(PasswordGenerator.generateRandomPassword());
		System.out.println(PasswordGenerator.generateRandomPassword());
		System.out.println(PasswordGenerator.generateRandomPassword());
		System.out.println(PasswordGenerator.generateRandomPassword());
	}

}
