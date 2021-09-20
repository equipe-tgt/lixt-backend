package br.com.ifsp.pi.lixt.dto;

import lombok.*;

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
}
