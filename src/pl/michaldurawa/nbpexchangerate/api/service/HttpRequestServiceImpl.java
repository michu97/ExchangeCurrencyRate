package pl.michaldurawa.nbpexchangerate.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.json.JSONObject;


public class HttpRequestServiceImpl implements HttpRequestService {
	private static final Logger LOGGER = Logger
			.getLogger(HttpRequestService.class.getCanonicalName());
	private static final String GET = "GET";
	private static final String ContentType = "Content-Type";
	private static final String ApplicationJson = "application/json";
	
	@Override
	public JSONObject getResponse(String url) {
		return getContent(url);
	}
	
	JSONObject getContent(String url) {
		return new JSONObject(getJson(url));
	}
	
	private String getJson(String url) {
		BufferedReader in = null;
		try {
			HttpURLConnection connection = getConnection(url);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputline;
			StringBuffer content = new StringBuffer();
			while ((inputline = in.readLine()) != null) {
				content.append(inputline);
			}
			return content.toString();
		} catch (IOException e) {
			LOGGER.info("Connecting to external Api failed");
			throw new RuntimeException("cannot connecting to external api", e);
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
	
	private HttpURLConnection getConnection(String url) throws IOException {
		URL connectingURL = getURL(url);
			HttpURLConnection connection =(HttpURLConnection) connectingURL.openConnection();
			connection.setRequestMethod(GET);
			connection.setRequestProperty(ContentType, ApplicationJson);
			int reasponseCode = connection.getResponseCode();
			System.out.println(reasponseCode);
			if (reasponseCode == 404) {
				throw new ContentNotFoundException("Data not avaliable");
			}
			return connection;
	}
	
	private URL getURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			LOGGER.info("Cannot parse URL");
			throw new IllegalArgumentException(e);
		}
	}

}
