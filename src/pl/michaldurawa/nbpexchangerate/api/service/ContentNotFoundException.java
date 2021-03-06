package pl.michaldurawa.nbpexchangerate.api.service;

public class ContentNotFoundException extends RuntimeException {

	public ContentNotFoundException() {
		super();
	}

	public ContentNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ContentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContentNotFoundException(String message) {
		super(message);
	}

	public ContentNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
