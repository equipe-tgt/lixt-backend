package br.com.ifsp.pi.lixt.utils.conversion;

import java.math.BigDecimal;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Operators {
	
	protected static final String COMMA = ",";
	protected static final String QUOTATION_MARK = "\"";
	protected static final String NEW_LINE = "\n";
	protected static final String KEY_OPEN = "{";
	protected static final String KEY_CLOSE = "}";
	protected static final String COLON = ":";
	
	protected static String createJsonLine(String key) {
		return QUOTATION_MARK.concat(key).concat(QUOTATION_MARK)
				.concat(COLON)
				.concat("null");
	}
	
	protected static String createJsonLine(String key, String value) {
		return QUOTATION_MARK.concat(key).concat(QUOTATION_MARK)
				.concat(COLON)
				.concat(QUOTATION_MARK).concat(value.toString()).concat(QUOTATION_MARK);
	}
	
	protected static String createJsonLine(String key, Long value) {
		return QUOTATION_MARK.concat(key).concat(QUOTATION_MARK)
				.concat(COLON)
				.concat(Objects.isNull(value) ? "null" : value.toString());
	}
	
	protected static String createJsonLine(String key, Integer value) {
		return QUOTATION_MARK.concat(key).concat(QUOTATION_MARK)
				.concat(COLON)
				.concat(Objects.isNull(value) ? "null" : value.toString());
	}
	
	protected static String createJsonLine(String key, Boolean value) {
		return QUOTATION_MARK.concat(key).concat(QUOTATION_MARK)
				.concat(COLON)
				.concat(Objects.isNull(value) ? "false" : value.toString());
	}
	
	protected static String createJsonLine(String key, BigDecimal value) {
		return QUOTATION_MARK.concat(key).concat(QUOTATION_MARK)
				.concat(COLON)
				.concat(Objects.isNull(value) ? "null" : value.toString());
	}

}
