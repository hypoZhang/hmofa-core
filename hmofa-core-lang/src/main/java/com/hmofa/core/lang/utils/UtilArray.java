package com.hmofa.core.lang.utils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hmofa.core.lang.converter.Converter;



/**
 * <dd>Description:[数组工具]</dd>
 * <dt>UtilArray</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2015-11-2</dd>
 * @version 1.0
 * @author 张海波
 */
public class UtilArray {

	public final static <T> T[] array(T... args) {
		return args;
	}

	public final static Class<?>[] array(Class<?>... args) {
		return args;
	}
	
	public final static <T> Map<T, T> asMap(T... args) {
		Map<T, T> map = new HashMap<T, T>();
		mapPut(map, args);
		return map;
	}

	public final static <T> Map<T, T> asOrderMap(T... args) {
		Map<T, T> map = new LinkedHashMap<T, T>();
		mapPut(map, args);
		return map;
	}

	private final static <T> void mapPut(Map<T, T> map, T... args) {
		for (T t : args) {
			map.put(t, t);
		}
	}
	
	
	
	
	// 安全的移除空值
	public final static <T> T[] removalNull(T... args) {
		int length = args.length;
		T[] ar = null;
		for (int i = 0; i < length; i++) {
			if (args[i] == null) {
				cloneArray(length, ar, i, args);
				
				int index = i + 1;
				index = index >= length ? length - 1 : index;
				int cend = length - index;
				System.arraycopy(ar, index, ar, i, cend);
			}
		}
		return ar == null ? args : ar;
	}

	private static <T> void cloneArray(int length, T[] ar, int i, T... args) {
		if (ar == null) {
			ar = newInstance(args[i], args.length);
			System.arraycopy(args, 0, ar, i, length);
		}
	}

	/**
	 * <p>Discription:[转换成对应基本类型数组。自动去除null对象]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static byte[] toPrimitive(Byte... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_BYTE_ARRAY;
		}
		int dstlen = 0, olen = array.length;
		final byte[] result = new byte[olen];
		for (int i = 0; i < olen; i++) {
			if (array[i] == null)
				continue;
			result[dstlen++] = array[i].byteValue();
		}
		if (dstlen == olen)
			return result;
		final byte[] tosize = new byte[dstlen];
		System.arraycopy(result, 0, tosize, 0, dstlen);
		return tosize;
	}

	/**
	 * <p>Discription:[转换成对应基本类型数组。自动去除null对象]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static long[] toPrimitive(Long... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_LONG_ARRAY;
		}
		int dstlen = 0, olen = array.length;
		final long[] result = new long[olen];
		for (int i = 0; i < olen; i++) {
			if (array[i] == null)
				continue;
			result[dstlen++] = array[i].longValue();
		}
		if (dstlen == olen)
			return result;
		final long[] tosize = new long[dstlen];
		System.arraycopy(result, 0, tosize, 0, dstlen);
		return tosize;
	}

	/**
	 * <p>Discription:[转换成对应基本类型数组。自动去除null对象]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static short[] toPrimitive(Short... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_SHORT_ARRAY;
		}
		int dstlen = 0, olen = array.length;
		final short[] result = new short[olen];
		for (int i = 0; i < olen; i++) {
			if (array[i] == null)
				continue;
			result[dstlen++] = array[i].shortValue();
		}
		if (dstlen == olen)
			return result;
		final short[] tosize = new short[dstlen];
		System.arraycopy(result, 0, tosize, 0, dstlen);
		return tosize;
	}

	/**
	 * <p>Discription:[转换成对应基本类型数组。自动去除null对象]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static int[] toPrimitive(Integer... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_INT_ARRAY;
		}
		int dstlen = 0, olen = array.length;
		final int[] result = new int[olen];
		for (int i = 0; i < olen; i++) {
			if (array[i] == null)
				continue;
			result[dstlen++] = array[i].intValue();
		}
		if (dstlen == olen)
			return result;
		final int[] tosize = new int[dstlen];
		System.arraycopy(result, 0, tosize, 0, dstlen);
		return tosize;
	}

	/**
	 * <p>Discription:[转换成对应基本类型数组。自动去除null对象]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static float[] toPrimitive(Float... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_FLOAT_ARRAY;
		}
		int dstlen = 0, olen = array.length;
		final float[] result = new float[olen];
		for (int i = 0; i < olen; i++) {
			if (array[i] == null)
				continue;
			result[dstlen++] = array[i].floatValue();
		}
		if (dstlen == olen)
			return result;
		final float[] tosize = new float[dstlen];
		System.arraycopy(result, 0, tosize, 0, dstlen);
		return tosize;
	}

	/**
	 * <p>Discription:[转换成对应基本类型数组。自动去除null对象]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static double[] toPrimitive(Double... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_DOUBLE_ARRAY;
		}
		int dstlen = 0, olen = array.length;
		final double[] result = new double[olen];
		for (int i = 0; i < olen; i++) {
			if (array[i] == null)
				continue;
			result[dstlen++] = array[i].doubleValue();
		}
		if (dstlen == olen)
			return result;
		final double[] tosize = new double[dstlen];
		System.arraycopy(result, 0, tosize, 0, dstlen);
		return tosize;
	}

	/**
	 * <p>Discription:[转换成对应基本类型数组。自动去除null对象]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static boolean[] toPrimitive(Boolean... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_BOOLEAN_ARRAY;
		}
		int dstlen = 0, olen = array.length;
		final boolean[] result = new boolean[olen];
		for (int i = 0; i < olen; i++) {
			if (array[i] == null)
				continue;
			result[dstlen++] = array[i].booleanValue();
		}
		if (dstlen == olen)
			return result;

		final boolean[] tosize = new boolean[dstlen];
		System.arraycopy(result, 0, tosize, 0, dstlen);
		return tosize;
	}

	/**
	 * <p>Discription:[转换成对应基本类型数组。自动去除null对象]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static char[] toPrimitive(Character... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_CHAR_ARRAY;
		}
		int dstlen = 0, olen = array.length;
		final char[] result = new char[olen];
		for (int i = 0; i < olen; i++) {
			if (array[i] == null)
				continue;
			result[dstlen++] = array[i].charValue();
		}
		if (dstlen == olen)
			return result;

		final char[] tosize = new char[dstlen];
		System.arraycopy(result, 0, tosize, 0, dstlen);
		return tosize;
	}

	/**
	 * <p>Discription:[转换为对应封装类型]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static Byte[] toWrapClass(byte... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_BYTE_OBJECT_ARRAY;
		}
		final Byte[] result = new Byte[array.length];
		int i = 0;
		for (byte by : array)
			result[i++] = by;
		return result;
	}

	/**
	 * <p>Discription:[转换为对应封装类型]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static Long[] toWrapClass(long... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_LONG_OBJECT_ARRAY;
		}
		final Long[] result = new Long[array.length];
		int i = 0;
		for (long by : array)
			result[i++] = by;
		return result;
	}

	/**
	 * <p>Discription:[转换为对应封装类型]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static Short[] toWrapClass(short... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_SHORT_OBJECT_ARRAY;
		}
		final Short[] result = new Short[array.length];
		int i = 0;
		for (short by : array)
			result[i++] = by;
		return result;
	}

	/**
	 * <p>Discription:[转换为对应封装类型]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static Integer[] toWrapClass(int... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_INTEGER_OBJECT_ARRAY;
		}
		final Integer[] result = new Integer[array.length];
		int i = 0;
		for (int by : array)
			result[i++] = by;
		return result;
	}

	/**
	 * <p>Discription:[转换为对应封装类型]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static Float[] toWrapClass(float... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_FLOAT_OBJECT_ARRAY;
		}
		final Float[] result = new Float[array.length];
		int i = 0;
		for (float by : array)
			result[i++] = Float.valueOf(by);
		return result;
	}

	/**
	 * <p>Discription:[转换为对应封装类型]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static Double[] toWrapClass(double... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_DOUBLE_OBJECT_ARRAY;
		}
		final Double[] result = new Double[array.length];
		int i = 0;
		for (double by : array)
			result[i++] = Double.valueOf(by);
		return result;
	}

	/**
	 * <p>Discription:[转换为对应封装类型]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static Boolean[] toWrapClass(boolean... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_BOOLEAN_OBJECT_ARRAY;
		}
		final Boolean[] result = new Boolean[array.length];
		int i = 0;
		for (boolean by : array)
			result[i++] = Boolean.valueOf(by);
		return result;
	}

	/**
	 * <p>Discription:[转换为对应封装类型]</p>
	 * @param array
	 * @return
	 * @author:  张海波  2011-04-25
	 */
	public final static Character[] toWrapClass(char... array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return EMPTY_CHARACTER_OBJECT_ARRAY;
		}
		final Character[] result = new Character[array.length];
		int i = 0;
		for (char by : array)
			result[i++] = Character.valueOf(by);
		return result;
	}

	public final static <T> T[] toObjectArray(Class<T> dstcls, Object... array) {
		if (array == null || array.length == 0)
			return null;
		if (dstcls.isPrimitive())
			dstcls = Converter.cast(Converter.getPrimitiveWrap(dstcls));
		if (array.getClass().getComponentType() == dstcls)
			return Converter.cast(array);
		int olen = array.length;
		final T[] result = Converter.cast(Array.newInstance(dstcls, olen));
		int i = 0;
		for (Object obj : array)
			result[i++] = Converter.convert(obj, dstcls);
		return result;
	}

	public final static byte[] copyOf(int newLength, byte... original) {
		byte[] copy = new byte[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}

	public final static short[] copyOf(int newLength, short... original) {
		short[] copy = new short[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}

	public final static int[] copyOf(int newLength, int... original) {
		int[] copy = new int[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}

	public final static long[] copyOf(int newLength, long... original) {
		long[] copy = new long[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}

	public final static float[] copyOf(int newLength, float... original) {
		float[] copy = new float[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}

	public final static double[] copyOf(int newLength, double... original) {
		double[] copy = new double[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}

	public final static boolean[] copyOf(int newLength, boolean... original) {
		boolean[] copy = new boolean[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}

	public final static char[] copyOf(int newLength, char... original) {
		char[] copy = new char[newLength];
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	
	public final static <T> T[] copyOf(T... original) {
		return copyOf(original.length, original);
	}

	public final static <T> T[] copyOf(int newLength, T... original) {
		return Converter.cast(copyOf(original, newLength, original.getClass()));
	}

	private static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
		T[] copy = Converter.cast(Array.newInstance(newType.getComponentType(), newLength));
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
	
	public final static <T> T[] newInstance(Class<T> clazz, int length) {
		T[] copy = Converter.cast(Array.newInstance(clazz, length));
		return copy;
	}
	
	public final static <T> T[] newInstance(T t, int length) {
		if (t == null)
			return Converter.cast(new Object[length]);
		return Converter.cast(newInstance(t.getClass(), length));
	}

	public final static boolean isNotEmpty(byte... array) {
		return !isEmpty(array);
	}

	public final static boolean isNotEmpty(long... array) {
		return !isEmpty(array);
	}

	public final static boolean isNotEmpty(short... array) {
		return !isEmpty(array);
	}

	public final static boolean isNotEmpty(int... array) {
		return !isEmpty(array);
	}

	public final static boolean isNotEmpty(float... array) {
		return !isEmpty(array);
	}

	public final static boolean isNotEmpty(double... array) {
		return !isEmpty(array);
	}

	public final static boolean isNotEmpty(boolean... array) {
		return !isEmpty(array);
	}

	public final static boolean isNotEmpty(char... array) {
		return !isEmpty(array);
	}

	public final static boolean isNotEmpty(Object... array) {
		return !isEmpty(array);
	}

	public final static boolean isEmpty(byte... array) {
		if (array == null || array.length == 0)
			return true;
		return false;
	}

	public final static boolean isEmpty(long... array) {
		if (array == null || array.length == 0)
			return true;
		return false;
	}

	public final static boolean isEmpty(short... array) {
		if (array == null || array.length == 0)
			return true;
		return false;
	}

	public final static boolean isEmpty(int... array) {
		if (array == null || array.length == 0)
			return true;
		return false;
	}

	public final static boolean isEmpty(float... array) {
		if (array == null || array.length == 0)
			return true;
		return false;
	}

	public final static boolean isEmpty(double... array) {
		if (array == null || array.length == 0)
			return true;
		return false;
	}

	public final static boolean isEmpty(boolean... array) {
		if (array == null || array.length == 0)
			return true;
		return false;
	}

	public final static boolean isEmpty(char... array) {
		if (array == null || array.length == 0)
			return true;
		return false;
	}

	/**
	 * 数组是否为空
	 * <pre>
	 * isEmpty(null) = true
	 * isEmpty(new Object[0]) = true
	 * isEmpty(new Object[]{1}) = false
	 * </pre>
	 * @param array
	 * @return
	 */
	public final static <T> boolean isEmpty(T... array) {
		if (array == null || array.length == 0)
			return true;
		return false;
	}

	/**
	 * <p>Discription:[对象是否是数组，并且为空]</p>
	 * @param obj
	 * @return
	 * @author 张海波  2017-3-20
	 */
	public final static <T> boolean isArrayEmpty(T obj) {
		if (obj == null)
			return true;
		if (!obj.getClass().isArray())
			return false;
		if (!obj.getClass().getComponentType().isPrimitive())
			return isEmpty((Object[]) obj);
		try {
			byte[] c = (byte[]) obj;
			return isEmpty(c);
		} catch (ClassCastException ex) {
		}
		try {
			char[] c = (char[]) obj;
			return isEmpty(c);
		} catch (ClassCastException ex) {
		}
		try {
			int[] c = (int[]) obj;
			return isEmpty(c);
		} catch (ClassCastException ex) {
		}
		try {
			long[] c = (long[]) obj;
			return isEmpty(c);
		} catch (ClassCastException ex) {
		}
		try {
			float[] c = (float[]) obj;
			return isEmpty(c);
		} catch (ClassCastException ex) {
		}
		try {
			double[] c = (double[]) obj;
			return isEmpty(c);
		} catch (ClassCastException ex) {
		}
		try {
			short[] c = (short[]) obj;
			return isEmpty(c);
		} catch (ClassCastException ex) {
		}
		try {
			boolean[] c = (boolean[]) obj;
			return isEmpty(c);
		} catch (ClassCastException ex) {
		}
		return false;
	}

	/**
	 * <p>Discription:[数组顺序换位]</p>
	 * @param array
	 * @return 返回引用
	 * @author hypo  2012-06-13
	 */
	public final static <T> T[] reverse(T... array) {
		if (isEmpty(array))
			return array;
		T btmp = null;
		int j = array.length;
		BigDecimal length = new BigDecimal(array.length);
		int center = length.divide(divnum2, 0, RoundingMode.HALF_DOWN).intValue();
		for (int i = 0; i < center; i++) {
			btmp = array[i];
			array[i] = array[--j];
			array[j] = btmp;
		}
		return array;
	}

	public final static byte[] reverse(byte... array) {
		if (isEmpty(array))
			return array;
		byte btmp = 0;
		int j = array.length;
		BigDecimal length = new BigDecimal(array.length);
		int center = length.divide(divnum2, 0, RoundingMode.HALF_DOWN).intValue();
		for (int i = 0; i < center; i++) {
			btmp = array[i];
			array[i] = array[--j];
			array[j] = btmp;
		}
		return array;
	}

	public final static char[] reverse(char... array) {
		if (isEmpty(array))
			return array;
		char btmp = 0;
		int j = array.length;
		BigDecimal length = new BigDecimal(array.length);
		int center = length.divide(divnum2, 0, RoundingMode.HALF_DOWN).intValue();
		for (int i = 0; i < center; i++) {
			btmp = array[i];
			array[i] = array[--j];
			array[j] = btmp;
		}
		return array;
	}

	public final static int[] reverse(int... array) {
		if (isEmpty(array))
			return array;
		int btmp = 0;
		int j = array.length;
		BigDecimal length = new BigDecimal(array.length);
		int center = length.divide(divnum2, 0, RoundingMode.HALF_DOWN).intValue();
		for (int i = 0; i < center; i++) {
			btmp = array[i];
			array[i] = array[--j];
			array[j] = btmp;
		}
		return array;
	}

	public final static short[] reverse(short... array) {
		if (isEmpty(array))
			return array;
		short btmp = 0;
		int j = array.length;
		BigDecimal length = new BigDecimal(array.length);
		int center = length.divide(divnum2, 0, RoundingMode.HALF_DOWN).intValue();
		for (int i = 0; i < center; i++) {
			btmp = array[i];
			array[i] = array[--j];
			array[j] = btmp;
		}
		return array;
	}

	public final static long[] reverse(long... array) {
		if (isEmpty(array))
			return array;
		long btmp = 0;
		int j = array.length;
		BigDecimal length = new BigDecimal(array.length);
		int center = length.divide(divnum2, 0, RoundingMode.HALF_DOWN).intValue();
		for (int i = 0; i < center; i++) {
			btmp = array[i];
			array[i] = array[--j];
			array[j] = btmp;
		}
		return array;
	}

	public final static float[] reverse(float... array) {
		if (isEmpty(array))
			return array;
		float btmp = 0;
		int j = array.length;
		BigDecimal length = new BigDecimal(array.length);
		int center = length.divide(divnum2, 0, RoundingMode.HALF_DOWN).intValue();
		for (int i = 0; i < center; i++) {
			btmp = array[i];
			array[i] = array[--j];
			array[j] = btmp;
		}
		return array;
	}

	public final static double[] reverse(double... array) {
		if (isEmpty(array))
			return array;
		double btmp = 0;
		int j = array.length;
		BigDecimal length = new BigDecimal(array.length);
		int center = length.divide(divnum2, 0, RoundingMode.HALF_DOWN).intValue();
		for (int i = 0; i < center; i++) {
			btmp = array[i];
			array[i] = array[--j];
			array[j] = btmp;
		}
		return array;
	}

	public final static boolean contains(byte value, byte... array) {
		for (byte by : array) {
			if (by == value)
				return true;
		}
		return false;
	}
	
	public final static byte[] removeLeft0(int num, byte... bytes) {
		int begindex = 0;
		for (int index = 0; index < bytes.length; index++) {
			if(bytes[index] == 0)
				begindex++;
			if(bytes[index] != 0 || (num != -1 && (begindex == num)))
				break;
		}
		if (begindex == 0)
			return bytes;
		int length1 = bytes.length - begindex;
		byte[] tosize = new byte[length1];
		System.arraycopy(bytes, begindex, tosize, 0, length1);
		return tosize;
	}

	/**
	 * <p>Discription:[删除字节数组前后为0字节]</p>
	 * @param bytes
	 * @return
	 * @author 张海波  2015-12-31
	 */
	public final static byte[] trim0(byte... bytes) {

		int begindex = -1;
		int endindex = -1;
		int lastindex = bytes.length;
		for (int index = 0; index < bytes.length; index++) {
			if (begindex == -1 && bytes[index] != 0)
				begindex = index;
			if (endindex == -1 && bytes[--lastindex] != 0)
				endindex = lastindex + 1;
			if (begindex != -1 && endindex != -1)
				break;
		}
		if (begindex == -1 && endindex == -1)
			return EMPTY_BYTE_ARRAY;
		if (begindex == 0 && endindex == bytes.length)
			return bytes;
		int length1 = endindex - begindex;
		byte[] tosize = new byte[length1];
		System.arraycopy(bytes, begindex, tosize, 0, length1);
		return tosize;
	}

	/**
	 * <p>Discription:[删除前后为  32 空格字节]</p>
	 * @param bytes
	 * @return
	 * @author zhanghaibo3  2016-12-30
	 */
	public final static byte[] trimSpace(byte... bytes) {

		int begindex = -1;
		int endindex = -1;
		int lastindex = bytes.length;
		for (int index = 0; index < bytes.length; index++) {
			if (begindex == -1 && bytes[index] != 32)
				begindex = index;
			if (endindex == -1 && bytes[--lastindex] != 32)
				endindex = lastindex + 1;
			if (begindex != -1 && endindex != -1)
				break;
		}
		if (begindex == -1 && endindex == -1)
			return EMPTY_BYTE_ARRAY;
		if (begindex == 0 && endindex == bytes.length)
			return bytes;
		int length1 = endindex - begindex;
		byte[] tosize = new byte[length1];
		System.arraycopy(bytes, begindex, tosize, 0, length1);
		return tosize;
	}

	/**
	 * <p>Discription:[删除字节数组中为0字节]</p>
	 * @param bytes
	 * @return
	 * @author 张海波  2015-12-31
	 */
	public final static byte[] remove0(byte... bytes) {
		int index0 = 0;
		byte[] midByte = null;
		for (int index = 0; index < bytes.length; index++) {
			if (bytes[index] == 0 && midByte == null) {
				midByte = new byte[bytes.length - index];
				System.arraycopy(bytes, 0, midByte, 0, index);
				index0 = index - 1;
			} else {
				if (bytes[index] != 0 && midByte != null)
					midByte[++index0] = bytes[index];
			}
		}
		if (midByte == null)
			return bytes;
		if (index0 == midByte.length)
			return midByte;
		byte[] tosize = new byte[index0 + 1];
		System.arraycopy(midByte, 0, tosize, 0, tosize.length);
		return tosize;
	}

	private final static BigDecimal divnum2 = new BigDecimal("2");

	public final static int indexOf(Object objectToFind, Object... array) {
		return indexOf(objectToFind, 0, array);
	}

	public final static int indexOf(Object objectToFind, int startIndex, Object... array) {
		if (array == null)
			return -1;
		if (startIndex < 0)
			startIndex = 0;
		for (int index = startIndex; index < array.length; index++) {
			if (UtilObject.equals(objectToFind, array[index]))
				return index;
		}
		return -1;
	}

	public final static <T> T[] emptyArray(Class<T> cls) {
		return newInstance(cls, 0);
	}
	
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	public static final int[] EMPTY_INT_ARRAY = new int[0];
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	public static final long[] EMPTY_LONG_ARRAY = new long[0];
	public static final short[] EMPTY_SHORT_ARRAY = new short[0];
	public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
	public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
	public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

	public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
	public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
	public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
	public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
	public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
	public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
	public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
	public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

}
