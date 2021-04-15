package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class SaleDocumentService {
	public void insert() {
		NBPExchangeRateApi api = new NBPExchangeRateApi();
		Optional<BigDecimal> amountInPLN = api.getAmountInPLN(LocalDate.of(2021,1,4), new BigDecimal(200), CurrencyCode.JPY);
		System.out.println(amountInPLN.get());
	}
}
