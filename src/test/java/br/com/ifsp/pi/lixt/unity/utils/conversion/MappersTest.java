package br.com.ifsp.pi.lixt.unity.utils.conversion;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.dto.*;
import br.com.ifsp.pi.lixt.mapper.specific.AllCommentsMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ifsp.pi.lixt.mapper.CategoryMapper;
import br.com.ifsp.pi.lixt.mapper.CommentMapper;
import br.com.ifsp.pi.lixt.mapper.ItemOfPurchaseMapper;
import br.com.ifsp.pi.lixt.mapper.ListMembersMapper;
import br.com.ifsp.pi.lixt.mapper.ListOfItemsMapper;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.mapper.ProductOfListMapper;
import br.com.ifsp.pi.lixt.mapper.PurchaseListMapper;
import br.com.ifsp.pi.lixt.mapper.PurchaseLocalMapper;
import br.com.ifsp.pi.lixt.mapper.PurchaseMapper;
import br.com.ifsp.pi.lixt.mapper.UserMapper;
import br.com.ifsp.pi.lixt.mapper.specific.InviteMapper;
import br.com.ifsp.pi.lixt.utils.security.oauth.objects.OauthUserDto;

@SpringBootTest
@DisplayName("Testar mappers do sistema")
class MappersTest {
	
	@Test
	void dtoToEntityNull() {
		assertNull(CategoryMapper.dtoToEntity(null));
		assertNull(CommentMapper.dtoToEntity(null));
		assertNull(ItemOfPurchaseMapper.dtoToEntity(null));
		assertNull(ListMembersMapper.dtoToEntity(null));
		assertNull(ListOfItemsMapper.dtoToEntity(null));
		assertNull(ProductMapper.dtoToEntity(null));
		assertNull(ProductOfListMapper.dtoToEntity(null));
		assertNull(PurchaseMapper.dtoToEntity(null));
		assertNull(PurchaseListMapper.dtoToEntity(null));
		assertNull(PurchaseLocalMapper.dtoToEntity(null));
		assertNull(UserMapper.dtoToEntity(null));
	}
	
	@Test
	void entityToDtoNull() {
		assertNull(CategoryMapper.entityToDto(null));
		assertNull(CommentMapper.entityToDto(null));
		assertNull(ItemOfPurchaseMapper.entityToDto(null));
		assertNull(ListMembersMapper.entityToDto(null));
		assertNull(ListOfItemsMapper.entityToDto(null));
		assertNull(ProductMapper.entityToDto(null));
		assertNull(ProductOfListMapper.entityToDto(null));
		assertNull(PurchaseMapper.entityToDto(null));
		assertNull(PurchaseListMapper.entityToDto(null));
		assertNull(PurchaseLocalMapper.entityToDto(null));
		assertNull(UserMapper.entityToDto(null));
	}
	
	@Test
	void casesNull() {
		assertNull(UserMapper.dtoOauthToDto(null));
		assertNull(InviteMapper.entityToDto(null));
		
		assertNotNull(ListMembersMapper.dtoToEntity(new ListMembersDto()));
		assertNotNull(UserMapper.dtoOauthToDto(new OauthUserDto()));
		assertNotNull(UserMapper.dtoToEntity(new UserDto()));
	}
	
	@Test
	void testComplexObject() {
		
		assertNotNull(ListOfItemsMapper.dtoToEntity(
				ListOfItemsDto.builder().productsOfList(new ArrayList<>()).build()
		).getProductsOfList());
		
		assertNotNull(ListOfItemsMapper.dtoToEntity(
				ListOfItemsDto.builder().listMembers(new ArrayList<>()).build()
		).getListMembers());

		assertNotNull(AllCommentsMapper.entityToDto(new ArrayList<GlobalCommentDto>(), new ArrayList<CommentDto>()).getCommentsDto());

		assertNotNull(AllCommentsMapper.entityToDto(new ArrayList<GlobalCommentDto>(), new ArrayList<CommentDto>()).getGlobalCommentsDto());
	}

}
