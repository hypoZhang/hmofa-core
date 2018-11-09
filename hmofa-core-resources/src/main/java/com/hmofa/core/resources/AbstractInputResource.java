package com.hmofa.core.resources;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import com.hmofa.core.exception.CharsetException;
import com.hmofa.core.exception.ExceptionHelper;
import com.hmofa.core.exception.IOException;
import com.hmofa.core.lang.coder.StringCoder;

public abstract class AbstractInputResource implements InputResource {

	public abstract URL getURL() throws IOException;

	public abstract long lastModified();
	
	public abstract long contentLength() throws IOException;

	public abstract Resource createRelative(String relative) throws IOException;

	public abstract Resource getParentResource();
	
	public abstract String getAbsolutePath();

	public abstract String getResourceName();

	public abstract String getDescription();

	public abstract boolean exists();
	
	public abstract InputStream getResourceAsInputStream() throws IOException;
	
	
	public URI getURI() throws IOException {
		URL ul = getURL();
		try {
			return ul.toURI();
		} catch (URISyntaxException e) {
			throw new IOException(getDescription(), e);
		}
	}
	
	public String getResourceType() throws IOException {
		if(type == null) {
			guessType();
		}
		return type;
	}

	private void guessType() {
		InputStream in = null;
		try {
			in = getResourceAsInputStream();
			if (in == null)
				return;
			type = URLConnection.guessContentTypeFromStream(new BufferedInputStream(in));
			if (type == null)
				type = URLConnection.guessContentTypeFromName(getURL().toString());
		} catch (java.io.IOException e) {
			throw ExceptionHelper.wrap(e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (java.io.IOException e) {
				ExceptionHelper.addSuppressed(e);
			}
		}
	}
	
	private String type;

	public  boolean isOpenable() {
		return true;
	}

	public boolean isExclusiveAccess() {
		return false;
	}

	public Charset getCharset() {
		return Charset.defaultCharset();
	}

	public Reader getResourceAsReader() throws IOException {
		return getResourceAsReader(getCharset());
	}

	public Reader getResourceAsReader(String charEncoding) throws IOException, CharsetException {
		Charset charset = StringCoder.lookupCharset(charEncoding);
		return getResourceAsReader(charset);
	}

	public Reader getResourceAsReader(Charset charset) throws IOException {
		InputStream in = getResourceAsInputStream();
		return new BufferedReader(new InputStreamReader(in, charset));
	}

}
