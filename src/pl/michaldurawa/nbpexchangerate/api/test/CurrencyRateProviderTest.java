package pl.michaldurawa.nbpexchangerate.api.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import pl.michaldurawa.nbpexchangerate.api.CurrencyCode;
import pl.michaldurawa.nbpexchangerate.api.CurrencyRateProvider;
import pl.michaldurawa.nbpexchangerate.api.NBPHandler;
import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;

public class CurrencyRateProviderTest {
	
	
	@Test
	public void shouldReturnDataFromFiveDaysEarlier() throws ConnectionToExternalApiException {
		NBPHandler handler = new NBPHandler();
		CurrencyRateProvider provider = new CurrencyRateProvider(handler);
		BigDecimal rateAmount = provider.getRateAmount(LocalDate.now().plusDays(5), new BigDecimal(150), CurrencyCode.USD);
		assertEquals(rateAmount, provider.getRateAmount(LocalDate.now(), new BigDecimal(150), CurrencyCode.USD));
	}
	
	@Test
	public void shouldReturnDataFromEarlier() throws ConnectionToExternalApiException {
		NBPHandler handler = new NBPHandler();
		CurrencyRateProvider provider = new CurrencyRateProvider(handler);
		BigDecimal rateAmount = provider.getRateAmount(LocalDate.of(2021, 4, 10), new BigDecimal(150), CurrencyCode.USD);
		assertEquals(rateAmount, provider.getRateAmount(LocalDate.of(2021, 4, 9), new BigDecimal(150), CurrencyCode.USD));
	}
}
