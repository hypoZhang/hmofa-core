package com.hmofa.core.resources.input;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.hmofa.core.exception.BaseRuntimeException;
import com.hmofa.core.exception.ExceptionHelper;
import com.hmofa.core.exception.IOException;
import com.hmofa.core.lang.exception.Assert;
import com.hmofa.core.resources.AbstractInputResource;
import com.hmofa.core.resources.DirectoryResource;
import com.hmofa.core.resources.Resource;
import com.hmofa.core.resources.util.UtilResource;

/*
   *      表述的是  类的加载路径 为根
 *  1、ClassPathResource(String relative) 表示当前线程  类加载器 为根
 *  2、ClassPathResource(String relative, Class<?> clazz) 以 clazz 的类加载器为根，clazz表示的路径为 当前路径根。如果clazz为空，则同（1）
 *  3、ClassPathResource(String relative, ClassLoader classLoader)  以指定的类加载器为根
 * 
 */
public class ClassPathResource extends AbstractInputResource implements DirectoryResource {

	public ClassPathResource(String relative) {
		this(relative, (ClassLoader) null);
	}

	/**
	 * 以 clazz 为相对路径
	 * @param relative
	 * @param clazz
	 */
	public ClassPathResource(String relative, Class<?> clazz) {
		String pathToUse = UtilResource.resolveName(relative, clazz);
		String fullrelative = UtilResource.cleanPath(pathToUse);
				
		this.claxx = clazz;
		this.original = relative;
		this.relative = fullrelative;
		
		
		URL classUrl = clazz == null ? null : clazz.getProtectionDomain().getCodeSource().getLocation();
		this.jarUrl = classUrl == null ? null : UtilResource.isJarZipURL(classUrl) ? classUrl : null;
		this.classLoader = clazz == null ? Thread.currentThread().getContextClassLoader() : clazz.getClassLoader();
		
		Assert.notNull(fullrelative, getDescription(), "must not be null");
	}

	public ClassPathResource(String relative, ClassLoader classLoader) {
		String pathToUse = UtilResource.cleanPath(relative);
		String fullrelative = UtilResource.resolveName(pathToUse, null);
		
		this.original = relative;
		this.relative = fullrelative;
		this.classLoader = classLoader == null ? Thread.currentThread().getContextClassLoader() : classLoader;
		
		Assert.notNull(relative, getDescription(), "must not be null");
	}

	private Class<?> claxx;
	private String original;
	
	private String relative;			// 包的完整路径
	private ClassLoader classLoader;    // 类加载器下的包
	private String resourceName; 		// 资源名称 (不含路径)
	private String description;			// 资源的描述信息  (通常用于异常显示信息)
	
	private URL url;
	private URL jarUrl;

	public ClassLoader getClassLoader() {
		return classLoader;
	}
	
	public String getPath() {
		return original;
	}

	public String getRelative() {
		return relative;
	}

	public URL getJarURL() {
		if (jarUrl == null)
			jarUrl = extractJarFileURL();
		return jarUrl;
	}
	
	public boolean isCompressedPackage() {
		URL ul = getJarURL();
		return ul != null;
	}

	private URL extractJarFileURL() {
		URL url = getResource();
		if (url != null && UtilResource.isJarZipURL(url)) {
			URL actualUrl = UtilResource.extractJarFileURL(url);
			return actualUrl;
		}
		return null;
	}
	
	private URL getResource() {
		if (url == null)
			url = getClassLoader().getResource(getRelative());
		return url;
	}
	
	public boolean exists() {
		URL ul = getResource();
		return ul != null;
	}
	
	public  boolean isOpenable() {
		return isDirectory() || isExclusiveAccess() ? false : true;
	}
	
	public boolean isExclusiveAccess() {
		if (isCompressedPackage())
			return false;
		FileLock fileLock = null;
		FileChannel channel = null;
		InputStream fis = null;
		try {
			fis = this.getResourceAsInputStream();
			if (fis instanceof FileInputStream) {
				channel = ((FileInputStream) fis).getChannel();
				fileLock = channel.tryLock(0, Long.MAX_VALUE, false);
				if (fileLock != null && fileLock.isValid())
					return true;
			}
			return false;
		} catch (java.io.IOException e) {
			ExceptionHelper.wrap(e); // 如果 finally 未有异常则会忽略，否则一起抛出
			return false;
		} finally {
			BaseRuntimeException ex = null;
			try {
				fileLock.release();
			} catch (java.io.IOException e) {
				ex = ExceptionHelper.addSuppressed(e);
			}
			try {
				channel.close();
			} catch (java.io.IOException e) {
				ex = ExceptionHelper.addSuppressed(e);
			}
			try {
				fis.close();
			} catch (java.io.IOException e) {
				ex = ExceptionHelper.addSuppressed(e);
			}
			if (ex != null)
				throw ex; // 有异常抛出
		}
	}
	
	public URL getURL() throws IOException {
		if (url == null)
			getResource();
		if (url == null) {
			String error = this.claxx == null ? "资源不存在。确认是否为包完整路径" : "资源不存在。指定类下资源，请使用相对路径。";
			throw new IOException(error).addAttribute("description", getDescription());
		}
		return url;
	}

	public long lastModified() {
		long lastModified = 0;
		if (isCompressedPackage()) {
			JarEntry jarEntry = getJarEntry();
			lastModified = jarEntry == null ? 0 : jarEntry.getTime();
		} else {
			URL ul = getResource();
			if (ul != null)
				lastModified = new File(ul.getPath()).lastModified();
		}
		return lastModified;
	}
	
	public long contentLength() throws IOException {
		long length = 0;
		if (isCompressedPackage()) {
			JarEntry jarEntry = getJarEntry();
			length = jarEntry == null ? 0 : jarEntry.getSize();
		} else {
			URL ul = getResource();
			if (ul != null)
				length = new File(ul.getPath()).length();
		}
		return length;
	}
	
	public Resource createRelative(String relative) throws IOException {
		String parent = new File(this.relative).getParent();
		String newResource = "/" + parent + "/" + relative;
		return new ClassPathResource(newResource, classLoader);
	}

	public String getResourceName() {
		if (resourceName == null)
			resourceName = new File(getRelative()).getName();
		return resourceName;
	}
	
	public Resource getParentResource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAbsolutePath() {
		URL ul = getResource();
		if (ul != null)
			return new File(ul.getPath()).getAbsolutePath();
		return null;
	}

	public String getDescription() {
		if (description == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("class path resource [").append(original).append("]");
			sb.append(" -> [").append(getRelative()).append("] ");
			description = sb.toString();
		}
		return description;
	}

	public InputStream getResourceAsInputStream() throws IOException {
		InputStream input = classLoader.getResourceAsStream(getRelative());
		if (input == null)
			throw new IOException(getDescription() + " cannot be opened because it does not exist");
		return input;
	}

	
	public boolean isDirectory() {
		if (isCompressedPackage()) {
			JarEntry entry = getJarEntry();
			return entry.isDirectory();
		} else {
			URL ul = getResource();
			if (ul != null)
				return new File(ul.getPath()).isDirectory();
		}
		return false;
	}

	public List<String> list() {
		List<String> list = new ArrayList<String>();
		if(isDirectory()) {
			
		}
		if (isCompressedPackage()) {
			String pathPrefix = new File(getRelative()).getParent();
			pathPrefix = pathPrefix + "/";
			Map<String, JarEntry> entryMap = getJarEntryMap(pathPrefix);
			Iterator<String> it = entryMap.keySet().iterator();
			while (it.hasNext()) {
				String path = it.next();
				list.add(new File(path).getName());
			}
		}
		return list;
	}

	public List<File> listFile() {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<String, JarEntry> getJarEntryMap(String pathPrefix) {
		JarFile jf = null;
		String prefix = pathPrefix == null ? null : pathPrefix.replace("\\", "/").replaceAll("//", "/");
		Map<String, JarEntry> entryMap = new HashMap<String, JarEntry>();
		try {
			jf = getJarFile();
			if (jf != null) {
				Enumeration<JarEntry> ejar = jf.entries();
				while (ejar.hasMoreElements()) {
					JarEntry jarEntry = ejar.nextElement();
					String name = jarEntry.getName();
					if(name.equals(prefix) && !jarEntry.isDirectory()) {
						entryMap.put(name, jarEntry);
						break;
					}
					if (isFilter(prefix, jarEntry))
						entryMap.put(name, jarEntry);
				}
			}
		} finally {
			try {
				if (jf != null)
					jf.close();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
		return entryMap;
	}

	private boolean isFilter(String prefix, JarEntry jarEntry) {
		if(prefix == null)
			return true;
		String name = jarEntry.getName();
		if(name.equals(prefix) && jarEntry.isDirectory())
			return false;
		return name.startsWith(prefix);
	}
	
	private JarEntry getJarEntry() {
		Map<String, JarEntry> entryMap = getJarEntryMap(getRelative());
		return entryMap.get(getRelative());
	}

	private JarFile getJarFile() throws IOException {
		try {
			URL jarUrl = extractJarFileURL();
			if (jarUrl == null)
				return null;
			return new JarFile(jarUrl.getPath());
		} catch (java.io.IOException e) {
			throw new IOException(e);
		}
	}

	public List<String> list(FilenameFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<File> listFiles(FilenameFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<File> listFiles(FileFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}



}
