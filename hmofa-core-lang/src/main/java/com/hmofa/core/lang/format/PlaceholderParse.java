package com.hmofa.core.lang.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hmofa.core.exception.BaseRuntimeException;
import com.hmofa.core.lang.exception.Assert;
import com.hmofa.core.lang.tuple.KeyValue;
import com.hmofa.core.lang.utils.UtilCollection;
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
public class PlaceholderParse implements Serializable, Iterable<Compileholder> {

	private static final long serialVersionUID = -5458290296507277391L;

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
			if (str.length() > 0)
				compileList.add(new Compileholder(str, false, null));
			
			beginIndex = endIndex + placeholderStr.length();
			
			compileList.add(new Compileholder(placeholderStr, true, placeholder));
		}
		if(beginIndex < originalString.length()) {
			String str = originalString.substring(beginIndex, originalString.length());
			compileList.add(new Compileholder(str, false, null));
		}
		
		StringBuilder sb = new StringBuilder();
		for (Compileholder kv : compileList) {
			sb.append(kv.getString());
		}
		if (!sb.toString().equals(this.originalString))
			throw new BaseRuntimeException("编译字符串时发生异常，编译结果与原字符不相符。")
					.addAttribute("originalString", originalString)
					.addAttribute("compileString", sb.toString());
		
		
		int max = 0;
//		max = findMax(max);
		
		Map<String, VarNameholder> theMap = new LinkedHashMap<String, VarNameholder>();  
		for (Compileholder holder : compileList) {
			if (!holder.isPlaceholder())
				continue;

			VarNameholder varHolder = null;
			String varName = holder.getVarName();
			if (varName == null) {
				varHolder = new VarNameholder(Integer.toString(max), true);
				max++;
			} else {
				varHolder = new VarNameholder(varName, false);
			}
			theMap.put(holder.getID(), varHolder);
		}
		this.placeholderMap = Collections.unmodifiableMap(theMap);
	}

	private Placeholder placeholder; // 占位符
	private String originalString; // 原字符串
	
	// 当前字符解析结果   
	private List<Compileholder> compileList = new ArrayList<Compileholder>(); 
	
	 // key = uuid, value = (key = paramName, value = self num or sys num)  true = sys
	private Map<String, VarNameholder> placeholderMap;

	/** key = paramName, value = true 系统编号  false 自编号 */
	public VarNameholder getVarNameBy(String placeholderUUID) {
		return placeholderMap.get(placeholderUUID);
	}
	
	public Map<String, VarNameholder> getVarNameholderMap() {
		return placeholderMap;
	}
	
	public List<VarNameholder> getVarNameholders() {
		return UtilCollection.newListV(placeholderMap);
	}
	
	public String getOriginalString() {
		return originalString;
	}
	
	public boolean isContainsPlaceholder() {
		return compileList.isEmpty();
	}

	public Iterator<Compileholder> iterator() {
		return compileList.iterator();
	}
	
	public String toAlreadyCodeParamNameString() {
		StringBuilder sb = new StringBuilder();
		for (Compileholder kv : compileList) {
			if (!kv.isPlaceholder()) {
				sb.append(kv.getString());
				continue;
			}
			
			VarNameholder kvParam = getVarNameBy(kv.getID());
			if (placeholder.isPair()) {
				sb.append(placeholder.getPrefix());
				sb.append(kvParam.getString()).append("[").append(kvParam.isAutoNO()).append("]");
				sb.append(placeholder.getSuffix());
			} else {
				sb.append("<");
				sb.append(kvParam.getString()).append("[").append(kvParam.isAutoNO()).append("]");
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
	 * 占位符分类：
	 *    1、占位符有自定义名；
	 *    2、占位符无自定义名；
	 *    3、占位符自定义名，可有可无；
	 * 
	 * 【自定义名】 ： 指占位符 内指定的名称。例： 'select * from ${table} where id = ${0}' ; table 和  0 都表示自定义名
	 * 
	 * 占位符注意事项：
	 * 	   1、 必须有前缀；
	 *     2、正则表达式不能为空，并书写正确；
	 *     3、正则表达式的行为，必须与你指定的 占位符分类相符；  如占位符 {x} 又可以 {} ,则类型为 可有可无 
	 * 
	 * =======================================
	 * 格式化：
	 *     1、支持按  占位符出现顺序  编号；
	 *     2、支持按自定义名 替换；
	 *     
	 *     3、未指定自定义名   自动编号，从 0 开始；
	 *     4、自动编号 参数，支持 从末尾 追加；
	 *     
	 *     5、自动编号参数 替换值，与自定义参数替换值 相互独立
	 *     6、未指定 参数对应 替换值，则按原占位符信息输出
	 * 
	 */
}
