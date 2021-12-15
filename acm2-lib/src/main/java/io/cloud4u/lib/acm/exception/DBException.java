package io.cloud4u.lib.acm.exception;

// FIXME: moved to common/lib. remove this later.
public class DBException extends Exception {

	private static final long serialVersionUID = -6499627054131881217L;

	public DBException() {
		super();
	}

	public DBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message) {
		super(message);
	}

	public DBException(Throwable cause) {
		super(cause);
	}
	
}
