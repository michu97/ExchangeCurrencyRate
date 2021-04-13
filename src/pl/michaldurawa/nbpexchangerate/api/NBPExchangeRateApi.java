package pl.michaldurawa.nbpexchangerate.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;


public class NBPExchangeRateApi {
	
	private static final String GET = "GET";
	private static final String ContentType = "Content-Type";
	private static final String ApplicationJson = "application/json";
	private static final String ApiUrl = "http://api.nbp.pl/api/exchangerates/tables/";
	
	public CurrencyRateTable getTable() {
		try {
			String content = getJson(new URL(ApiUrl + "A"));
			ObjectMapper om = new ObjectMapper();
			CurrencyRateTable[] table = om.readValue(content, CurrencyRateTable[].class);
			return table[0];
		} catch (Exception e) {
		}
		return null;
	}
	
	public String getExchangeRateTable() {
		try {
			URL url = new URL(ApiUrl + "B");
			return getJson(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return "";
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
