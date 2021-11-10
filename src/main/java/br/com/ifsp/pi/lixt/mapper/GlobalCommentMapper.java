package br.com.ifsp.pi.lixt.mapper;

import br.com.ifsp.pi.lixt.data.business.globalcomment.GlobalComment;
import br.com.ifsp.pi.lixt.dto.GlobalCommentDto;
import br.com.ifsp.pi.lixt.utils.mapper.Mapper;

import java.util.Objects;

public abstract class GlobalCommentMapper extends Mapper {

    private GlobalCommentMapper() {}

    public static GlobalCommentDto entityToDto(GlobalComment entity) {
        if(Objects.isNull(entity))
            return null;

        return GlobalCommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .userId(entity.getUserId())
                .date(entity.getDate())
                .productId(entity.getProductId())
                .isPublic(entity.getIsPublic())
                .user(map(entity.getUser(), UserMapper::entityToDto))
                .build();
    }

    public static GlobalComment dtoToEntity(GlobalCommentDto dto) {
        if(Objects.isNull(dto))
            return null;

        return GlobalComment.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .userId(dto.getUserId())
                .date(dto.getDate())
                .productId(dto.getProductId())
                .isPublic(dto.getIsPublic())
                .user(map(dto.getUser(), UserMapper::dtoToEntity))
                .build();
    }
}
