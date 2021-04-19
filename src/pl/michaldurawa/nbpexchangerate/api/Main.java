package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

public class Main {
	
	public static void main(String[] args) {
		SaleDocumentService saleDocumentService = new SaleDocumentService();
		saleDocumentService.insert();
		
		 List<CurrencyRate> arrayList = new ArrayList<>();
		 CurrencyRateTable currencyRateTable = new CurrencyRateTable();
		 
		 for (int i = 0; i < 50; i ++) {
			CurrencyRate currencyRate = new CurrencyRate();
			currencyRate.setEffectiveDate(Date.from(LocalDate.now().minusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			float a = ((new Random().nextInt(500)) + 4500) / 1000f;
			currencyRate.setMid(new BigDecimal(a).setScale(4, BigDecimal.ROUND_HALF_UP));
			arrayList.add(currencyRate);
		 }
		 
		 currencyRateTable.setCode("USD");
		 currencyRateTable.setRates(arrayList);
		 System.out.println(new JSONObject(currencyRateTable));
	}
}
