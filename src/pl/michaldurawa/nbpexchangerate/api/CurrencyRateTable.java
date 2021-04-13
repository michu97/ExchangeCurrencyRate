package pl.michaldurawa.nbpexchangerate.api;

import java.util.List;

public class CurrencyRateTable {
	public String table;
    public String no;
    public String effectiveDate;
    public List<CurrencyRate> rates;
    
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
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public List<CurrencyRate> getRates() {
		return rates;
	}
	public void setRates(List<CurrencyRate> rates) {
		this.rates = rates;
	}
    
}
