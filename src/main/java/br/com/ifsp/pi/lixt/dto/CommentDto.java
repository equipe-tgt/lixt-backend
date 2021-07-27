package br.com.ifsp.pi.lixt.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommentDto {

	private Long id;
	
	private Long userId;
	
	private Long productOfListId;
	
	private String content;
	
	private UserDto user;
	
	private LocalDateTime date;
	
}
