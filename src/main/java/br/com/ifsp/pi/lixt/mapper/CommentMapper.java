package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import br.com.ifsp.pi.lixt.data.business.comment.Comment;
import br.com.ifsp.pi.lixt.dto.CommentDto;

public abstract class CommentMapper {
	
	public static CommentDto entityToDto(Comment entity) {

		if(Objects.isNull(entity))
			return null;
		
		return CommentDto.builder()
				.id(entity.getId())
				.userId(entity.getUserId())
				.productOfListId(entity.getProductOfListId())
				.content(entity.getContent())
				.user(UserMapper.entityToDto(entity.getUser()))
				.build();
	}
	
	public static Comment dtoToEntity(CommentDto dto) {

		if(Objects.isNull(dto))
			return null;
		
		return Comment.builder()
				.id(dto.getId())
				.userId(dto.getUserId())
				.productOfListId(dto.getProductOfListId())
				.content(dto.getContent())
				.user(UserMapper.dtoToEntity(dto.getUser()))
				.build();
	}

}
