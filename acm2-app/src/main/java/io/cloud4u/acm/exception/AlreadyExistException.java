package io.cloud4u.acm.exception;

public class AlreadyExistException extends Exception {

	private static final long serialVersionUID = 1809818330412362907L;
	
	public AlreadyExistException() {
		super();
	}

	public AlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyExistException(String message) {
		super(message);
	}

	public AlreadyExistException(Throwable cause) {
		super(cause);
	}

}
