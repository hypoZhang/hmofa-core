package com.hmofa.core.lang.bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hmofa.core.exception.CloneNotSupportedException;
import com.hmofa.core.lang.ICloneable;
import com.hmofa.core.lang.tuple.KeyValue;
import com.hmofa.core.lang.utils.UtilString;

public class StringFormat implements ICloneable<StringFormat> {
	
	public static String format(String src, Object... values) {
		//{}
		return null;
	}
	
	public static String formatSql(String src, Object... values) {
		// ?
		return null;
	}
	
	public static String formatMsg(String src, Object... values) {
		//{x} ${}
		return null;
	}
	
	public static String formatParm(String src, String name, String value, String... kvs) {
		// :xxx
		return null;
	}
	
	public String getFormatString(Object... args) {
		return null;
	}
	
	public StringFormat(CharSequence string, String placeholder) {
		this(string, Placeholder.valueOfs(placeholder));
	}

	public StringFormat(CharSequence string, Placeholder placeholder) {
		this.placeholder = placeholder;
		this.originalString = string == null ? "" : string.toString();
		
	}
	
	public StringFormat clone() throws CloneNotSupportedException {
		return null;
	}
	
	private void compile() {
		List<KeyValue<Integer, String>> kv = UtilString.find(originalString, placeholder.pattern());
				
	}
	
	private Placeholder placeholder;  // 占位符
	private String originalString;    // 原字符串
	
	
	// 占位符总结:     ?  {}  ${name}   :name
	// 1、支持占位符 参数位置替换
	// 2、支持参数名称替换
	// 3、支持javaBean属性名替换 (名称替换的变种)
	// 4、支持占位符编号，编号必须是正整数 (占位符位置变种)
	
}
