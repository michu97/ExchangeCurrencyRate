package pl.michaldurawa.nbpexchangerate.api;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;

public class CurrencyRateProvider {
	private final NBPHandler handler;

	public CurrencyRateProvider(NBPHandler handler) {
		this.handler = handler;
	}
	
	public BigDecimal getAmountInPLN(LocalDate date, BigDecimal ammount,
			CurrencyCode code) throws ConnectionToExternalApiException {
		try {
			if (date.compareTo(LocalDate.now()) > 0) {
				date = LocalDate.now();
			}
			return getCurrencyRate(date, ammount, code)
					.getMid().multiply(ammount);
		} catch (FileNotFoundException e) {
			return getAmountInPLN(date.minusDays(1), ammount, code);
		}
	}
	
	public BigDecimal getAmountInPLN(BigDecimal ammount,
			CurrencyCode code) throws ConnectionToExternalApiException {
		return getAmountInPLN(LocalDate.now(), ammount, code);
	}
	
	private CurrencyRate getCurrencyRate(LocalDate date, BigDecimal ammount,
			CurrencyCode code) throws FileNotFoundException,
								ConnectionToExternalApiException {
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
