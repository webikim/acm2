package io.cloud4u.lib.acm.exception;

// FIXME: moved to common/lib. remove this later.
public class DuplicateException extends Exception {

	private static final long serialVersionUID = 7485232008238760299L;

	public DuplicateException() {
		super();
	}

	public DuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateException(String message) {
		super(message);
	}

	public DuplicateException(Throwable cause) {
		super(cause);
	}

}
