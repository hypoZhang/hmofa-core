package com.hmofa.core.exception;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hmofa.core.exception.nestable.NestableRuntimeException;

public class BaseRuntimeException extends NestableRuntimeException {

	/***/
	private static final long serialVersionUID = 7640705754888009960L;

	public BaseRuntimeException() {
		super();
	}
	
	public BaseRuntimeException(String message) {
		super(message);
	}

	public BaseRuntimeException(Throwable cause) {
		this(cause != null ? cause.getMessage() : null, cause);
	}

	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public Object getAttribute(String name) {
		return attributes.get(name);
	}
		
	public BaseRuntimeException addAttribute(String name, Object value) {
		attributes.put(name, value);
		return this;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = attributes.keySet().iterator();
		while (iter.hasNext()) {
			String name = (String) iter.next();
			Object value = attributes.get(name);
			sb.append("[").append(name).append("] = ").append(value).append("\n");
		}
		sb.append(super.toString());
		return sb.toString();
	}
	
	private Map<String, Object> attributes = new LinkedHashMap<String, Object>();
	
}
