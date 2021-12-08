package br.com.ifsp.pi.lixt.mapper.logger;

import br.com.ifsp.pi.lixt.integration.barcode.v1.logger.BarcodeLogger;
import br.com.ifsp.pi.lixt.utils.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class BarcodeLoggerMapper extends Mapper {
	
	public static BarcodeLogger build(Long counter, String barcode, Long productId) {

		return BarcodeLogger.builder()
				.counterOfDay(counter)
				.barcode(barcode)
				.idProduct(productId)
				.build();
	}
	
	public static BarcodeLogger build(Long counter, String barcode) {

		return BarcodeLogger.builder()
				.counterOfDay(counter)
				.barcode(barcode)
				.build();
	}

}
