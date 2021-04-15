package pl.michaldurawa.nbpexchangerate.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;

public class NBPHandler {
	private static final String GET = "GET";
	private static final String ContentType = "Content-Type";
	private static final String ApplicationJson = "application/json";
	private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";
	
	
	
	public String getContent(LocalDate date, BigDecimal ammount, CurrencyCode code) throws ConnectionToExternalApiException, FileNotFoundException {
		HttpURLConnection connection = getConnection(generateURL(date, ammount, code));
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputline;
			StringBuffer content = new StringBuffer();
			while ((inputline = in.readLine()) != null) {
				content.append(inputline);
			}
			return content.toString();
		} catch (IOException e) {
			throw new FileNotFoundException("Not found - data not available");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private HttpURLConnection getConnection(URL url) throws ConnectionToExternalApiException {
			HttpURLConnection connection;
			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(GET);
				connection.setRequestProperty(ContentType, ApplicationJson);
				return connection;
			} catch (IOException e) {
				throw new ConnectionToExternalApiException("Connection to external api failed", e);
			}
		
	}
	
	private URL generateURL(LocalDate date, BigDecimal ammount, CurrencyCode code) {
		String url = API_URL + code.name() + "/" + date.format(DateTimeFormatter.ISO_LOCAL_DATE);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}