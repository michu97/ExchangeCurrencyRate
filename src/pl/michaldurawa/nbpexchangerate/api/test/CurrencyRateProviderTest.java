package pl.michaldurawa.nbpexchangerate.api.test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import pl.michaldurawa.nbpexchangerate.api.CurrencyCode;
import pl.michaldurawa.nbpexchangerate.api.CurrencyRateProvider;
import pl.michaldurawa.nbpexchangerate.api.NBPHandler;
import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;

public class CurrencyRateProviderTest {
	NBPHandler handler;
	CurrencyRateProvider provider;
	
	@Before
	public void setup() {
		handler = new NBPHandler();
		provider = new CurrencyRateProvider(handler);
	}
	
	@Test
	public void shouldReturnDataFromFiveDaysEarlier() throws ConnectionToExternalApiException {
		BigDecimal rateAmount = provider.getRateAmount(LocalDate.now().plusDays(5), new BigDecimal(150), CurrencyCode.CNY);
		assertEquals(rateAmount, provider.getRateAmount(LocalDate.now(), new BigDecimal(150), CurrencyCode.CNY));
		
	}
}
