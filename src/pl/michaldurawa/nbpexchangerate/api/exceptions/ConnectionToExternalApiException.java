package pl.michaldurawa.nbpexchangerate.api.exceptions;

import java.io.IOException;

public class ConnectionToExternalApiException extends IOException {

	public ConnectionToExternalApiException() {
		super();
	}

	public ConnectionToExternalApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionToExternalApiException(String message) {
		super(message);
	}

	public ConnectionToExternalApiException(Throwable cause) {
		super(cause);
	}
	
}
