package br.com.ifsp.pi.lixt.mapper.specific;

import br.com.ifsp.pi.lixt.dto.CommentDto;
import br.com.ifsp.pi.lixt.dto.GlobalCommentDto;
import br.com.ifsp.pi.lixt.dto.specific.AllCommentsDto;
import br.com.ifsp.pi.lixt.mapper.CommentMapper;
import br.com.ifsp.pi.lixt.mapper.GlobalCommentMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AllCommentsMapper {

    private AllCommentsMapper() {}

    public static AllCommentsDto entityToDto(List<GlobalCommentDto> globalComments,
                                             List<CommentDto> comments) {

        return AllCommentsDto.builder()
                .globalCommentsDto(Objects.isNull(globalComments) ? null : globalComments.stream().collect(Collectors.toList()))
                .commentsDto(Objects.isNull(comments) ? null : comments.stream().collect(Collectors.toList()))
                .build();
    }
}
