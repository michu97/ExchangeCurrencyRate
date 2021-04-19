package pl.michaldurawa.nbpexchangerate.api.service;

import org.json.JSONObject;

public interface HttpRequestService {
	JSONObject getResponse(String url);
}
