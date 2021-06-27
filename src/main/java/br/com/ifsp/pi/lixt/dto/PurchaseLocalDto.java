package br.com.ifsp.pi.lixt.dto;

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
public class PurchaseLocalDto {
	
	private Long id;
	
	private String name;
	
	private Double latitude;
	
	private Double longitude;	

}
