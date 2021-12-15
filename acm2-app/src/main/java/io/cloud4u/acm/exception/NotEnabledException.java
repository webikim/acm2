package io.cloud4u.acm.exception;

public class NotEnabledException extends Exception {

	private static final long serialVersionUID = -6396723598099266728L;

	public NotEnabledException() {
		super();
	}

	public NotEnabledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotEnabledException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnabledException(String message) {
		super(message);
	}

	public NotEnabledException(Throwable cause) {
		super(cause);
	}

}
