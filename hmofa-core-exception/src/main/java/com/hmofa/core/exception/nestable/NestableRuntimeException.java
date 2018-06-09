package com.hmofa.core.exception.nestable;

import java.io.PrintStream;
import java.io.PrintWriter;

public class NestableRuntimeException extends RuntimeException implements Nestable {
	
	public NestableRuntimeException() {
		_flddelegate = new NestableDelegate(this);
		cause = null;
		detailMessages = null;
	}

	public NestableRuntimeException(String message) {
		_flddelegate = new NestableDelegate(this);
		cause = null;
		detailMessages = message;
	}

	public NestableRuntimeException(Throwable cause) {
		_flddelegate = new NestableDelegate(this);
		this.cause = null;
		this.cause = cause;
		detailMessages = null;
	}

	public NestableRuntimeException(String message, Throwable cause) {
		_flddelegate = new NestableDelegate(this);
		this.cause = null;
		this.cause = cause;
		detailMessages = null;
		detailMessages = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public String getMessage() {
		if(NexceptionUtils.isNotWhitespace(detailMessages))
			return detailMessages;
		if (NexceptionUtils.isNotWhitespace(super.getMessage()))
			return super.getMessage();
		if (cause != null)
			return cause.toString();
		return null;
	}

	public String getMessage(int index) {
		if (index == 0)
			return detailMessages;
		return _flddelegate.getMessage(index);
	}

	public String[] getMessages() {
		return _flddelegate.getMessages();
	}

	public Throwable getThrowable(int index) {
		return _flddelegate.getThrowable(index);
	}

	public int getThrowableCount() {
		return _flddelegate.getThrowableCount();
	}

	public Throwable[] getThrowables() {
		return _flddelegate.getThrowables();
	}

	public int indexOfThrowable(Class<?> type) {
		return _flddelegate.indexOfThrowable(type, 0);
	}

	public int indexOfThrowable(Class<?> type, int fromIndex) {
		return _flddelegate.indexOfThrowable(type, fromIndex);
	}

	public void printStackTrace() {
		_flddelegate.printStackTrace();
	}

	public void printStackTrace(PrintStream out) {
		_flddelegate.printStackTrace(out);
	}

	public void printStackTrace(PrintWriter out) {
		_flddelegate.printStackTrace(out);
	}

	public final void printPartialStackTrace(PrintWriter out) {
		super.printStackTrace(out);
	}
	
	public String toNativeString() {
		return getClass().getName() + "@" + Integer.toHexString(super.hashCode());
	}
	
	protected NestableDelegate _flddelegate;
	private static final long serialVersionUID = 1L;
	private Throwable cause;
	private String detailMessages; 
	
}
