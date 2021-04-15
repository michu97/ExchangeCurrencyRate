package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;

public class SaleDocumentService {
	public void insert() throws ConnectionToExternalApiException {
		NBPExchangeRateApi api = new NBPExchangeRateApi();
			BigDecimal  rate = api.getAmmountInPLN(LocalDate.of(2021, 4, 10),
						       new BigDecimal(200), CurrencyCode.USD);
			System.out.println(rate);
	}
}
