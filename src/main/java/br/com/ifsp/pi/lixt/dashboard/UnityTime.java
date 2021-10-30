package br.com.ifsp.pi.lixt.dashboard;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UnityTime {
	
	DAILY("DAY"),
	WEEKLY("WEEK"),
	MONTHLY("MONTH"),
	YEARLY("YEAR");
	
	private String unity;
	
	public String getParams(String field) {
		return unity + "(" + field + ")";
	}

}
