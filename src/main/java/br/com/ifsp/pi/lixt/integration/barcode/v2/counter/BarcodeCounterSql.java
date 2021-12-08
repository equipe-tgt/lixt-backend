package br.com.ifsp.pi.lixt.integration.barcode.v2.counter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BarcodeCounterSql {

	public static String createBarcodeCounterTable() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder = stringBuilder.append("create table if not exists tb_barcode_counter (")
				.append("	nr_request_of_day LONG,")
				.append("	dt_date DATE")
				.append(");");
		
		return stringBuilder.toString();
	}
	
	public static String insertIfIsEmpty() {
		return "insert into tb_barcode_counter (nr_request_of_day, dt_date) select 0, now() where not exists (select * from tb_barcode_counter);";
	}
	
	public static String updateIfLate() {
		return "update tb_barcode_counter set nr_request_of_day = 0 where dt_date < NOW();";
	}
	
	public static String count() {
		return "select nr_request_of_day from tb_barcode_counter;";
	}
	
	public static String updateBarcode() {
		return "update tb_barcode_counter set dt_date = NOW();";
	}
	
	public static String updateBarcodeCount() {
		return "update tb_barcode_counter set nr_request_of_day = ?;";
	}
}
