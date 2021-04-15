package pl.michaldurawa.nbpexchangerate.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
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
	private static final String ApiUrlC = "http://api.nbp.pl/api/exchangerates/tables/C/";
	
	public Map<String, BigDecimal> getTableFromPLN(LocalDate date, BigDecimal pln) {
		Map<String, BigDecimal> amountMap = new HashMap<>();
		BuySellTableRate buySellTable = getBuySellTable(date);
		List<BuySellRate> rates = buySellTable.getRates();
		for (var r : rates) {
			amountMap.put(r.getCode(), pln.divide(r.getBid(), 2, RoundingMode.UP));
		}
		return amountMap;
	}
	
	public Optional<BigDecimal> getAmountFromPLN(LocalDate date, BigDecimal pln, String code) {
		Optional<BuySellRate> value = getCurrencyBuySellRateByCodAndDate(code, date);
		return value.map(x -> pln.divide(x.getBid(), 2, RoundingMode.UP));
	}
	
	public Optional<BigDecimal> getAmountFromPLNByAvrageRate(LocalDate date, BigDecimal pln, String code) {
		Optional<CurrencyAvrageRate> value = getAvrageCurrencyRateByCodAndDate(code, date);
		return value.map(x -> pln.divide(x.getMid(), 2, RoundingMode.UP));
	}
	
	public Optional<BigDecimal> getAmountInPLN(LocalDate date, BigDecimal amount, String code) {
		Optional<BuySellRate> value = getCurrencyBuySellRateByCodAndDate(code, date);
		return value.map(x -> amount.multiply(x.getAsk()).setScale(2, RoundingMode.DOWN));
	}
	
	public Optional<BigDecimal> getAmountInPLNByAvrageRate(LocalDate date, BigDecimal amount, String code) {
		Optional<CurrencyAvrageRate> value = getAvrageCurrencyRateByCodAndDate(code, date);
		return value.map(x -> amount.multiply(x.getMid()).setScale(2, RoundingMode.DOWN));
	}
	
	public Optional<CurrencyAvrageRate> getAvrageCurrencyRateByCodAndDate(String code, LocalDate date) {
			CurrencyAvrageRateTable table = getTable(date);
			return table.getRates().stream()
					.filter(x -> x.getCode().equals(code))
					.findFirst();
	}
	
	public Optional<BuySellRate> getCurrencyBuySellRateByCodAndDate(String code, LocalDate date) {
		BuySellTableRate table = getBuySellTable(date);
		return table.getRates().stream()
				.filter(x -> x.getCode().equals(code))
				.findFirst();
	}
	
	public Map<LocalDate, Optional<CurrencyAvrageRate>> getCurrencyRateBeetweenDates(String code, LocalDate startDate, LocalDate endDate) {
		Map<LocalDate, Optional<CurrencyAvrageRate>> map = new HashMap<>();
		List<CurrencyAvrageRateTable> table = getTable(startDate, endDate);
		for (var c : table) {
			map.put(c.getEffectiveDate(), c.getRates().stream()
					.filter(x -> x.getCode().equals(code))
					.findFirst());
		}
		return map;
	}
	
	public Map<LocalDate, Optional<BuySellRate>> getCurrencyBuySellRateBeetweenDates(String code, LocalDate startDate, LocalDate endDate) {
		Map<LocalDate, Optional<BuySellRate>> map = new HashMap<>();
		List<BuySellTableRate> table = getBuySellTable(startDate, endDate);
		for (var c : table) {
			map.put(c.getEffectiveDate(), c.getRates().stream()
					.filter(x -> x.getCode().equals(code))
					.findFirst());
		}
		return map;
	}
	
	public CurrencyAvrageRateTable getTable() {
		try {
			String content = getJson(new URL(ApiUrl));
			ObjectMapper om = new ObjectMapper();
			CurrencyAvrageRateTable[] table = om.readValue(content, CurrencyAvrageRateTable[].class);
			return table[0];
		} catch (IOException e) {
			throw new RuntimeException("bad response", e);
		}
	}
	
	public BuySellTableRate getBuySellTable() {
		try {
			String content = getJson(new URL(ApiUrlC));
			ObjectMapper om = new ObjectMapper();
			BuySellTableRate[] table = om.readValue(content, BuySellTableRate[].class);
			return table[0];
		} catch (IOException e) {
			throw new RuntimeException("bad response", e);
		}
	}
	
	public CurrencyAvrageRateTable getTable(LocalDate date) {
		try {
			String content = getJson(new URL(ApiUrl + date.format(DateTimeFormatter.ISO_LOCAL_DATE)));
			ObjectMapper om = new ObjectMapper();
			CurrencyAvrageRateTable[] table = om.readValue(content, CurrencyAvrageRateTable[].class);
			return table[0];
		} catch (Exception e) {
			throw new RuntimeException("bad response", e);
		}
	}
	
	public BuySellTableRate getBuySellTable(LocalDate date) {
		try {
			String content = getJson(new URL(ApiUrlC + date.format(DateTimeFormatter.ISO_LOCAL_DATE)));
			ObjectMapper om = new ObjectMapper();
			BuySellTableRate[] table = om.readValue(content, BuySellTableRate[].class);
			return table[0];
		} catch (Exception e) {
			throw new RuntimeException("bad response", e);
		}
	}
	
	public Optional<CurrencyAvrageRate> getCurrencyRate(String code) {
		CurrencyAvrageRateTable table = getTable();
		
		return table.getRates().stream()
		.filter(c -> c.getCode().equals(code))
		.findFirst();
	}
	
	public Optional<BuySellRate> getBuySellRate(String code) {
		BuySellTableRate table = getBuySellTable();
		
		return table.getRates().stream()
		.filter(c -> c.getCode().equals(code))
		.findFirst();
	}
	
	public List<CurrencyAvrageRateTable> getTable(LocalDate startDate, LocalDate endDate) {
		try {
			String content = getJson(new URL(ApiUrl + startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) +
					"/" + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
			ObjectMapper om = new ObjectMapper();
			CurrencyAvrageRateTable[] table = om.readValue(content, CurrencyAvrageRateTable[].class);
			return (List<CurrencyAvrageRateTable>) Arrays.asList(table);
		} catch (Exception e) {
			throw new RuntimeException("bad response", e);
		}
	}
	
	public List<BuySellTableRate> getBuySellTable(LocalDate startDate, LocalDate endDate) {
		try {
			String content = getJson(new URL(ApiUrlC + startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) +
					"/" + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
			ObjectMapper om = new ObjectMapper();
			BuySellTableRate[] table = om.readValue(content, BuySellTableRate[].class);
			return (List<BuySellTableRate>) Arrays.asList(table);
		} catch (Exception e) {
			throw new RuntimeException("bad response", e);
		}
	}
	
	
	private String getJson(URL url) {
		try {
			HttpURLConnection con = setupConnection(url);
			StringBuffer content = getContent(con);
			con.disconnect();
			return content.toString();
		} catch (IOException e) {
			throw new RuntimeException("connection error");
		} 
	}

	private StringBuffer getContent(HttpURLConnection con) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputline;
		StringBuffer content = new StringBuffer();
		while ((inputline = in.readLine()) != null) 
			content.append(inputline);
		in.close();
		return content;
	}

	private HttpURLConnection setupConnection(URL url) throws IOException, ProtocolException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(GET);
		con.setRequestProperty(ContentType, ApplicationJson);
		int code = con.getResponseCode();
		if (code == 404 || code == 400) 
			throw new RuntimeException("bad request");
		return con;
	}

	
}
