package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CurrencyRate {
	private String no;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pl_PL")
	private Date effectiveDate;
	private BigDecimal mid;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public LocalDate getEffectiveDate() {
		return effectiveDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public BigDecimal getMid() {
		return mid;
	}
	public void setMid(BigDecimal bid) {
		this.mid = bid;
	}
		
	
}
