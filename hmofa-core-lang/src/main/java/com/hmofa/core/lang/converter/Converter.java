package com.hmofa.core.lang.converter;

import java.util.Map;

import com.hmofa.core.exception.ConversionException;
import com.hmofa.core.exception.LoadClassException;
import com.hmofa.core.lang.tuple.Pair;
import com.hmofa.core.lang.utils.UtilClassLoader;
import com.hmofa.core.lang.utils.UtilObject;
import com.hmofa.core.lang.utils.UtilString;
import com.hmofa.platform.core.bean.classcache.ClassManager;




/**
 * <dd>Description:[转换器 工具]</dd>
 * <dt>Converter</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2015-11-2</dd>
 * @version 1.0
 * @author 张海波
 */
public final class Converter {

	public static final <T> T convert(Object converObj, Class<T> claxx, Object... param) {
		return null;
	}
	
	/**
	 * 如果 obj 为 null返回 null <br>
	 * obj不为null， 不支持转换 时  除目标类型 CharSequence 返回 "" 空串， 其它类型不支持则抛出异常
	 * <p>Discription:[转换对象为目标类型]</p>
	 * @param obj	被转换对象
	 * @param cls	目标类型
	 * @return 目标类型，或抛出异常
	 * @author zhanghaibo3  2015-11-3
	 * @throws ConversionException
	 */
	public static final <T> T convert(Object obj, Class<T> cls) {
		if (cls == null)
			throw new ConversionException(); 	// 目标类型不能为空类型
		if (obj == null) {
			if (String.class == cls)
				return cast("");
			return null;
		}
		
		if (cls.isInstance(obj))
			return cls.cast(obj);
		if (cls.isPrimitive() && obj.getClass().getName().equals(getPrimitiveWrap(cls).getName()))
			return cast(obj);
		
		Map<Class<?>, IConvertible<?>> conversionsMap = ClassManager.getConverterConvertibleMap();
		if (!conversionsMap.containsKey(cls))
			throw new ConversionException(); 	// 不支持的类型转换 (没有对应转换器)
		
		IConvertible<T> conver = cast(conversionsMap.get(cls));
		T t = null;
		try {
			t = conver.convert(obj);
		} catch (Throwable caue) {
			throw new ConversionException(caue, obj, cls);
		}
		// 除字符串外，其它类型都不返回  null 不支持的转换则抛异常
		if (t == null) {
			if (String.class == cls)
				return cast("");
		}
		if (!isCharSequence(cls))
			return t;
		t = charSequenceBuffer(cls, t);  // 转换为   StringBuffer  | StringBuilder
		return t;
	}
	
	/**
	 * <p>Discription:[转换对象为目标类型]</p>
	 * @param obj        被转换对象
	 * @param className  目标类型
	 * @return
	 * @author zhanghaibo3  2015-11-3
	 */
	public static Object convert(Object obj, String className) {
		if(UtilString.isEmpty(className))
			throw new ConversionException(); // 目标类型不能为空类型
		if (UtilObject.isEmpty(obj))
			return null;
		try {
			Class<?> cls = UtilClassLoader.loadClass(className);
			return convert(obj, cls);
		} catch (LoadClassException ex) {
			throw new ConversionException(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T cast(Object obj) {
		try {
			return (T) obj;
		} catch (ClassCastException ex) {
			throw new ConversionException(ex);
		}
	}
	
	/**
	 * <p>Discription:[取原始类型包装类,否则返回它自己]</p>
	 * @param cls
	 * @return
	 * @author 张海波  2015-11-5
	 */
	public static final Class<?> getPrimitiveWrap(Class<?> cls) {
		if(isPrimitiveWrap(cls))
			return cls;
		Map<Class<?>, Class<?>> primitiveMap = ClassManager.getConverterPrimitiveMap();
		if (!primitiveMap.containsKey(cls))
			return cls;		// 没有对应原始类型，返回自己
		return primitiveMap.get(cls);
	}
	
	/**
	 * <p>Discription:[取基础类型，否则返回它自己]</p>
	 * @param cls
	 * @return
	 * @author zhanghaibo3  2016-1-30
	 */
	public static final Class<?> getPrimitive(Class<?> cls) {
		if (cls.isPrimitive())
			return cls;
		Map<Class<?>, Class<?>> primitiveMap = ClassManager.getConverterPrimitiveMap();
		if (isPrimitiveWrap(cls))
			return primitiveMap.get(cls);
		return cls;
	}
		
	/**
	 * <p>Discription:[是否原始类型封装类型]</p>
	 * @param obj
	 * @return
	 * @author zhanghaibo3  2015-11-5
	 */
	public static final boolean isPrimitiveWrap(Object obj) {
		return isPrimitiveWrap(obj == null ? null : obj.getClass());
	}
	
	public static final boolean isPrimitiveWrap(Class<?> cls) {
		Map<Class<?>, Class<?>> primitiveMap = ClassManager.getConverterPrimitiveMap();
		if (!primitiveMap.containsKey(cls))
			return false;
		return primitiveMap.get(cls).isPrimitive();
	}
	
	private static <T> T charSequenceBuffer(Class<T> cls, T t) {
		if (!isCharSequence(cls))
			return t;
		String tmp = t.toString();
		if (isStringBuffer(cls))
			return cast(new StringBuffer(tmp.length() + 2).append(tmp));
		return cast(new StringBuilder(tmp.length() + 2).append(tmp));
	}
	
	private static boolean isCharSequence(Class<?> cls) {
		return isStringBuffer(cls) || isStringBuilder(cls);
	}

	private static boolean isStringBuffer(Class<?> cls) {
		if (StringBuffer.class == cls)
			return true;
		return false;
	}

	private static boolean isStringBuilder(Class<?> cls) {
		if (StringBuilder.class == cls)
			return true;
		return false;
	}
		
//	@SuppressWarnings("unchecked")
//	public static final <T> T[] cast(Object[] obj) {
//		try {
//			return (T[]) obj;
//		} catch (ClassCastException ex) {
//			throw new ConversionException(ex);
//		}
//	}
	
	private Converter() {
	}
}
