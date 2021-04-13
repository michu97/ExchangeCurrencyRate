package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;

public class BuySellRate {
	private String currency;
    private String code;
    private BigDecimal bid;
    private BigDecimal ask;
    
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getBid() {
		return bid;
	}
	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}
	public BigDecimal getAsk() {
		return ask;
	}
	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}
    
    
}
