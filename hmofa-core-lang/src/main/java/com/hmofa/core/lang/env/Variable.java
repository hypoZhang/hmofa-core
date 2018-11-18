package com.hmofa.core.lang.env;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

import com.hmofa.core.lang.LastModified;
import com.hmofa.core.lang.converter.Converter;
import com.hmofa.core.lang.utils.UtilCollection;

/**
 * <dd>Description:[应用环境变量]</dd>
 * <dt>Variable</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2018-11-15</dd>
 * @version 1.0
 * @author hypo zhang
 */
public class Variable implements LastModified, Serializable {

	private static final long serialVersionUID = -8799079144171457859L;

	public Variable() {
		this(null);
	}
	
	public void addVariable(String varName, Object value) {
		this.envMap.put(varName, value);
		setLastModified0(System.currentTimeMillis());
	}
	
	public void addVariable(Map<String, ?> varMap) {
		this.envMap.putAll(varMap);
		setLastModified0(System.currentTimeMillis());
	}

	public Object getVariable(String varName) {
		return this.envMap.get(varName);
	}
	
	public <T> T getVariable(String varName, Class<T> classType) {
		Object varValue = getVariable(varName);
		return Converter.convert(varValue, classType);
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		throw new UnsupportedOperationException();
	}

	public boolean isModified(long lastModified) {
		return this.lastModified < lastModified;
	}

	public boolean isUnmodifiable() {
		return unmodifiable;
	}
	
	public String fingerprint() {
		return fingerprint("SHA1");
	}
	
	/**
	 * <p>Discription:[Variable 数据指纹 (键和值)]</p>
	 * @return
	 * @author hypo zhang  2018-11-17
	 */
	public String fingerprint(String algorithm) {
		return UtilCollection.fingerprint(envMap, algorithm);
	}

	protected Variable(Map<String, ?> envMap) {
		if (envMap == null) {
			this.envMap = new HashMap<String, Object>();
		} else {
			Map<String, Object> theEnvironment = new HashMap<String, Object>();
			for (Iterator<String> it = envMap.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				theEnvironment.put(key, envMap.get(key));
			}
			this.envMap = Collections.unmodifiableMap(theEnvironment);
			unmodifiable = true;
		}
	}
			
	private final void setLastModified0(long lastModified) {
		this.lastModified = lastModified;
	}

	private boolean unmodifiable = false; // 不可修改的
	private final Map<String, Object> envMap;
	private long lastModified = System.currentTimeMillis();

	///////////////////////////////////////////////////////////////
	
	private static final Variable jvmFirst = loadJVMVariable(); // 虚拟机 启动时参数， 初次加载
	private static Variable jvmCurrent = jvmFirst;

	public static final Locale defaultJvmLocale() {
		return jvmFirst.getVariable(Locale.class.getName(), Locale.class);
	}
	
	public static final TimeZone defaultJvmTimeZone() {
		return jvmFirst.getVariable(TimeZone.class.getName(), TimeZone.class);
	}
	
	private static final Variable loadJVMVariable() {
		Properties prop = System.getProperties();
		Map<String, Object> theEnvironment = new HashMap<String, Object>();
		for (Iterator<Object> it = prop.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			String value = prop.getProperty(key);
			theEnvironment.put(key, value);
		}
		// 保证初次加载时设置
		long count = ppxhCount.incrementAndGet();
		if (count == 1) {
			theEnvironment.put(Locale.class.getName(), Locale.getDefault());
			theEnvironment.put(TimeZone.class.getName(), TimeZone.getDefault());
		}
		if (count == 3)
			ppxhCount.decrementAndGet();
		return new Variable(theEnvironment);
	}
	
	private static final AtomicLong ppxhCount = new AtomicLong();
	
	public static final Variable getJVMFirstEnv() {
		return jvmFirst;
	}
	
	public static final Variable getJVMCurrentEnv() {
		return jvmCurrent;
	}

	public static final void reloadJVMEnv() {
		Variable reload = loadJVMVariable();
		if (!reload.fingerprint().equals(jvmCurrent.fingerprint()))
			jvmCurrent = reload;
	}
	
	/**
	 * <p>Discription:[JVM环境，现在时与初次加载是否发生变化]</p>
	 * @return
	 * @author hypo zhang  2018-11-17
	 */
	public static final boolean isModifiedJVMCurrentThanFirst() {
		return jvmFirst.isModified(jvmCurrent.getLastModified());
	}

	/**
	 * <p> Discription:[现在时 取JVM env值] </p>
	 * @param varName
	 * @return
	 * @author hypo zhang 2018-11-15
	 */
	public static final String getJVMCurrentVariable(String varName) {
		Object value = jvmCurrent.getVariable(varName);
		return value == null ? null : value.toString();
	}

	/**
	 * <p>Discription:[从初次加载  取JVM env值] </p>
	 * 
	 * @param varName
	 * @return
	 * @author hypo zhang 2018-11-15
	 */
	public static final String getJVMFirstVariable(String varName) {
		Object value = jvmFirst.getVariable(varName);
		return value == null ? null : value.toString();
	}

	// 要求 Variable 类 在系统启动时加载
}
