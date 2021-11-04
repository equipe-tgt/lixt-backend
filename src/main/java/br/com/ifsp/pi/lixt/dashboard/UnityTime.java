package br.com.ifsp.pi.lixt.dashboard;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UnityTime {
	
	DAILY("DAY") {
		@Override
		public String getParams(String field) {
			return call("concat", call("day", field) + getBar() + call("month", field) + getBar() + call("year", field));
		}	
	},
	
	WEEKLY("WEEK") {
		@Override
		public String getParams(String field) {
			return call("concat", call("week", field) + getBar() + call("year", field));
		}	
	},
	
	MONTHLY("MONTH") {
		@Override
		public String getParams(String field) {
			return call("concat", call("month", field) + getBar() + call("year", field));
		}	
	},
	
	YEARLY("YEAR") {
		@Override
		public String getParams(String field) {
			return this.getUnity() + "(" + field + ")";
		}	
	};
	
	private String unity;
	
	public abstract String getParams(String field);
	
	private static String call(String method, String param) {
		return method + "(" + param + ")";
	}
	
	private static String getBar() {
		return ", '/', ";
	}

}
