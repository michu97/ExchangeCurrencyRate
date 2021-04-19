package pl.michaldurawa.nbpexchangerate.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NbpUrlGenerator implements ApiUrlGenerator {
	public static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";
	
	@Override
	public URL getURL(LocalDate date, CurrencyCode code) {
		String url = API_URL + code.name() + "/" + 
					 date.format(DateTimeFormatter.ISO_LOCAL_DATE);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

}
