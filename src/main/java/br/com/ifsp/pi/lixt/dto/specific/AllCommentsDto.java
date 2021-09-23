package br.com.ifsp.pi.lixt.dto.specific;

import br.com.ifsp.pi.lixt.dto.CommentDto;
import br.com.ifsp.pi.lixt.dto.GlobalCommentDto;
import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AllCommentsDto {

    private List<GlobalCommentDto> globalCommentsDto;

    private List<CommentDto> commentsDto;
}
