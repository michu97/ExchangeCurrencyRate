package pl.michaldurawa.nbpexchangerate.api.service;

import java.io.IOException;

import org.json.JSONObject;

public class FakeHttpRequestService implements HttpRequestService {
	private final boolean isInternetConnection;
	
	
	
	public FakeHttpRequestService(boolean isInternetConnection) {
		this.isInternetConnection = isInternetConnection;
	}



	@Override
	public JSONObject getResponse(String url) {
		if (!isInternetConnection)
			throw new RuntimeException("cannot connecting to external api",
					new IOException());
		return null;
	}

}
