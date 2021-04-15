package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;


public class NBPExchangeRateApi {
		
	private NBPExchangeRepo repo;
	private CurrencyRateProvider provider;
	
	public NBPExchangeRateApi() {
		this.repo = new NBPExchangeRepo();
		NBPHandler nbpHandler = new NBPHandler();
		this.provider = new CurrencyRateProvider(nbpHandler);
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
		return repo.getAvrageCurrencyRateByCodAndDate(code, date);
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
		return repo.getCurrencyBuySellRateByCodAndDate(code, date);
	}
	
	public Map<LocalDate, Optional<BuySellRate>> getCurrencyBuySellRateBeetweenDates(String code, LocalDate startDate, LocalDate endDate) {
		return repo.getCurrencyBuySellRateBeetweenDates(code, startDate, endDate);
	}
	
	public Map<String, BigDecimal> getTableFromPLN(LocalDate date, BigDecimal pln) {
		return repo.getTableFromPLN(date, pln);
	}
	
	public Optional<BigDecimal> getAmountFromPLN(LocalDate date, BigDecimal pln, String code) {
		return repo.getAmountFromPLN(date, pln, code);
	}
	
	public Optional<BigDecimal> getAmountInPLN(LocalDate date, BigDecimal foreignExchange, CurrencyCode code) {
		return repo.getAmountInPLN(date, foreignExchange, code.name());
	}
	
	public Optional<BigDecimal> getAmountInPLNByAvrageRate(LocalDate date, BigDecimal foreignExchange, String code) {
		return repo.getAmountInPLNByAvrageRate(date, foreignExchange, code);
	}
	
	public BigDecimal getAmmountInPLN(LocalDate date, BigDecimal ammount, CurrencyCode code) throws ConnectionToExternalApiException {
		return provider.getAmountInPLN(date, ammount, code);
	}
	
	public BigDecimal getAmmountInPLN(BigDecimal ammount, CurrencyCode code) throws ConnectionToExternalApiException {
		return provider.getAmountInPLN(ammount, code);
	}
}
