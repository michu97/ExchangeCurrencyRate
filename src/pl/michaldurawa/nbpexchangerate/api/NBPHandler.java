package pl.michaldurawa.nbpexchangerate.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class NBPHandler {
//	TODO change nbpHandler -> ApiHandler
	private static final String GET = "GET";
	private static final String ContentType = "Content-Type";
	private static final String ApplicationJson = "application/json";
	private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private static final Logger LOGGER = Logger.getLogger(NBPHandler.class.getName());
	
	public String getContent(LocalDate date, BigDecimal ammount, CurrencyCode code) {
		if (isDateLaterThanToday(date)) {
			date = LocalDate.now();
		}
		try {
			HttpURLConnection connection = getConnection(generateURL(date, ammount, code));
			int responseCode = connection.getResponseCode();
			if (responseCode == 404) {
				return getContent(date.minusDays(1), ammount, code);
			} 
			return getResponseContent(connection);
		} catch (IOException e) {
			LOGGER.info("Connection to external Api failed");
			throw new RuntimeException(e);
		}
	}

	private String getResponseContent(HttpURLConnection connection) {
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
			LOGGER.info("Cannot download content");
			throw new RuntimeException(e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isDateLaterThanToday(LocalDate date) {
		return date.compareTo(LocalDate.now()) > 0;
	}
	
	private HttpURLConnection getConnection(URL url) {
			try {
				HttpURLConnection connection;
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(GET);
				connection.setRequestProperty(ContentType, ApplicationJson);
				return connection;
			} catch (IOException e) {
				LOGGER.info("Open connection throw IOException and I dont't know how to deal with it");
				throw new RuntimeException(e);
			}
	}
	
	private URL generateURL(LocalDate date, BigDecimal ammount, CurrencyCode code) {
		String url = API_URL + code.name() + "/" +
					 date.format(DateTimeFormatter.ISO_LOCAL_DATE);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			System.out.println("TO ja");
			e.printStackTrace();
			return null;
		}
	}
}