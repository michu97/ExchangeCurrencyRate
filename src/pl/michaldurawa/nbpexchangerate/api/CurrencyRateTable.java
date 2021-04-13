package pl.michaldurawa.nbpexchangerate.api;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CurrencyRateTable {
	private String table;
    private String no;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pl_PL")
    private Date effectiveDate;
    private List<CurrencyRate> rates;
    
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
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
	public List<CurrencyRate> getRates() {
		return rates;
	}
	public void setRates(List<CurrencyRate> rates) {
		this.rates = rates;
	}
    
}
