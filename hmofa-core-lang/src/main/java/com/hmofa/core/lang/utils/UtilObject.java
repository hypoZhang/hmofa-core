package com.hmofa.core.lang.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.Map;

import com.hmofa.core.lang.ICloneable;


/**
 * <dd>Description:[对象工具]</dd>
 * <dt>UtilObejct</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2015-11-2</dd>
 * @version 1.0
 * @author 张海波
 */
public class UtilObject {
	
	public static final <T> T nullNvl(T value, T defaultValue) {
		return isNull(value) ? defaultValue : value;
	}
	
	public static final <T> T emptyNvl(T value, T defaultValue) {
		return isEmpty(value) ? defaultValue : value;
	}
	
	public static final <T> T blankNvl(T value, T defaultValue) {
		return isBlank(value) ? defaultValue : value;
	}
	
	public static final boolean isNotNull(Object value) {
		return !isNull(value);
	}
	
	public static final boolean isNull(Object value) {
		return value == null;
	}
	
	public static final boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}

	public static final boolean isEmpty(Object value) {
		boolean empty = value == null;
		empty = !empty && value instanceof CharSequence ? value.toString().length() == 0 : empty;
		empty = !empty && value instanceof Collection ? ((Collection<?>) value).isEmpty() : empty;
		empty = !empty && value instanceof Map ? ((Map<?, ?>) value).isEmpty() : empty;
		return empty;
	}
	
	public static final boolean isNotBlank(Object value) {
		return !isBlank(value);
	}
	
	public static final boolean isBlank(Object value) {
		boolean empty = isEmpty(value);
		empty = !empty ? value.toString().trim().length() == 0 : empty;
		return empty;
	}

	public static final boolean equals(Object obj1, Object obj2) {
		return obj1 == null ? obj2 == null : obj1 == obj2 ? true : obj1.equals(obj2);
	}
	
	
	public static Object clone(Object object) {
		if (object == null) {
			return null;
		}

		if (object instanceof String[]) {
			String[] source = (String[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Long[]) {
			Long[] source = (Long[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Integer[]) {
			Integer[] source = (Integer[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Short[]) {
			Short[] source = (Short[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Character[]) {
			Character[] source = (Character[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Byte[]) {
			Byte[] source = (Byte[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Boolean[]) {
			Boolean[] source = (Boolean[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Double[]) {
			Double[] source = (Double[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Float[]) {
			Float[] source = (Float[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof BigDecimal[]) {
			BigDecimal[] source = (BigDecimal[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Date[]) {
			Date[] source = (Date[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof Object[]) {
			Object[] source = (Object[]) object;
			return UtilArray.copyOf(source.length, source);
		}

		if (object instanceof List) {
			List<?> source = (List<?>) object;
			List<Object> value = new ArrayList<Object>(source.size());
			for (int i = 0, size = source.size(); i < size; i++) {
				value.add(clone(source.get(i)));
			}
			return value;
		}

		if (object instanceof Map) {
			Map<?, ?> source = (Map<?, ?>) object;
			Map<Object, Object> value = new HashMap<Object, Object>();
			Iterator<?> iter = source.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				value.put(key, clone(source.get(key)));
			}
			return value;
		}

		if (object instanceof ICloneable)
			return ((ICloneable<?>) object).clone();

		return object;
	}

}
