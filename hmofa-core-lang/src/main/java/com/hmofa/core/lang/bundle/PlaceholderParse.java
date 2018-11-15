package com.hmofa.core.lang.bundle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hmofa.core.exception.BaseRuntimeException;
import com.hmofa.core.lang.exception.Assert;
import com.hmofa.core.lang.tuple.KeyValue;
import com.hmofa.core.lang.tuple.Tuple;
import com.hmofa.core.lang.utils.UtilString;

/**
 * 没有参数名称的 占位符 位，以 编号 作为名称
 * 
 * <dd>Description:[占位符解析]</dd>
 * <dt>PlaceholderParse</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2018-11-14</dd>
 * @version 1.0
 * @author hypo zhang
 */
public class PlaceholderParse implements Iterable<KeyValue<String, Boolean>> {

	public PlaceholderParse(CharSequence string, String placeholderName) {
		this(string, Placeholder.valueOfs(placeholderName));
	}

	public PlaceholderParse(CharSequence string, Placeholder placeholder) {
		Assert.notNull(placeholder);
		this.placeholder = placeholder;
		this.originalString = string == null ? "" : string.toString();

		compile();
	}

	private void compile() {				
		int beginIndex = 0;
		List<KeyValue<Integer, String>> kvList = UtilString.find(originalString, placeholder.pattern());
		if(kvList.isEmpty())
			return;
		
		for (KeyValue<Integer, String> kv : kvList) {
			Integer endIndex = kv.getKey();
			String placeholderStr = kv.getValue();
			String str = originalString.substring(beginIndex, endIndex);
			compileList.add(Tuple.withKV(str, false));
			
			beginIndex = endIndex + placeholderStr.length();
			
			KeyValue<String, Boolean> placeholderTup = Tuple.withKV(placeholderStr, true);
			compileList.add(placeholderTup);
		}
		
		StringBuilder sb = new StringBuilder();
		for (KeyValue<String, Boolean> kv : compileList) {
			sb.append(kv.getKey());
		}
		if (!sb.toString().equals(this.originalString))
			throw new BaseRuntimeException("编译字符串时发生异常，编译结果与原字符不相符。")
					.addAttribute("originalString", originalString)
					.addAttribute("compileString", sb.toString());
		
		
		int max = 0;
//		max = findMax(max);
		for (KeyValue<String, Boolean> kv : compileList) {
			if (!kv.getValue())
				continue;

			KeyValue<String, Boolean> paramKv = null;
			String placeholderStr = kv.getKey();
			if (isPaddingNo(kv.getKey())) {
				paramKv = new KeyValue<String, Boolean>(Integer.toString(max), true);
				max++;
			} else {
				String paramName = null;
				if (placeholder.isPair())
					paramName = placeholderStr.substring(placeholder.getPrefix().length(), placeholder.getSuffix().length());
				else
					paramName = placeholderStr.substring(placeholder.getPrefix().length(), placeholderStr.length());
				paramKv = new KeyValue<String, Boolean>(paramName, false);
			}
			placeholderMap.put(kv.getTupleUUID(), paramKv);
		}
	}

	private boolean isPaddingNo(String holderString) {
		return placeholder.isNoHaveName() || (placeholder.isNotEssential() && placeholder.holderString().equals(holderString));
	}

	private Placeholder placeholder; // 占位符
	private String originalString; // 原字符串
	
	// 当前字符解析结果   key = string, value = placeholder {yes or no}  true = placeholder
	private List<KeyValue<String, Boolean>> compileList = new ArrayList<KeyValue<String, Boolean>>(); 
	
	 // key = uuid, value = (key = paramName, value = self num or sys num)  true = sys
	private Map<String, KeyValue<String, Boolean>> placeholderMap = new LinkedHashMap<String, KeyValue<String, Boolean>>();  

	/** key = paramName, value = true 系统编号  false 自编号 */
	public KeyValue<String, Boolean> getParamNameBy(String placeholderUUID) {
		return placeholderMap.get(placeholderUUID);
	}
	
	public String getOriginalString() {
		return originalString;
	}
	
	public boolean isEmpty() {
		return compileList.isEmpty();
	}

	public Iterator<KeyValue<String, Boolean>> iterator() {
		return compileList.iterator();
	}
	
	public String toAlreadyCodeParamNameString() {
		StringBuilder sb = new StringBuilder();
		for (KeyValue<String, Boolean> kv : compileList) {
			if (!kv.getValue()) {
				sb.append(kv.getKey());
				continue;
			}
			
			KeyValue<String, Boolean> kvParam = getParamNameBy(kv.getTupleUUID());
			if (placeholder.isPair()) {
				sb.append(placeholder.getPrefix());
				sb.append(kvParam.getKey());
				sb.append(placeholder.getSuffix());
			} else {
				sb.append("<");
				sb.append(kvParam.getKey());
				sb.append(">");
			}
		}
		return sb.toString();
	}
	
	public StringFormat stringFormat() {
		return new StringFormat(this);
	}
	
	public StringFormat stringFormat(boolean append) {
		return new StringFormat(this, append);
	}
	
	public StringFormat stringFormat(boolean append, String appendSeparator) {
		return new StringFormat(this, append, appendSeparator);
	}
		
//	private int findMax(int max) {
//		if (!placeholder.isHaveName()) {
//			// 找可有可无 ，已有参数 最大编号
//			for (KeyValue<String, Boolean> kv : compileList) {
//				String paramName = null;
//				String placeholderStr = kv.getKey();
//				if (placeholder.isNotEssential() && !placeholder.holderString().equals(placeholderStr)) {
//					if (placeholder.isPair())
//						paramName = placeholderStr.substring(placeholder.getPrefix().length(), placeholder.getSuffix().length());
//					else
//						paramName = placeholderStr.substring(placeholder.getPrefix().length(), placeholderStr.length());
//					
//					if (UtilNumber.isInteger(paramName)) {
//						int cur = Integer.parseInt(paramName);
//						if (max == -1 || max < cur)
//							max = cur;
//					}
//				}
//			}
//			max = max + 1;
//		}
//		return max;
//	}
	
	/** ? */
	public static final PlaceholderParse sqlParse(String src) {
		return new PlaceholderParse(src, Placeholder.SQL);
	}
	
	/** {} {xxx} */
	public static final PlaceholderParse messageParse(String src) {
		return new PlaceholderParse(src, Placeholder.MSG);
	}
	
	/** ${xxx} */
	public static final PlaceholderParse variableParse(String src) {
		return new PlaceholderParse(src, Placeholder.VAR);
	}
	
	/** :xxx */
	public static final PlaceholderParse paramNameParse(String src) {
		return new PlaceholderParse(src, Placeholder.PARAMNAME);
	}
	
	/* 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
}
