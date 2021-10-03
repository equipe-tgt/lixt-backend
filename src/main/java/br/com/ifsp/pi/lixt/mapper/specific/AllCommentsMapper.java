package br.com.ifsp.pi.lixt.mapper.specific;

import br.com.ifsp.pi.lixt.data.business.comment.Comment;
import br.com.ifsp.pi.lixt.data.business.globalComment.GlobalComment;
import br.com.ifsp.pi.lixt.dto.specific.AllCommentsDto;
import br.com.ifsp.pi.lixt.mapper.CommentMapper;
import br.com.ifsp.pi.lixt.mapper.GlobalCommentMapper;
import br.com.ifsp.pi.lixt.utils.mapper.Mapper;

import java.util.List;

public abstract class AllCommentsMapper extends Mapper {

    private AllCommentsMapper() {}

    public static AllCommentsDto entityToDto(List<GlobalComment> globalComments, List<Comment> comments) {

        return AllCommentsDto.builder()
                .globalCommentsDto(map(globalComments, GlobalCommentMapper::entityToDto))
                .commentsDto(map(comments, CommentMapper::entityToDto))
                .build();
    }
}
