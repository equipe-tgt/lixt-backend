package br.com.ifsp.pi.lixt.dto;

import br.com.ifsp.pi.lixt.utils.conversion.date.LocalDateTimeDeserializer;
import br.com.ifsp.pi.lixt.utils.conversion.date.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GlobalCommentDto {

    private Long id;

    private String content;

    private Long userId;

    private Long productId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;
}
