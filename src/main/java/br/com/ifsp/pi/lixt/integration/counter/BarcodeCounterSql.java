package br.com.ifsp.pi.lixt.integration.counter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BarcodeCounterSql {
	
	public static final String getBarcodeCounterDescription() {
		return "barcode-counter";
	}
	
	public static String count() {
		return "select count(*) from tb_counter;";
	}
	
	public static String insertBarcodeCounter() {
		return "insert into tb_counter (id_counter, st_description, id_count, dt_created_at, dt_updated_at) "
				+ "values (null, 'barcode-counter', 0, now(), now());";
	}
	
	public static String updateBarcode() {
		return "update tb_counter set id_count = 0, dt_updated_at = NOW() where st_description = 'barcode-counter' and dt_updated_at < CURDATE()";
	}

}
