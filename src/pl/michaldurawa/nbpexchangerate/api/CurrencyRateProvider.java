package pl.michaldurawa.nbpexchangerate.api;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	
	public BigDecimal getRateAmount(LocalDate date, BigDecimal ammount, CurrencyCode code) throws ConnectionToExternalApiException {
		CurrencyRate currencyRate = null;
		try {
			if (date.compareTo(LocalDate.now()) > 0) {
				date = LocalDate.now();
			}
			currencyRate = getCurrencyRate(date, ammount, code);
		} catch (FileNotFoundException e) {
			return getRateAmount(date.minusDays(1), ammount, code);
		}
		System.out.println(currencyRate.getEffectiveDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
		return currencyRate.getBid().multiply(ammount);
	}
	
	private CurrencyRate getCurrencyRate(LocalDate date, BigDecimal ammount, CurrencyCode code) throws FileNotFoundException, ConnectionToExternalApiException {
		try {
			String content = handler.getContent(generateURL(date, ammount, code));
			ObjectMapper objectMapper = new ObjectMapper();
			CurrencyRateTable rateTables = objectMapper.readValue(content, CurrencyRateTable.class);
			CurrencyRate currencyRate = rateTables.getRates().get(0);
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
