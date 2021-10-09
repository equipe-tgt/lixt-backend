package br.com.ifsp.pi.lixt.unity.utils.conversion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import br.com.ifsp.pi.lixt.utils.database.operations.Like;
import br.com.ifsp.pi.lixt.utils.pagination.Page;
import br.com.ifsp.pi.lixt.utils.security.Users;

@SpringBootTest
@DisplayName("Testar utilitários do sistema")
class UtilsTests {
	
	@Test
	void testUserToken() {
		assertThat(Users.getUserId()).isNull();
		assertThat(Users.getEmail()).isNull();
	}
	
	@Test
	void testPaginate() {
		Page page = Page.builder().size(10).pageNumber(0).build();
		PageRequest pageRequest = page.getPaginationParams();
		
		assertThat(pageRequest.getPageSize()).isEqualTo(10);
		assertThat(pageRequest.getPageNumber()).isEqualTo(0);
		assertThat(pageRequest.getOffset()).isEqualTo(0);
	}
	
	@Test
	void testDatabaseOperators() {
		assertThat(Like.endsWith("abc")).endsWith("%");
		assertThat(Like.startsWith("abc")).startsWith("%");
	}

}
