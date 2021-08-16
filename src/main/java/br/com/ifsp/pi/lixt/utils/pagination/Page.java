package br.com.ifsp.pi.lixt.utils.pagination;

import org.springframework.data.domain.PageRequest;

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
public class Page<T> {
	
	private T filter;

	private int page;

	private int size;
	
	public PageRequest getPaginationParams() {
		return PageRequest.of(page, size);
	}

}
