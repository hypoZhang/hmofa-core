package com.hmofa.core.exception;

public class IOException extends BaseRuntimeException {

	private static final long serialVersionUID = 1331787611308168333L;

	public IOException(Throwable cause) {
		super(cause);
	}

	public IOException(String message) {
		super(message);
	}

	public IOException(String message, Throwable cause) {
		super(message, cause);
	}
}
