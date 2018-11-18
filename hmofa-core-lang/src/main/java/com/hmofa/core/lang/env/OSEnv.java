package com.hmofa.core.lang.env;

/**
 * <dd>Description:[系统环境变量]</dd>
 * <dt>EnvVar</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2018-11-15</dd>
 * @version 1.0
 * @author hypo zhang
 */
public class OSEnv extends Variable {

	private static final long serialVersionUID = -1280288521956687276L;

	private OSEnv() {
		super(System.getenv());
	}
	
	private static final OSEnv systemEnv = new OSEnv();

	public static final String getEnvVariable(String varName) {
		return systemEnv.getVariable(varName, String.class);
	}

	public static final OSEnv getSystemEnv() {
		return systemEnv;
	}
	
}
