package pl.michaldurawa.nbpexchangerate.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class NBPExchangeRateApi {
		
	private NBPExchangeRepo repo;
	
	public NBPExchangeRateApi(NBPExchangeRepo repo) {
		this.repo = repo;
	}

	public CurrencyRateTable getToDayTable() {
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
	
}
