package io.cloud4u.acm.exception;

public class ExpiredException extends Exception {

	private static final long serialVersionUID = 2440834737002509384L;
	
	public ExpiredException() {
		super();
	}

	public ExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpiredException(String message) {
		super(message);
	}

	public ExpiredException(Throwable cause) {
		super(cause);
	}

}
