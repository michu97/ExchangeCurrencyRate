package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.json.JSONObject;

public class SaleDocumentService {
	public void insert() {
		ExchangeRateApi nbpApi = new NbpApi();
		BigDecimal amountFromPLN = nbpApi.getAmountFromPLN(LocalDate.of(2020, 12, 25),new BigDecimal(150), CurrencyCode.EUR);
		System.out.println(amountFromPLN);
		
	}
}
