package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import pl.michaldurawa.nbpexchangerate.api.service.ContentNotFoundException;
import pl.michaldurawa.nbpexchangerate.api.service.HttpRequestService;

public class NbpResponseReciver implements ResponseReciver {
	private static final int DyasBeforeDate = 93;
	private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private LocalDate date;
	private final CurrencyCode code;
	private final HttpRequestService service;
	
	
	public NbpResponseReciver(LocalDate date, CurrencyCode code,
			HttpRequestService service) {
		this.date = date;
		this.code = code;
		this.service = service;
	}

	@Override
	public BigDecimal getExchangeRate() {
		return reciveJson();
	}
	
	private BigDecimal reciveJson() {
		JSONObject response = getResponse();
		JSONArray rates = response.getJSONArray("rates");
		JSONObject rate = rates.getJSONObject(0);
		return rate.getBigDecimal("mid");
	}
	
	private JSONObject getResponse() {
		 try {
			return service.getResponse(generateURL());
		} catch (ContentNotFoundException e) {
				return service.getResponse(generateNotFoundURL());
		}
	}
	
	private String generateURL() {
		if (IsDateLaterAsToday(date)) {
			return API_URL + code.toString() + "/" + 
				   LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		}
		if (IsDateBeforeApiHisotry()) {
			return API_URL + code.toString() + "/" + 
					   LocalDate.of(2002, 1, 2).format(DateTimeFormatter.ISO_LOCAL_DATE);
		}
		return API_URL + code.toString() + "/" + 
				date.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	private String generateNotFoundURL() {
		return API_URL + code.toString() + "/" + 
				date.minusDays(DyasBeforeDate).format(DateTimeFormatter.ISO_LOCAL_DATE) + 
				"/" + date.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	private boolean IsDateBeforeApiHisotry() {
		return date.isBefore(LocalDate.of(2002, 1, 2));
	}

	private boolean IsDateLaterAsToday(LocalDate date) {
		return date.isAfter(LocalDate.now());
	}
	

}
