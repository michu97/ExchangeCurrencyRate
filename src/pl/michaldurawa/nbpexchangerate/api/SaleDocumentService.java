package pl.michaldurawa.nbpexchangerate.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;

public class SaleDocumentService {
	public void insert() throws ConnectionToExternalApiException {
		NBPExchangeRateApi api = new NBPExchangeRateApi();
//		Optional<BigDecimal> amountInPLN = api.getAmountInPLN(LocalDate.of(2021,1,20), new BigDecimal(200), CurrencyCode.JPY);
			BigDecimal rate;
				rate = api.getRate(LocalDate.of(2021, 4, 10), new BigDecimal(200), CurrencyCode.USD);
			System.out.println(rate);
	}
}
