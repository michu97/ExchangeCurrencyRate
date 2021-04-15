package pl.michaldurawa.nbpexchangerate.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;

public class NBPHandler {
	private static final String GET = "GET";
	private static final String ContentType = "Content-Type";
	private static final String ApplicationJson = "application/json";
	
	public String getContent(URL url) throws ConnectionToExternalApiException, FileNotFoundException {
		HttpURLConnection connection = getConnection(url);
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
				int responseCode = connection.getResponseCode();
				System.out.println(responseCode);
				return connection;
			} catch (IOException e) {
				throw new ConnectionToExternalApiException("Connection to external api failed", e);
			}
		
	}
}