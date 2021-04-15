package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;

public class CurrencyRateProvider {
	private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";
	private final NBPHandler handler;

	public CurrencyRateProvider(NBPHandler handler) {
		this.handler = handler;
	}
	
	public CurrencyRate getCurrencyRate(LocalDate date, BigDecimal ammount, CurrencyCode code) throws ConnectionToExternalApiException {
		try {
			String content = handler.getContent(generateURL(date, ammount, code));
			ObjectMapper objectMapper = new ObjectMapper();
			CurrencyRateTable[] rateTables = objectMapper.readValue(content, CurrencyRateTable[].class);
			CurrencyRate currencyRate = rateTables[0].getRates().get(0);
			return currencyRate;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private URL generateURL(LocalDate date, BigDecimal ammount, CurrencyCode code) {
		String url = API_URL + code.name() + "/" + date.format(DateTimeFormatter.ISO_LOCAL_DATE);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
