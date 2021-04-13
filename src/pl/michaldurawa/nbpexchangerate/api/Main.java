package pl.michaldurawa.nbpexchangerate.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;



public class Main {

	public static void main(String[] args) {
//		NBPExchangeRepo repo = new NBPExchangeRepo();
		NBPExchangeRateApi api = new NBPExchangeRateApi();
//		CurrencyAvrageRateTable table = api.getTableByDay(LocalDate.of(2021, 3, 1));
//		System.out.println(table.getEffectiveDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
//		System.out.println(table.getRates().get(1).getMid());
//		
//		List<CurrencyAvrageRateTable> table2 = api.getTableBeetwenDates(LocalDate.of(2021, 3, 1), LocalDate.of(2021, 4, 5));
//		
//		for (var c : table2) {
//			System.out.print(c.getEffectiveDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + " ");
//			c.getRates().forEach(x -> System.out.print(x.getMid() + " "));
//			System.out.println();
//		}
//		
//		Optional<CurrencyAvrageRate> currencyRate = api.getCurrencyRateByCode("USD");
//		currencyRate.ifPresent(x -> System.out.println(x.getMid()));
//		
//		Map<LocalDate, Optional<CurrencyAvrageRate>> currencyRateBeetweenDates = api.getCurrencyRateBeetweenDates("USD", LocalDate.of(2021, 3, 1), LocalDate.of(2021, 4, 5));
//		for (Map.Entry<LocalDate, Optional<CurrencyAvrageRate>> entry : currencyRateBeetweenDates.entrySet()) {
//			System.out.println(entry.getKey() + " " + entry.getValue().get().getCode() + entry.getValue().get().getMid());
//		}
		
		
		BuySellTableRate buy = api.getBuySellTable();
		
		System.out.println(buy.getRates().get(0).getBid());
	}

}
