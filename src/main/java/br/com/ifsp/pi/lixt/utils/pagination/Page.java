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
public class Page {

	private int pageNumber;

	private int size;
	
	public PageRequest getPaginationParams() {
		return PageRequest.of(pageNumber, size);
	}

}
