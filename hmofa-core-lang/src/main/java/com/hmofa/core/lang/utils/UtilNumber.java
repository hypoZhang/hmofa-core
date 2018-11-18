package com.hmofa.core.lang.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hmofa.core.exception.ConversionException;
import com.hmofa.core.exception.PrecisionLossException;
import com.hmofa.core.lang.converter.Converter;
import com.hmofa.core.lang.helper.PadAtChar;


public class UtilNumber {

	public static byte[] toByteArrayBigEndian(Character ch) {
		if (ch == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		char c = ch.charValue();
		return toByteArrayBigEndian(c);
	}

	public static byte[] toByteArrayBigEndian(Short s) {
		if (s == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		short numberS = s.shortValue();
		return toByteArrayBigEndian(numberS);
	}

	public static byte[] toByteArrayBigEndian(Integer i) {
		if (i == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		int numberI = i.intValue();
		return toByteArrayBigEndian(numberI);
	}

	public static byte[] toByteArrayBigEndian(Long L) {
		if (L == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		long numberL = L.longValue();
		return toByteArrayBigEndian(numberL);
	}

	public static byte[] toByteArrayBigEndian(Float f) {
		if (f == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		float numberF = f.floatValue();
		return toByteArrayBigEndian(numberF);
	}

	public static byte[] toByteArrayBigEndian(Double d) {
		if (d == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		double numberD = d.doubleValue();
		return toByteArrayBigEndian(numberD);
	}

	public static byte[] toByteArrayBigEndian(char ch) {
		return toByteArrayBigEndian(ch, 2);
	}

	public static byte[] toByteArrayBigEndian(short numberS) {
		return toByteArrayBigEndian(numberS, 2);
	}

	public static byte[] toByteArrayBigEndian(int numberI) {
		return toByteArrayBigEndian(numberI, 4);
	}

	public static byte[] toByteArrayBigEndian(long numberL) {
		return toByteArrayBigEndian(numberL, 8);
	}

	public static byte[] toByteArrayBigEndian(float numberF) {
		int i = Float.floatToIntBits(numberF);
		return toByteArrayBigEndian(i, 4);
	}

	public static byte[] toByteArrayBigEndian(double numberD) {
		long L = Double.doubleToLongBits(numberD);
		return toByteArrayBigEndian(L, 8);
	}

	/**
	 * <p>Discription:[按高位字节顺序编码]</p>
	 * 虚拟机默认字节序，网络字节序
	 * @param value
	 * @param length
	 * @return
	 * @author zhanghaibo3  2015-12-30
	 */
	private static byte[] toByteArrayBigEndian(long value, int length) {
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++)
			bytes[length - 1 - i] = (byte) (value >> (i * 8));
		return bytes;
	}

	public static byte[] toByteArrayLittleEndian(Character ch) {
		if (ch == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		char c = ch.charValue();
		return toByteArrayLittleEndian(c);
	}

	public static byte[] toByteArrayLittleEndian(Short s) {
		if (s == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		short numberS = s.shortValue();
		return toByteArrayLittleEndian(numberS);
	}

	public static byte[] toByteArrayLittleEndian(Integer i) {
		if (i == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		int numberI = i.intValue();
		return toByteArrayLittleEndian(numberI);
	}

	public static byte[] toByteArrayLittleEndian(Long L) {
		if (L == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		long numberL = L.longValue();
		return toByteArrayLittleEndian(numberL);
	}

	public static byte[] toByteArrayLittleEndian(Float f) {
		if (f == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		float numberF = f.floatValue();
		return toByteArrayLittleEndian(numberF);
	}

	public static byte[] toByteArrayLittleEndian(Double d) {
		if (d == null)
			return UtilArray.EMPTY_BYTE_ARRAY;
		double numberD = d.doubleValue();
		return toByteArrayLittleEndian(numberD);
	}

	public static byte[] toByteArrayLittleEndian(char ch) {
		return toByteArrayLittleEndian(ch, 2);
	}

	public static byte[] toByteArrayLittleEndian(short numberS) {
		return toByteArrayLittleEndian(numberS, 2);
	}

	public static byte[] toByteArrayLittleEndian(int numberI) {
		return toByteArrayLittleEndian(numberI, 4);
	}

	public static byte[] toByteArrayLittleEndian(long numberL) {
		return toByteArrayLittleEndian(numberL, 8);
	}

	public static byte[] toByteArrayLittleEndian(float numberF) {
		int i = Float.floatToIntBits(numberF);
		return toByteArrayLittleEndian(i, 4);
	}

	public static byte[] toByteArrayLittleEndian(double numberD) {
		long L = Double.doubleToLongBits(numberD);
		return toByteArrayLittleEndian(L, 8);
	}

	/**
	 * <p>Discription:[按低位字节顺序编码]</p>
	 * @param value		编码数字
	 * @param length	字节最大长度
	 * @return
	 * @author 张海波  2015-12-30
	 */
	private static byte[] toByteArrayLittleEndian(long value, int length) {
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++)
			bytes[i] = (byte) (value >> (i * 8));
		return bytes;
	}

	///////////////////////////////////////////////////

	public static final short toBigEndianShort(byte... bytes) {
		long L = toBigEndianLong(bytes);
		if (isPrecisionLoss(L, Short.class))
			throw new PrecisionLossException(null, L, Short.MAX_VALUE);
		return (short) L;
	}

	public static final float toBigEndianFloat(byte... bytes) {
		int L = toBigEndianInteger(bytes);
		return Float.intBitsToFloat(L);
	}

	public static final double toBigEndianDouble(byte... bytes) {
		long L = toBigEndianLong(bytes);
		return Double.longBitsToDouble(L);
	}

	public static final int toBigEndianInteger(byte... bytes) {
		long L = toBigEndianLong(bytes);
		if (isPrecisionLoss(L, Integer.class))
			throw new PrecisionLossException(null, L, Integer.MAX_VALUE);
		return (int) L;
	}

	/**
	 * <p>Discription:[Big Endian 顺序字节数组 (高位字节序)]</p>
	 * 虚拟机默认字节序
	 * @param bytes
	 * @return
	 * @author 张海波  2015-12-18
	 */
	public static final long toBigEndianLong(byte... bytes) {
		long L = 0;
		for (int i = 0; i < bytes.length; i++)
			L |= ((bytes[bytes.length - 1 - i] & 0xFFL) << (i * 8));
		return L;
	}

	public static final short toLittleEndianShort(byte... bytes) {
		long L = toLittleEndianLong(bytes);
		if (isPrecisionLoss(L, Short.class))
			throw new PrecisionLossException(null, L, Short.MAX_VALUE);
		return (short) L;
	}

	public static final float toLittleEndianFloat(byte... bytes) {
		int L = toLittleEndianInteger(bytes);
		return Float.intBitsToFloat(L);
	}

	public static final double toLittleEndianDouble(byte... bytes) {
		long L = toLittleEndianLong(bytes);
		return Double.longBitsToDouble(L);
	}

	public static final int toLittleEndianInteger(byte... bytes) {
		long L = toLittleEndianLong(bytes);
		if (isPrecisionLoss(L, Integer.class))
			throw new PrecisionLossException(null, L, Integer.MAX_VALUE);
		return (int) L;
	}

	/**
	 * <p>Discription:[Little Endian 顺序字节数组（低位字节序）]</p>
	 * @param bytes
	 * @return
	 * @author 张海波  2015-12-18
	 */
	public static final long toLittleEndianLong(byte... bytes) {
		long L = 0;
		for (int i = 0; i < bytes.length; i++)
			L |= ((bytes[i] & 0xFFL) << (i * 8));
		return L;
	}
	
	public static long max(long... ls) {
		long max = 0;
		boolean flag = false;
		for (int i = 0; i < ls.length; i++) {
			long compare = ls[i];
			int cvalue = max < compare ? -1 : (max == compare ? 0 : 1);
			if (cvalue < 0 || !flag)
				max = compare;
		}
		return max;
	}
	
	public static double max(double... ls) {
		double max = 0;
		boolean flag = false;
		for (int i = 0; i < ls.length; i++) {
			double compare = ls[i];
			int cvalue = max < compare ? -1 : (max == compare ? 0 : 1);
			if (cvalue < 0 || !flag)
				max = compare;
		}
		return max;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Comparable<T> max(Comparable<T>... ls) {
		Comparable<T>[] ar = removalNull(ls);
		if (UtilArray.isEmpty(ar))
			return null;
		Comparable max = null;
		boolean flag = false;
		for (int i = 0; i < ar.length; i++) {
			Comparable compare = ar[i];
			int cvalue = (max == null ? 0 : (max.compareTo(compare)));
			if (cvalue < 0 || !flag)
				max = compare;
		}
		return max;
	}
	
	private static <T> Comparable<T>[] removalNull(Comparable<T>... ls) {
		int length = ls.length;
		Comparable<T>[] ar = ls;
		for (int i = 0; i < length; i++) {
			if (ls[i] == null) {
				int index = i + 1;
				index = index >= length ? length -1 : index;
				int cend = length - index;
				System.arraycopy(ar, index, ar, i, cend);
			}
		}
		return ar;
	}
	
	public static long min(long... ls) {
		long min = 0;
		boolean flag = false;
		for (int i = 0; i < ls.length; i++) {
			long compare = ls[i];
			int cvalue = min < compare ? -1 : (min == compare ? 0 : 1);
			if (cvalue > 0 || !flag)
				min = compare;
		}
		return min;
	}
	
	public static double min(double... ls) {
		double min = 0;
		boolean flag = false;
		for (int i = 0; i < ls.length; i++) {
			double compare = ls[i];
			int cvalue = min < compare ? -1 : (min == compare ? 0 : 1);
			if (cvalue > 0 || !flag)
				min = compare;
		}
		return min;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Comparable<T> min(Comparable<T>... ls) {
		Comparable<T>[] ar = removalNull(ls);
		if (UtilArray.isEmpty(ar))
			return null;
		Comparable min = null;
		boolean flag = false;
		for (int i = 0; i < ar.length; i++) {
			Comparable compare = ar[i];
			int cvalue = (min == null ? 0 : (min.compareTo(compare)));
			if (cvalue > 0 || !flag)
				min = compare;
		}
		return min;
	}
	

	/**
	 * <p>Discription:[判断是否为奇数]</p>
	 * @author hypo  2012-03-14
	 */
	public static boolean isOdd(int i) {
		return (i & 1) != 0;
	}

	/**
	 * <p>Discription:[判断是否为偶数]</p>
	 * @author hypo  2012-03-14
	 */
	public static boolean isEven(int i) {
		return (i & 1) == 0;
	}

	/**
	 * 是否为整型  int or long
	 * @param expr
	 * @return
	 */
	public static boolean isInteger(String expr) {
		Matcher m = patInt.matcher(expr);
		return m.matches();
	}

	public static boolean isNotInteger(String expr) {
		return !isInteger(expr);
	}

	/**
	 * 是否为浮点型 float double
	 * @param expr
	 * @return
	 */
	public static boolean isFloat(String expr) {
		Matcher m = patFloat.matcher(expr);
		return m.matches();
	}

	public static boolean isNotFloat(String expr) {
		return !isFloat(expr);
	}

	/**
	 * 是否为字符串  由 "" 包含的串  \"\"
	 * @param expr
	 * @return
	 */
	public static boolean isString(String expr) {
		Matcher m = patString.matcher(expr);
		return m.matches();
	}

	/**
	 * 是否为美元   数字   $12345
	 * @param expr
	 * @return
	 */
	public static boolean isDollarString(String expr) {
		Matcher m = dollarString.matcher(expr);
		return m.matches();
	}
	
	//  取 商
	public static final long getQuotient(long dividend, long divisor) {
		return 0;
	}
	
	// 取余数 (模)
	public static final long getRemainder(long dividend, long divisor) {
		return 0;
	}

	private static final Pattern patInt = Pattern.compile("[+-]?\\d+");
	private static final Pattern patFloat = Pattern.compile("[+-]?\\d+\\x2E\\d+[Ee]?[1-9][0-9]*");
	private static final Pattern patString = Pattern.compile("\\x22(.*)\\x22");
	private static final Pattern dollarString = Pattern.compile("\\x24(\\d+)");

	public static boolean isNotPrecisionLoss(Object obj, Class<?> dstCls) {
		return !isPrecisionLoss(obj, dstCls);
	}

	/**
	 * <p>Discription:[对象转为目标类型是否造成精度损失]</p>
	 * obj 非数值类型或不能转换为数值型，则返回 true
	 * obj == null      true;
	 * obj == NaN		true;
	 * @param obj
	 * @param dstCls
	 * @return obj 超过目标最大或最小值，返回 true 否则返回 false
	 * @author zhanghaibo3  2016-2-6
	 */
	public static boolean isPrecisionLoss(Object obj, Class<?> dstCls) {
		Object objValue = obj;
		if (objValue == null)
			return true;
		Class<?> cls = Converter.getPrimitiveWrap(dstCls);
		if (objValue instanceof String || objValue instanceof BigDecimal) {
			if (cls.equals(BigDecimal.class))
				return false;
			BigDecimal bd = null;
			if (objValue instanceof String) {
				String s = objValue.toString().trim();
				if (!(isInteger(s) || isFloat(s)))
					return true;
				bd = new BigDecimal(s);
			} else {
				bd = (BigDecimal) objValue;
			}
			int scale = bd.scale();
			Object ovalue = null;
			if (scale == 0) {
				ovalue = bd.longValue();
			} else {
				ovalue = bd.doubleValue();
			}
			try {
				String s = Converter.convert(ovalue, String.class);
				String bds = Converter.convert(bd, String.class);
				if (!s.equals(bds)) // BigDecimal 转  long double 时有没有精度丢失
					return true;
				objValue = ovalue;
			} catch (ConversionException ex) {
				return true;
			}
		}

		if (objValue instanceof Integer || objValue instanceof Short || objValue instanceof Byte || objValue instanceof Long) {
			Long value = (Long) objValue;
			if (cls == Integer.class || cls == int.class) {
				if (value <= Integer.MAX_VALUE || value >= Integer.MIN_VALUE)
					return false;
			} else if (cls == Short.class || cls == short.class) {
				if (value <= Short.MAX_VALUE || value >= Short.MIN_VALUE)
					return false;
			} else if (cls == Byte.class || cls == byte.class) {
				if (value <= Byte.MAX_VALUE || value >= Byte.MIN_VALUE)
					return false;
			} else if (cls == Float.class || cls == float.class) {
				if (value <= Float.MAX_VALUE || value >= Float.MIN_VALUE)
					return false;
			} else if (cls == Double.class || cls == double.class) {
				if (value <= Double.MAX_VALUE || value >= Double.MIN_VALUE)
					return false;
			} else if (cls.equals(BigDecimal.class)) {
				return false;
			} else if (cls == Long.class || cls == long.class) {
				if (value <= Long.MAX_VALUE || value >= Long.MIN_VALUE)
					return false;
			}
		} else if (objValue instanceof Float || objValue instanceof Double) {
			Double dvalue = (Double) objValue;
			if (Double.isNaN(dvalue))
				return true;
			if (cls == Integer.class || cls == int.class) {
				if (dvalue <= Integer.MAX_VALUE || dvalue >= Integer.MIN_VALUE)
					return false;
			} else if (cls == Short.class || cls == short.class) {
				if (dvalue <= Short.MAX_VALUE || dvalue >= Short.MIN_VALUE)
					return false;
			} else if (cls == Byte.class || cls == byte.class) {
				if (dvalue <= Byte.MAX_VALUE || dvalue >= Byte.MIN_VALUE)
					return false;
			} else if (cls == Long.class || cls == long.class) {
				if (dvalue <= Long.MAX_VALUE || dvalue >= Long.MIN_VALUE)
					return false;
			} else if (cls == Float.class || cls == float.class) {
				if (dvalue <= Float.MAX_VALUE || dvalue >= Float.MIN_VALUE)
					return false;
			} else if (cls.equals(BigDecimal.class)) {
				return false;
			} else if (cls == Double.class || cls == double.class) {
				if (dvalue <= Double.MAX_VALUE || dvalue >= Double.MIN_VALUE)
					return false;
			}
		}
		return true;
	}
		
	public static String formatNumeric(Object obj) {
		BigDecimal bd = null;
		if (obj instanceof Float || obj instanceof Double || obj instanceof Long 
		 || obj instanceof Integer || obj instanceof Short || obj instanceof Byte)
			bd = new BigDecimal(obj.toString());
		else if (obj instanceof BigDecimal)
			bd = (BigDecimal) obj;
		int size = bd == null ? 2 : bd.scale();
		PadAtChar pad = UtilString.padAtChar;
		String pads = pad.rightPad("", size, "#");
		String format = "#";
		format = UtilObject.isEmpty(pads) ? format : format.concat(".").concat(pads);
		DecimalFormat df = new DecimalFormat(format);
		return df.format(obj);
	}

	// 1.0-0.9 存在精度问题，最好用  BigDecimal 进行运算
}
