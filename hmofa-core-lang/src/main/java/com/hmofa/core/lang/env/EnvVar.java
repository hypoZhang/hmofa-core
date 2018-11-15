package com.hmofa.core.lang.env;

import java.util.Map;

import com.hmofa.core.lang.LastModified;

/**
 * <dd>Description:[系统环境变量]</dd>
 * <dt>EnvVar</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2018-11-15</dd>
 * @version 1.0
 * @author hypo zhang
 */
public class EnvVar implements LastModified {

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
	}

	public boolean isModified(long lastModified) {
		return this.lastModified < lastModified;
	}

	private EnvVar() {
	}

	private String getEnvVar(String varName) {
		return envMap.get(varName);
	}

	private final long lastModified = System.currentTimeMillis();

	private final Map<String, String> envMap = System.getenv();

	private static final EnvVar envFirst = new EnvVar();

	public static final String getVariable(String varName) {
		return envFirst.getEnvVar(varName);
	}

	public static final EnvVar getSystemEnv() {
		return envFirst;
	}
}
