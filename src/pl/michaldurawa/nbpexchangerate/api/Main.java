package pl.michaldurawa.nbpexchangerate.api;

import java.util.Optional;

public class Main {

	public static void main(String[] args) {
		NBPExchangeRateApi api = new NBPExchangeRateApi();
		System.out.println(api.getExchangeRateTable());
		CurrencyRateTable table = api.getTable("2021-03-01");
		System.out.println(table.getRates().get(1).getMid());
		
		Optional<CurrencyRate> currencyRate = api.getCurrencyRate("USD");
		currencyRate.ifPresent(x -> System.out.println(x.getMid()));
	}

}
