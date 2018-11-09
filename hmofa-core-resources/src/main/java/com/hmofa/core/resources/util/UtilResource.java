package com.hmofa.core.resources.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.hmofa.core.exception.IOException;
import com.hmofa.core.lang.exception.Assert;

public class UtilResource {
	
	public static boolean isJarZipURL(URL url) {
		Assert.notNull(url, "Resource URL must not be null");
		String protocol = url.getProtocol();
		return urlZip.contains(protocol == null ? null : protocol.toLowerCase()) || isJarUrl0(url, protocol);
	}

	private static boolean isJarUrl0(URL url, String protocol) {
		int sepIndex = url.getPath().indexOf(JAR_URL_SEPARATOR);
		return URL_PROTOCOL_CODE_SOURCE.equalsIgnoreCase(protocol) && sepIndex != -1;
	}
	
	public static URL extractJarFileURL(URL jarUrl) throws IOException {
		String urlFile = jarUrl.getFile();
		int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
		if (separatorIndex == -1)
			return jarUrl;
		String jarFile = urlFile.substring(0, separatorIndex);
		try {
			return new URL(jarFile);
		} catch (MalformedURLException ex) {
			try {
				if (!jarFile.startsWith("/"))
					jarFile = "/" + jarFile;
				return new URL("file:" + jarFile);
			} catch (MalformedURLException e) {
				throw new IOException(e);
			}
		}
	}
	
	public static String resolvePathSeparator(String filePath) {
		return filePath != null ? filePath.replace("\\", separator).replaceAll("//", separator) : filePath;
	}
	
	private static final String separator = "/";  // 默认名称分隔符
	private static final String pathSeparator = ";";   // 默认路径分隔符
	
	/**
	 * <p>Discription:[取类路径，并与输入路径整合]</p>
	 * @param filePath
	 * @param clazz
	 * @return
	 * @author hypo zhang  2018-06-08
	 */
	public static String resolveName(String filePath, Class<?> clazz) {
		if (filePath == null && clazz == null)
			return null;
		
		String classpath = null;
		if (clazz != null) {
			Class<?> c = clazz;
			while (c.isArray())
				c = c.getComponentType();
			String baseName = c.getName();
			int index = baseName.lastIndexOf('.');
			if (index != -1)
				classpath = baseName.substring(0, index).replace('.', '/').concat("/");
		}
		String filename = null;
		if (filePath != null) {
			if (filePath.startsWith("/")) {
				filename = filePath.substring(1);
			} else {
				filename = filePath;
			}
		}
		
		String path = null;
		if (classpath != null && filename != null) {
			path = classpath.concat(filename);
		} else if (classpath == null) {
			path = filename;
		} else if(filename == null) {
			path = classpath;
		}
		return resolvePathSeparator(path);
    }
	
	public static String cleanPath(String path) {
		if (isEmpty(path))
			return null;
		String pathToUse = path.replace("\\", "/");
		int prefixIndex = pathToUse.indexOf(":");
		String prefix = "";
		if (prefixIndex != -1) {
			prefix = pathToUse.substring(0, prefixIndex + 1);
			pathToUse = pathToUse.substring(prefixIndex + 1);
		}
		if (pathToUse.startsWith("/")) {
			prefix = prefix + "/";
			pathToUse = pathToUse.substring(1);
		}
		String pathArray[] = delimitedListToStringArray(pathToUse, "/");
		List<String> pathElements = new LinkedList<String>();
		int tops = 0;
		for (int i = pathArray.length - 1; i >= 0; i--) {
			String element = pathArray[i];
			if (".".equals(element))
				continue;
			if ("..".equals(element)) {
				tops++;
				continue;
			}
			if (tops > 0)
				tops--;
			else
				pathElements.add(0, element);
		}
		for (int i = 0; i < tops; i++)
			pathElements.add(0, "..");
		return prefix + collectionToDelimitedString(pathElements, "/");
	}

	public static String[] delimitedListToStringArray(String str, String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}

	public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
		if (str == null)
			return new String[0];
		if (delimiter == null)
			return (new String[] { str });
		List<String> result = new ArrayList<String>();
		if ("".equals(delimiter)) {
			for (int i = 0; i < str.length(); i++)
				result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
		} else {
			int pos = 0;
			for (int delPos = 0; (delPos = str.indexOf(delimiter, pos)) != -1;) {
				result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length())
				result.add(deleteAny(str.substring(pos), charsToDelete));
		}
		return toStringArray(result);
	}
	
	public static String collectionToDelimitedString(Collection<?> coll, String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {
		if (coll == null || coll.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		Iterator<?> it = coll.iterator();
		do {
			if (!it.hasNext())
				break;
			sb.append(prefix).append(it.next()).append(suffix);
			if (it.hasNext())
				sb.append(delim);
		} while (true);
		return sb.toString();
	}
	
	private static String deleteAny(String inString, String charsToDelete) {
		if (isEmpty(inString) || isEmpty(charsToDelete))
			return inString;
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1)
				out.append(c);
		}
		return out.toString();
	}
	
	///////////////////////////////////////
		
	private static final String replace(String inString, CharSequence target, CharSequence replacement) {
		if (isEmpty(inString) || isEmpty(target) || replacement == null)
			return inString;
		return inString.replace(target, replacement);
	}
	
	private static final boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}
	
	private static String[] toStringArray(Collection<String> collection) {
		if (collection == null)
			return EMPTY_STRING_ARRAY;
		return collection.toArray(new String[collection.size()]);
	}
	
	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	public static final String CLASSPATH_URL_PREFIX = "classpath:";			// 类装载路径前缀
	public static final String FILE_URL_PREFIX = "file:";					// 文件系统路径 前缀
	
	public static final String JAR_URL_SEPARATOR = "!/";					// jar包路径 分隔符

	
	public static final String URL_PROTOCOL_JAR = "jar";					// Jar包URL
	public static final String URL_PROTOCOL_ZIP = "zip";					// 压缩包URL
	public static final String URL_PROTOCOL_VFSZIP = "vfszip";				// 
	public static final String URL_PROTOCOL_WSJAR = "wsjar";				// 
	public static final String URL_PROTOCOL_CODE_SOURCE = "code-source";	// 
	
	public static final String URL_PROTOCOL_FILE = "file";					// 文件系统URL

	
	private static final Set<String> urlZip = new HashSet<String>();
	static {
		urlZip.add(URL_PROTOCOL_JAR);
		urlZip.add(URL_PROTOCOL_JAR.toUpperCase());
		urlZip.add(URL_PROTOCOL_ZIP);
		urlZip.add(URL_PROTOCOL_ZIP.toUpperCase());
		urlZip.add(URL_PROTOCOL_VFSZIP);
		urlZip.add(URL_PROTOCOL_VFSZIP.toUpperCase());
		urlZip.add(URL_PROTOCOL_WSJAR);
		urlZip.add(URL_PROTOCOL_WSJAR.toUpperCase());
	}
	
	


	public static File getFile(String resourceLocation) throws IOException {
		Assert.notNull(resourceLocation, "Resource location must not be null");
		URL url = toURL(resourceLocation);
		return getFile(url);
	}

	public static File getFile(URL resourceUrl) throws IOException {
		return getFile(resourceUrl, "URL");
	}

	/**
	 * <p>Discription:[根据URL 取文件系统 File]</p>
	 * @param resourceUrl
	 * @param description  资源描述信息
	 * @return
	 * @throws IOException
	 * @author zhanghaibo3  2015-12-9
	 */
	public static File getFile(URL resourceUrl, String description) throws IOException {
		Assert.notNull(resourceUrl, "Resource URL must not be null");
		if (!"file".equals(resourceUrl.getProtocol()))
			throw new IOException(description + " cannot be resolved to absolute file path "
					+ "because it does not reside in the file system: " + resourceUrl);
		return new File(toURI(resourceUrl).getSchemeSpecificPart());
	}

	
	public static File getFile(URI resourceUri) throws IOException {
		return getFile(resourceUri, "URI");
	}

	
	/**
	 * <p>Discription:[根据URI 取文件系统 File]</p>
	 * @param resourceUri 
	 * @param description  资源描述信息
	 * @return
	 * @throws IOException
	 * @author zhanghaibo3  2015-12-9
	 */
	public static File getFile(URI resourceUri, String description) throws IOException {
		Assert.notNull(resourceUri, "Resource URI must not be null");
		if (!"file".equals(resourceUri.getScheme()))
			throw new IOException(description + " cannot be resolved to absolute file path "
					+ "because it does not reside in the file system: " + resourceUri);
		return new File(resourceUri.getSchemeSpecificPart());
	}
		
	public static URL toURL(String resourceLocation) throws IOException {
		Assert.notNull(resourceLocation, "Resource location must not be null");
		if (resourceLocation.startsWith("classpath:")) {
			String path = resourceLocation.substring("classpath:".length());
			URL url = Thread.currentThread().getContextClassLoader().getResource(path);
			if (url == null) {
				String description = "class path resource [" + path + "]";
				throw new IOException(description + " cannot be resolved to URL because it does not exist");
			}
			return url;
		}
		try {
			return new URL(resourceLocation);
		} catch (MalformedURLException ex) {
			try {
				return (new File(resourceLocation)).toURI().toURL();
			} catch (MalformedURLException ex2) {
				throw new IOException("Resource location [" + resourceLocation + "] is neither a URL not a well-formed file path");
			}
		}
	}
	
	/**
	 * <p>Discription:[URL 转 URI]</p>
	 * @param url
	 * @return
	 * @throws IOException
	 * @author zhanghaibo3  2015-12-9
	 */
	public static URI toURI(URL url) throws IOException {
		Assert.notNull(url, "Resource URL must not be null");
		return toURI(url.toString());
	}

	/**
	 * <p>Discription:[路径字符串转 URI]</p>
	 * @param location
	 * @return
	 * @throws IOException
	 * @author zhanghaibo3  2015-12-9
	 */
	public static URI toURI(String location) throws IOException {
		try {
			Assert.notNull(location, "Resource location to URI must not be null");
//			String uri = URLEncoder.encode(location, StringCoder.UCS2BE);
			String uri = replace(location, " ", "%20");
			return new URI(uri);
		} catch (URISyntaxException e) {
			throw new IOException(e);
//		} catch (UnsupportedEncodingException e) {
//			throw new IOException(e);
		}
	}
		
	
	//////////////////////////////////////////////////////////////////

}
