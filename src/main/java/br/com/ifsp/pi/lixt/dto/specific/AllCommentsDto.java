package br.com.ifsp.pi.lixt.dto.specific;

import br.com.ifsp.pi.lixt.dto.CommentDto;
import br.com.ifsp.pi.lixt.dto.GlobalCommentDto;
import lombok.*;

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
