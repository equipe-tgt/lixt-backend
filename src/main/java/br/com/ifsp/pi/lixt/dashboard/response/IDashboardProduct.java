package br.com.ifsp.pi.lixt.dashboard.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IDashboardProduct {
	
	public LocalDateTime getDate();
	
	public BigDecimal getPrice();

}
