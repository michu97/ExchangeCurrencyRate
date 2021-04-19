package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CurrencyRateProvider {
	private final NBPHandler handler;

	public CurrencyRateProvider(NBPHandler handler) {
		this.handler = handler;
	}
	
	public BigDecimal getAmountInPLN(LocalDate date, BigDecimal ammount,
			CurrencyCode code) {
			return getCurrencyRate(date, ammount, code)
					.getMid().multiply(ammount);
	}
	
	public BigDecimal getAmountInPLN(BigDecimal ammount,
			CurrencyCode code) {
		return getAmountInPLN(LocalDate.now(), ammount, code);
	}
	
	private CurrencyRate getCurrencyRate(LocalDate date, BigDecimal ammount,
			CurrencyCode code) {
		try {
			String content = handler.getContent(date, ammount, code);
			ObjectMapper objectMapper = new ObjectMapper();
			CurrencyRateTable rateTables = objectMapper
					.readValue(content, CurrencyRateTable.class);
			CurrencyRate currencyRate = rateTables.getRates().get(0);
			return currencyRate;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
