package pl.michaldurawa.nbpexchangerate.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NBPExchangeRepo {
	private static final String GET = "GET";
	private static final String ContentType = "Content-Type";
	private static final String ApplicationJson = "application/json";
	private static final String ApiUrl = "http://api.nbp.pl/api/exchangerates/tables/A/";
	
	public Optional<CurrencyRate> getCurrencyRateByCodByDate(String code, LocalDate date) {
			CurrencyRateTable table = getTable(date);
			return table.getRates().stream()
					.filter(x -> x.getCode().equals(code))
					.findFirst();
	}
	
	public Map<LocalDate, Optional<CurrencyRate>> getCurrencyRateBeetweenDates(String code, LocalDate startDate, LocalDate endDate) {
		Map<LocalDate, Optional<CurrencyRate>> map = new HashMap<>();
		List<CurrencyRateTable> table = getTable(startDate, endDate);
		for (var c : table) {
			map.put(c.getEffectiveDate(), c.getRates().stream()
					.filter(x -> x.getCode().equals(code))
					.findFirst());
		}
		return map;
		
	}
	
	public CurrencyRateTable getTable() {
		try {
			String content = getJson(new URL(ApiUrl));
			ObjectMapper om = new ObjectMapper();
			CurrencyRateTable[] table = om.readValue(content, CurrencyRateTable[].class);
			return table[0];
		} catch (IOException e) {
			
		}
		return null;
	}
	
	public CurrencyRateTable getTable(LocalDate date) {
		try {
			String content = getJson(new URL(ApiUrl + date.format(DateTimeFormatter.ISO_LOCAL_DATE)));
			ObjectMapper om = new ObjectMapper();
			CurrencyRateTable[] table = om.readValue(content, CurrencyRateTable[].class);
			return table[0];
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public Optional<CurrencyRate> getCurrencyRate(String code) {
		CurrencyRateTable table = getTable();
		
		return table.getRates().stream()
		.filter(c -> c.getCode().equals(code))
		.findFirst();
	}
	
	public List<CurrencyRateTable> getTable(LocalDate startDate, LocalDate endDate) {
		try {
			String content = getJson(new URL(ApiUrl + startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) +
					"/" + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
			ObjectMapper om = new ObjectMapper();
			CurrencyRateTable[] table = om.readValue(content, CurrencyRateTable[].class);
			return (List<CurrencyRateTable>) Arrays.asList(table);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	
	private String getJson(URL url) {
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(GET);
			con.setRequestProperty(ContentType, ApplicationJson);
			int status = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputline;
			StringBuffer content = new StringBuffer();
			while ((inputline = in.readLine()) != null) 
				content.append(inputline);
			in.close();
			con.disconnect();
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
}
