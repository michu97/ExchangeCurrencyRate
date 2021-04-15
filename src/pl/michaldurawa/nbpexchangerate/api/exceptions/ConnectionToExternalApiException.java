package pl.michaldurawa.nbpexchangerate.api.exceptions;

public class ConnectionToExternalApiException extends Exception {

	public ConnectionToExternalApiException() {
		super();
	}

	public ConnectionToExternalApiException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
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
