package com.hmofa.core.lang.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hmofa.core.lang.coder.StringCoder;
import com.hmofa.core.lang.converter.Converter;
import com.hmofa.core.lang.helper.PadAtByte;
import com.hmofa.core.lang.helper.PadAtChar;
import com.hmofa.core.lang.tuple.KeyValue;
import com.hmofa.core.lang.tuple.Tuple;



/**
 * <dd>Description:[字符串工具]</dd>
 * <dt>UtilString</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2015-11-2</dd>
 * @version 1.0
 * @author 张海波
 */
public class UtilString {

	public static final boolean isNotNull(CharSequence str) {
		return !isNull(str);
	}
	
	public static final boolean isNull(CharSequence str) {
		return str == null;
	}
	
	public static final boolean isNotEmpty(CharSequence str) {
		return !isEmpty(str);
	}
	
	/**
	 * <p>Discription:[是否为空]</p>
	 * <pre>
	 * isEmpty(null)     = true
	 * isEmpty("")       = true
	 * isEmpty("    ")   = false	空格
	 * isEmpty(" bob ")  = false
	 * isEmpty("\t \n \f \r \b") = false	制表\换行\换页\回车\退格 等转义符
	 * </pre>
	 * @param str
	 * @return 空 true , 非空 false
	 * @author zhanghaibo3  2015-11-2
	 */
	public static final boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}
	
	public static final boolean isNotWhitespace(CharSequence str) {
		return !isWhitespace(str);
	}

	/**
	 * <p>Discription:[是否为空]</p>
	 * <pre>
	 * isWhitespace(null)     = true
	 * isWhitespace("")       = true
	 * isWhitespace("    ")   = true	空格
	 * isWhitespace(" bob ")  = false
	 * isWhitespace("\t \n \f \r \b") = true  制表\换行\换页\回车\退格 等转义符
	 * </pre>
	 * @param str
	 * @return 空 true , 非空 false
	 * @author zhanghaibo3  2015-11-2
	 */
	public static final boolean isWhitespace(CharSequence str) {
		if (isEmpty(str))
			return true;
		BigDecimal length = new BigDecimal(str.length());
		int center = length.divide(divnum2, 0, RoundingMode.HALF_UP).intValue(); // 从前和中间开始取，减少循环
		for (int index = 0; index < center; index++) {
			char ch1 = str.charAt(index);
			int index2 = index + center;
			index2 = index2 >= str.length() ? str.length() - 1 : index2;
			char ch2 = str.charAt(index2);
			if (!Character.isWhitespace(ch1) || !Character.isWhitespace(ch2))
				return false;
		}
		return true;
	}
	
	private static final BigDecimal divnum2 = new BigDecimal("2");
	
	public static final boolean equals(CharSequence str1, CharSequence str2) {
		return str1 == null ? str2 == null : str2 == null ? false : str1.toString().equals(str2.toString());
	}
	
	public static final boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
		return str1 == null ? str2 == null : str2 == null ? false : str1.toString().equalsIgnoreCase(str2.toString());
	}
	
	public static final String replace(String inString, CharSequence target, CharSequence replacement) {
		if (isEmpty(inString) || isEmpty(target) || replacement == null)
			return inString;
		return inString.replace(target, replacement);
	}
	
	public static int lengthB(CharSequence csq) {
		return lengthB(csq, StringCoder.UTF_8);
	}
	
	public static int lengthB(CharSequence csq, Charset charset) {
		return lengthB(csq, StringCoder.charsetName(charset));
	}
	
	/**
	 * <p>Discription:[统计字符串 按指定编码 字节数]</p>
	 * @param csq
	 * @param charsetName  utf8 utf16 gbk
	 * @return
	 * @author zhanghaibo3  2016-12-30
	 */
	public static int lengthB(CharSequence csq, String charsetName) {
		int total = 0;
		String ncharset = charsetName == null ? "utf-8" : charsetName.toLowerCase();
		String txt = csq.toString();
		if ("utf-16".equalsIgnoreCase(ncharset) || "utf16".equalsIgnoreCase(ncharset)) {
			for (int i = 0, len = csq.length(); i < len; i++) {
				int charCode = txt.codePointAt(i);
				if (charCode <= 0xffff) { // 65535
					total += 2;
				} else {
					total += 4;
				}
			}
		} else if ("utf-8".equalsIgnoreCase(ncharset) || "utf8".equalsIgnoreCase(ncharset)) {
			for (int i = 0, len = txt.length(); i < len; i++) {
				int charCode = txt.codePointAt(i);
				if (charCode <= 0x007f) { // 127
					total += 1;
				} else if (charCode > 0x007f && charCode <= 0x07ff) { // 2047
					total += 2;
				} else if (charCode > 0x07ff && charCode <= 0xffff) { // 65535
					total += 3;
				} else {
					total += 4;
				}
			}
		} else if ("gbk".equalsIgnoreCase(ncharset) || "gb2312".equalsIgnoreCase(ncharset) || "big5".equalsIgnoreCase(ncharset)) {
			for (int i = 0, len = txt.length(); i < len; i++) {
				int charCode = txt.codePointAt(i);
				if (charCode <= 0x007f) {// 127
					total += 1;
				} else {
					total += 2;
				}
			}
		}
		total = total == 0 ? StringCoder.encode(txt, ncharset).length : total;
		return total;
	}
	
	/**
	 * <p>Discription:[是否Ascii全角字符]</p>
	 * <pre>
	 * ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ  		 -- 全角
	 * ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ　		 -- 全角
	 * ～｀！＠＃＄％＾＆＊（）－＿＋＝｛｝［］｜＼：；＂＇＜＞，．？　／  -- 全角
	 * 
	 * abcdefghijklmnopqrstuvwxyz  				 -- 半角
	 * ABCDEFGHIJKLMNOPQRSTUVWXYZ  				 -- 半角
	 * ~`!@#$%^&*()-_+={}[]|\:;"'<>,.? /		 -- 半角
	 * </pre>
	 * @param ch
	 * @return  全角  true 半角 false
	 * @author zhanghaibo3  2016-10-10
	 */
	public static boolean isAsciiFullAngle(char ch) {
		return (ch >= 65281 && ch <= 65374) || ch == 12288;
	}
	
	/**
	 * <p>Discription:[是否Ascii半角字符]</p>
	 * @param ch
	 * @return 半角  true  全角  false
	 * @author zhanghaibo3  2016-10-10
	 */
	public static boolean isAsciiHalfAngle(char ch) {
		return (ch >= 33 && ch <= 126) || ch == 32;
	}
	
	
	public static boolean containsAsciiFullAngle(String str) {
		if (isEmpty(str))
			return false;
		for (int i = 0, len = str.length(); i < len; i++) {
			if (isAsciiFullAngle(str.charAt(i)))
				return true;
		}
		return false;
	}
	
	public static boolean containsAsciiHalfAngle(String str) {
		if (isEmpty(str))
			return false;
		for (int i = 0, len = str.length(); i < len; i++) {
			if (isAsciiHalfAngle(str.charAt(i)))
				return true;
		}
		return false;
	}
	
	
	/**
	 * <p>Discription:[Ascii半角字符转换为全角字符]</p>
	 * @param ch
	 * @return 如果是半角字符则转换为全角，否则原值返回
	 * @author zhanghaibo3  2016-10-10
	 */
	public static char toAsciiFullAngle(char ch) {
		char chs = ch;
		if (isAsciiHalfAngle(chs))
			chs = chs == 32 ? 12288 : (char) (chs + 65248);
		return chs;
	}
	
	/**
	 * <p>Discription:[Ascii全角字符转换为半角字符]</p>
	 * @param ch
	 * @return 如果是全角字符则转换为半角，否则原值返回
	 * @author zhanghaibo3  2016-10-10
	 */
	public static char toAsciiHalfAngle(char ch) {
		char chs = ch;
		if (isAsciiFullAngle(chs))
			chs = chs == 12288 ? 32 : (char) (chs - 65248);
		return chs;
	}
	
	
	public static String toAsciiFullAngle(CharSequence inString) {
		if (inString == null)
			return null;
		StringBuilder sb = new StringBuilder(inString.length());
		for (int i = 0; i < inString.length(); i++)
			sb.append(toAsciiFullAngle(inString.charAt(i)));
		return sb.toString();
	}
	
	public static String toAsciiHalfAngle(CharSequence inString) {
		if (inString == null)
			return null;
		StringBuilder sb = new StringBuilder(inString.length());
		for (int i = 0; i < inString.length(); i++)
			sb.append(toAsciiHalfAngle(inString.charAt(i)));
		return sb.toString();
	}
	
	
	
	/**
	 * <p>Discription:[查找 正则表达式值 在字符串所出现的索引位置]</p>
	 * @param src		源字符串
	 * @param pattern	正则表达式
	 * @return			没找到则返回  int[0]
	 * @author 张海波  2015-12-10
	 */
	public static int[] findIndex(String src, String pattern) {
		if (isEmpty(src) || isEmpty(pattern))
			return UtilArray.EMPTY_INT_ARRAY;
		Pattern p = Pattern.compile(pattern);
		return findIndex(src, p);
	}

	public static int[] findIndex(String src, Pattern pattern) {
		List<KeyValue<Integer, String>> pstr = find(src, pattern);
		int[] arr = new int[pstr.size()];
		for (int i = 0; i < pstr.size(); i++) {
			KeyValue<Integer, String> kv = pstr.get(i);
			arr[i] = kv.getKey();
		}
		return arr;
	}

	public static String[] findContent(String src, String pattern) {
		if (isEmpty(src) || isEmpty(pattern))
			return UtilArray.EMPTY_STRING_ARRAY;
		Pattern p = Pattern.compile(pattern);
		return findContent(src, p);
	}

	public static String[] findContent(String src, Pattern pattern) {
		List<KeyValue<Integer, String>> pstr = find(src, pattern);
		String[] arr = new String[pstr.size()];
		for (int i = 0; i < pstr.size(); i++) {
			KeyValue<Integer, String> kv = pstr.get(i);
			arr[i] = kv.getValue();
		}
		return arr;
	}
	
	public static List<KeyValue<Integer, String>> find(String src, String pattern) {
		if (isEmpty(src) || isEmpty(pattern))
			return new ArrayList<KeyValue<Integer, String>>();
		Pattern p = Pattern.compile(pattern);
		return find(src, p);
	}

	public static List<KeyValue<Integer, String>> find(String str, Pattern pattern) {
		List<KeyValue<Integer, String>> list = new ArrayList<KeyValue<Integer, String>>();
		if (isEmpty(str) || pattern == null)
			return list;
		Matcher m = pattern.matcher(str);
		while (m.find())
			list.add(Tuple.withKV(m.start(), m.group()));
		return list;
	}
	
	public static boolean isJPFullAngle(char ch) {
		if (isAsciiFullAngle(ch))
			return true;
		if ((ch >= 65377 && ch <= 65439)) // 日文全角，对应的半角暂不清楚
			return true;
		return false;
	}
	
	public static final PadAtChar padAtChar = new PadAtChar();

	public static String leftPad(CharSequence csq, int length, CharSequence padValue) {
		return padAtChar.leftPad(csq, length, padValue);
	}

	public static String rightPad(CharSequence csq, int length, CharSequence padValue) {
		return padAtChar.rightPad(csq, length, padValue);
	}

	public static String leftPadB(CharSequence csq, int length, CharSequence padValue, String charsetName) {
		PadAtByte padAtByte = new PadAtByte(charsetName);
		return padAtByte.leftPad(csq, length, padValue);
	}

	public static String rightPadB(CharSequence csq, int length, CharSequence padValue, String charsetName) {
		PadAtByte padAtByte = new PadAtByte(charsetName);
		return padAtByte.rightPad(csq, length, padValue);
	}

	/**
	 * <p>Discription:[集合列表转换为 String[]</p>
	 * @param collection
	 * @return
	 * @author zhanghaibo3  2015-12-8
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null)
			return UtilArray.EMPTY_STRING_ARRAY;
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * <p>Discription:[枚举转换为 String[]]</p>
	 * @param enumeration
	 * @return
	 * @author zhanghaibo3  2016-1-21
	 */
	public static String[] toStringArray(Enumeration<?> enumeration) {
		if (enumeration == null)
			return UtilArray.EMPTY_STRING_ARRAY;
		List<String> list = new ArrayList<String>();
		while (enumeration.hasMoreElements())
			list.add(Converter.convert(enumeration.nextElement(), String.class));
		return list.toArray(new String[list.size()]);
	}

}
