package com.hmofa.core.lang;

public interface LastModified {
	
	long getLastModified();

	void setLastModified(long lastModified);

	boolean isModified(long lastModified);
}
