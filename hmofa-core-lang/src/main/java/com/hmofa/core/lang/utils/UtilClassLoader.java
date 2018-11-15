package com.hmofa.core.lang.utils;

import com.hmofa.core.exception.LoadClassException;

public class UtilClassLoader {

	/**
	 * <p>Discription:[加载类(使用系统装载类)]</p>
	 * @param className
	 * @return
	 * @throws BaseRuntimeException
	 * @author zhanghaibo3  2015-11-3
	 */
	public static Class<?> loadClass(String className) throws LoadClassException {
		return loadClass(className, getDefaultClassLoader());
	}
		
	public static Class<?> loadClass(String className, ClassLoader classLoader) throws LoadClassException {
		if (UtilString.isWhitespace(className))
			throw new LoadClassException();
		try {
			return classLoader.loadClass(className);
		} catch (ClassNotFoundException ex) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new LoadClassException();
			}
		}
	}

	/**
	 * <p>Discription:[获得装载类(线程)]</p>
	 * @return
	 * @author 张海波  2015-11-3
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader clc = getThreadLoader();
		return clc == null ? getSystemLoader() : clc;
	}


	/**
	 * <p>Discription:[获得系统装载类]</p>
	 * @return
	 * @author 张海波  2015-11-3
	 */
	public static ClassLoader getSystemLoader() {
		return ClassLoader.getSystemClassLoader();
	}

	
	/**
	 * <p>Discription:[获得当前线程使用的装载类]</p>
	 * @return
	 * @author 张海波  2015-11-3
	 */
	public static ClassLoader getThreadLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
}
