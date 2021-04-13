package pl.michaldurawa.nbpexchangerate.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class NBPExchangeRateApi {
		
	private NBPExchangeRepo repo;
	
	public NBPExchangeRateApi() {
		this.repo = new NBPExchangeRepo();
	}

	public CurrencyAvrageRateTable getTodayTable() {
		return repo.getTable();
	}
	
	public CurrencyAvrageRateTable getTableByDay(LocalDate date) {
		return repo.getTable(date);
	}
	
	public List<CurrencyAvrageRateTable> getTableBeetwenDates(LocalDate startDate, LocalDate endDate) {
		return repo.getTable(startDate, endDate);
	}
	
	public Optional<CurrencyAvrageRate> getCurrencyRateByCode(String code) {
		return repo.getCurrencyRate(code);
	}
	
	public Optional<CurrencyAvrageRate> getCurrencyRateByCodAndDate(String code, LocalDate date) {
		return repo.getCurrencyRateByCodByDate(code, date);
	}
	
	public Map<LocalDate, Optional<CurrencyAvrageRate>> getCurrencyRateBeetweenDates(String code, LocalDate startDate, LocalDate endDate) {
		return repo.getCurrencyRateBeetweenDates(code, startDate, endDate);
	}
	
	public BuySellTableRate getBuySellTable() {
		return repo.getBuySellTable();
	}
	
	public BuySellTableRate getBuySellTable(LocalDate date) {
		return repo.getBuySellTable(date);
	}
	
	public List<BuySellTableRate> getBuySellTableBeetwenDates(LocalDate startDate, LocalDate endDate) {
		return repo.getBuySellTable(startDate, endDate);
	}
	
	public Optional<BuySellRate> getCurrencyBuySellRateByCode(String code) {
		return repo.getBuySellRate(code);
	}
	
	public Optional<BuySellRate> getCurrencyBuySellRateByCodAndDate(String code, LocalDate date) {
		return repo.getCurrencyBuySellRateByCodByDate(code, date);
	}
	
	public Map<LocalDate, Optional<BuySellRate>> getCurrencyBuySellRateBeetweenDates(String code, LocalDate startDate, LocalDate endDate) {
		return repo.getCurrencyBuySellRateBeetweenDates(code, startDate, endDate);
	}
	
}
