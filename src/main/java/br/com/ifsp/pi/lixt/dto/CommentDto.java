package br.com.ifsp.pi.lixt.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.ifsp.pi.lixt.utils.conversion.date.LocalDateTimeDeserializer;
import br.com.ifsp.pi.lixt.utils.conversion.date.LocalDateTimeSerializer;
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
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime date;
	
}
