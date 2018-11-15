package com.hmofa.core.lang.env;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.hmofa.core.exception.BaseRuntimeException;
import com.hmofa.core.lang.LastModified;
import com.hmofa.core.lang.coder.StringCoder;
import com.hmofa.core.lang.helper.Hex;

/**
 * <dd>Description:[应用环境变量]</dd>
 * <dt>Variable</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2018-11-15</dd>
 * @version 1.0
 * @author hypo zhang
 */
public class Variable implements LastModified {

	public Variable() {
		this(null);
	}
	
	public void addVariable(String varName, Object value) {
		this.envMap.put(varName, value);
	}

	public Object getVariable(String varName) {
		return this.envMap.get(varName);
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
	}

	public boolean isModified(long lastModified) {
		return this.lastModified < lastModified;
	}

	public boolean isUnmodifiable() {
		return unmodifiable;
	}
		
	public String fingerprint() {
		List<String> keyList = new ArrayList<String>();
		for (Iterator<String> it = envMap.keySet().iterator(); it.hasNext();) {
			keyList.add(it.next());
		}
		Collections.sort(keyList);

		MessageDigest digest = getDigest("SHA1");
		for (String key : keyList) {
			Object value = envMap.get(key);
			digest.update(StringCoder.encode((value == null ? "" : value.toString()), StringCoder.UTF_8));
		}
		return Hex.toHexString(digest.digest());
	}

	private Variable(Map<String, String> envMap) {
		if (envMap == null) {
			this.envMap = new HashMap<String, Object>();
			unmodifiable = true;
		} else {
			Map<String, Object> theEnvironment = new HashMap<String, Object>();
			for (Iterator<String> it = envMap.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				theEnvironment.put(key, envMap.get(key));
			}
			this.envMap = Collections.unmodifiableMap(theEnvironment);
		}
	}
	
	private MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new BaseRuntimeException(e);
		}
	}

	private boolean unmodifiable = false; // 不可修改的
	private Map<String, Object> envMap;
	private final long lastModified = System.currentTimeMillis();

	
	
	private static final Variable envJVMFirst = loadJVMVariable(); // 虚拟机 启动时参数， 初次加载
	private static Variable envJVMCurrent = envJVMFirst;

	private static final Variable loadJVMVariable() {
		Properties prop = System.getProperties();
		Map<String, String> theEnvironment = new HashMap<String, String>();
		for (Iterator<Object> it = prop.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			String value = prop.getProperty(key);
			theEnvironment.put(key, value);
		}
		return new Variable(theEnvironment);
	}
	
	public static final Variable getJVMFirstEnv() {
		return envJVMFirst;
	}
	
	public static final Variable getJVMCurrentEnv() {
		return envJVMCurrent;
	}

	public static final void reloadJVMEnv() {
		Variable reload = loadJVMVariable();
		if (!reload.fingerprint().equals(envJVMCurrent.fingerprint()))
			envJVMCurrent = reload;
	}
	
	public static final boolean isModifiedCurrentThanFirst() {
		return envJVMFirst.isModified(envJVMCurrent.getLastModified());
	}

	/**
	 * <p>
	 * Discription:[从当前加载环境 取JVM env值]
	 * </p>
	 * 
	 * @param varName
	 * @return
	 * @author hypo zhang 2018-11-15
	 */
	public static final String getJVMCurrentVariable(String varName) {
		Object value = envJVMCurrent.getVariable(varName);
		return value == null ? null : value.toString();
	}

	/**
	 * <p>
	 * Discription:[从初次加载 环境 取JVM env值]
	 * </p>
	 * 
	 * @param varName
	 * @return
	 * @author hypo zhang 2018-11-15
	 */
	public static final String getJVMFirstVariable(String varName) {
		Object value = envJVMFirst.getVariable(varName);
		return value == null ? null : value.toString();
	}

	// 要求 Variable 类 在系统启动时加载
}
