package com.hmofa.core.lang.exception;

import com.hmofa.core.exception.BaseRuntimeException;
import com.hmofa.core.exception.NullArgumentException;
import com.hmofa.core.lang.bundle.StringFormat;
import com.hmofa.core.lang.utils.UtilArray;
import com.hmofa.core.lang.utils.UtilString;

public final class Assert {

	public static <T> T notNull(T object, String message, Object... args) {
		if (object == null)
			throw new NullArgumentException(new StringFormat(message, "{}").getFormatString(args));
		return object;
	}

	public static <T> T notNull(T object) {
		return notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	public static <T> T notEmpty(T object, String message, Object... args) {
		if (UtilArray.isArrayEmpty(object))
			throw new NullArgumentException(new StringFormat(message, "{}").getFormatString(args));
		if (UtilString.isEmpty(object.toString()))
			throw new NullArgumentException(new StringFormat(message, "{}").getFormatString(args));
		return object;
	}

	public static <T> T notEmpty(T object) {
		return notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	public static <T extends Comparable<T>> T greaterThan(T object1, T object2) {
		return greaterThan(object1, object2, "[Assertion failed] - object1 greater then object2");
	}

	public static <T extends Comparable<T>> T greaterThan(T object1, T object2, String message, Object... args) {
		notNull(object1);
		notNull(object2);
		int c = object1.compareTo(object2);
		if (c > 0)
			throw new IllegalArgumentException(new StringFormat(message, "{}").getFormatString(args));
		return object1;
	}

	/**
	 * <p>Discription:[返回一个异常]</p>
	 * @return
	 * @author 张海波  2016-4-10
	 */
	public static BaseRuntimeException getBaseRuntimeException() {
		return new BaseRuntimeException();
	}

	public static BaseRuntimeException getBaseRuntimeException(String message) {
		return new BaseRuntimeException(message);
	}
}
