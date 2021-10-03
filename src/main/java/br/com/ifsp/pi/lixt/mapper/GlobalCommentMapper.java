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
                .productId(entity.getProductId())
                .build();
    }

    public static GlobalComment dtoToEntity(GlobalCommentDto dto) {
        if(Objects.isNull(dto))
            return null;

        return GlobalComment.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .build();
    }
}
