package br.com.ifsp.pi.lixt.mapper.specific;

import br.com.ifsp.pi.lixt.dto.CommentDto;
import br.com.ifsp.pi.lixt.dto.GlobalCommentDto;
import br.com.ifsp.pi.lixt.dto.specific.AllCommentsDto;

import java.util.List;

public abstract class AllCommentsMapper {

    private AllCommentsMapper() {}

    public static AllCommentsDto entityToDto(List<GlobalCommentDto> globalComments,
                                             List<CommentDto> comments) {

        return AllCommentsDto.builder()
                .globalCommentsDto(globalComments)
                .commentsDto(comments)
                .build();
    }
}
