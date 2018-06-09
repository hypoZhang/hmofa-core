package com.hmofa.core.lang.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

	/**
	 * <p>Discription:[空值则替换  (默认 null)]</p>
	 * <pre>
	 * 空值三类: null | "" | "\t\n\b\r" (空值\空串\空控制符)
	 * </pre>
	 * @param obj			比较对象
	 * @param replaceValue	替换值
	 * @return
	 * @author zhanghaibo3  2015-11-2
	 */
	public static final <T> T NVL(T obj, T replaceValue) {
		return NVL(obj, replaceValue, NVL_TYPE_NULL);
	}

	/**
	 * <p>Discription:[空值则替换]</p>
	 * <pre>
	 * 空值三类: null | "" | "\t\n\b\r" (空值\空串\空控制符)
	 * </pre>
	 * @param obj			比较对象
	 * @param replaceValue	替换值
	 * @param emptyType		1 null | 2 empty | 3 whitespace
	 * @return
	 * @author zhanghaibo3  2015-11-2
	 */
	public static final <T> T NVL(T obj, T replaceValue, int emptyType) {
		int type = getEmptyType(obj);
		if (NVL_TYPE_NORMAL == type)
			return obj;
		return (NVL_TYPE_EMPTY == emptyType && type == NVL_TYPE_NULL) || emptyType == type ? replaceValue : obj;
	}

	/**
	 * <p>Discription:[对象为空 (空值或空串)]</p>
	 * @param obj
	 * @return 空值或空串 = true | false
	 * @author zhanghaibo3  2015-11-2
	 */
	public static final boolean isEmpty(Object obj) {
		int type = getEmptyType(obj);
		return type == NVL_TYPE_NULL || type == NVL_TYPE_EMPTY ? true : false;
	}

	/**
	 * <p>Discription:[对象不为空 (空值或空串)]</p>
	 * @param obj 
	 * @return 空值或空串 = true | false
	 * @author zhanghaibo3  2015-11-2
	 */
	public static final boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * <p>Discription:[取对象空值类型]</p>
	 * @param obj
	 * @return
	 * @author zhanghaibo3  2015-11-2
	 */
	public static final int getEmptyType(Object obj) {
		if (obj == null)
			return NVL_TYPE_NULL;
		if (!(obj instanceof String))
			return NVL_TYPE_NORMAL;
		String value = obj.toString();
		return UtilString.isEmpty(value) ? NVL_TYPE_EMPTY : UtilString.isWhitespace(value) ? NVL_TYPE_WHITESPACE : NVL_TYPE_NORMAL;
	}

	public static final boolean equals(Object obj1, Object obj2) {
		return obj1 == null ? obj2 == null : obj1.equals(obj2);
	}
	
	public static Object clone(Object object) throws CloneNotSupportedException {
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

	public static final int NVL_TYPE_NORMAL = 0; // 非空
	public static final int NVL_TYPE_NULL = 1; // null
	public static final int NVL_TYPE_EMPTY = 2; // ""
	public static final int NVL_TYPE_WHITESPACE = 3;// "\n"
}
