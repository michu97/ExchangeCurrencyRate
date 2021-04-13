package pl.michaldurawa.nbpexchangerate.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class NBPExchangeRateApi {
		
	private NBPExchangeRepo repo;
	
	public NBPExchangeRateApi(NBPExchangeRepo repo) {
		this.repo = repo;
	}

	public CurrencyRateTable getTodayTable() {
		return repo.getTable();
	}
	
	public CurrencyRateTable getTableByDay(LocalDate date) {
		return repo.getTable(date);
	}
	
	public List<CurrencyRateTable> getTableBeetwenDate(LocalDate startDate, LocalDate endDate) {
		return repo.getTable(startDate, endDate);
	}
	
	public Optional<CurrencyRate> getCurrencyRateByCode(String code) {
		return repo.getCurrencyRate(code);
	}
	
	public Optional<CurrencyRate> getCurrencyRateByCodByDate(String code, LocalDate date) {
		return repo.getCurrencyRateByCodByDate(code, date);
	}
	
	public Map<LocalDate, Optional<CurrencyRate>> getCurrencyRateBeetweenDates(String code, LocalDate startDate, LocalDate endDate) {
		return repo.getCurrencyRateBeetweenDates(code, startDate, endDate);
	}
	
}
