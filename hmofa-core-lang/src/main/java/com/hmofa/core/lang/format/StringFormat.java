package com.hmofa.core.lang.format;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hmofa.core.exception.BaseRuntimeException;
import com.hmofa.core.exception.CloneNotSupportedException;
import com.hmofa.core.lang.ICloneable;


/**
 * 
 * <dd>Description:[格式化 字符串]</dd>
 * <dt>StringFormat</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2018-11-14</dd>
 * @version 1.0
 * @author hypo zhang
 */
public class StringFormat implements Serializable, ICloneable<StringFormat> {
	
	private static final long serialVersionUID = 6985636127148740390L;

	public static String format(String src, Object... values) {
		return formatMsg(src, values);
	}
	
	public static String format(String src, boolean append, Object... values) {
		return formatMsg(src, append, values);
	}
	
	/**
	 *  自定编号   01 = 001  零开头，不管几个零 等价
	 */
	public static String formatMsg(String src, Object... values) {
		return formatMsg(src, false, values);
	}
	
	public static String formatMsg(String src, boolean append, Object... values) {
		return PlaceholderParse.messageParse(src).stringFormat().addFormatValue(values).toString();
	}
	
	
	public static String formatSql(String src, Object... values) {
		return PlaceholderParse.sqlParse(src).stringFormat().addFormatValue(values).toString();
	}
	
	public static String formatParm(String src, String name, Object value, Object... nameValuePair) {
		StringFormat stringFormat = PlaceholderParse.paramNameParse(src).stringFormat();
		addValue(stringFormat, (Object) name, value, nameValuePair);
		return stringFormat.toString();
	}

	public static String formatVar(String src, String name, Object value, Object... nameValuePair) {
		StringFormat stringFormat = PlaceholderParse.variableParse(src).stringFormat();
		addValue(stringFormat, (Object) name, value, nameValuePair);
		return stringFormat.toString();
	}
	
	private static void addValue(StringFormat stringFormat, Object...nameValuePair ) {
		for (int i = 0; i < nameValuePair.length; i += 2) {
			stringFormat.addFormatValueBy(nameValuePair[i] == null ? "" : nameValuePair[i].toString(), nameValuePair[i+1]);
		}
	}
		
	// 分自编号，和系统编号。 自编号 与 指定参数名  相同， 系统编号   为顺序索引位
	
	protected StringFormat(PlaceholderParse placeholderParse) {
		this(placeholderParse, false);
	}
	
	protected StringFormat(PlaceholderParse placeholderParse, boolean append) {
		this(placeholderParse, append, CONSTANT_APPEND_SEPARATOR);
	}
	
	protected StringFormat(PlaceholderParse placeholderParse, boolean append, String appendSeparator) {
		this.parse = placeholderParse;
		this.append = append;
		this.appendSeparator = appendSeparator == null ? "" : appendSeparator;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		Set<VarNameholder> paramAlready = this.append ? new HashSet<VarNameholder>() : null;
		
		Iterator<Compileholder> it = parse.iterator();
		while (it.hasNext()) {
			Compileholder kv = it.next();
			if (!kv.isPlaceholder()) {
				sb.append(kv.getString());
				continue;
			}
			
			String placeholderStr = kv.getString();
			VarNameholder paramName = parse.getVarNameBy(kv.getID());
			
			// 允许替换值为 null， 设置了参数，则取参数值，否则用替换字符
			Object value = !repNameValMap.containsKey(paramName) ? placeholderStr : repNameValMap.get(paramName);
			sb.append(value == null ? "" : value);
			
			if (paramAlready != null)
				paramAlready.add(paramName);
		}
		// 添加追加  值
		if (paramAlready != null) {
			Iterator<VarNameholder> its = repNameValMap.keySet().iterator();
			while (its.hasNext()) {
				VarNameholder paramName = its.next();
				if (paramName.isAutoNO() && !paramAlready.contains(paramName)) {
					Object value = repNameValMap.get(paramName);
					sb.append(value == null ? "" : appendSeparator);
					sb.append(value == null ? "" : value);
				}
			}
		}
		return sb.toString().trim();
	}
	
	public List<VarNameholder> getVarNameholders() {
		return parse.getVarNameholders();
	}
	
	/**
	 * <p>Discription:[未设置值的 占位符]</p>
	 * @return
	 * @author hypo zhang  2018-11-15
	 */
	public List<VarNameholder> remainVarNameholder() {
		
		List<VarNameholder> remain = new ArrayList<VarNameholder>();
		Iterator<Compileholder> it = parse.iterator();
		while (it.hasNext()) {
			Compileholder kv = it.next();
			if (!kv.isPlaceholder()) 
				continue;
			
			VarNameholder varName = parse.getVarNameBy(kv.getID());
			if(!repNameValMap.containsKey(varName))
				remain.add(varName);
		}
		return remain;
	}
	
	public StringFormat clone() throws CloneNotSupportedException {
		StringFormat format = new StringFormat(this.parse);
		format.repNameValMap.putAll(this.repNameValMap);
		return format;
	}
	
	/**
	 * 从 0 开始  编号， 建立参数名映射  (用于系统编号)
	 * <p>Discription:[按参数名 添加 替换的值]</p>
	 * @param values  允许 为  Null
	 * @return
	 * @author hypo zhang  2018-11-14
	 */
	public final StringFormat addFormatValue(Object... values) {
		int index = 0;
		for (Object value : values) {
			addFormatValue(index, value);
			index++;
		}
		return this;
	}
	
	/**
	 * <p>Discription:[用于系统编号，设置替换值]</p>
	 * @param index
	 * @param value
	 * @return
	 * @author hypo zhang  2018-11-15
	 */
	public final StringFormat addFormatValue(int index, Object value) {
		repNameValMap.put(new VarNameholder(Integer.toString(index), true), value);
		return this;
	}
		
	public final StringFormat addFormatValueBy(int paramName, Object value) {
		return addFormatValueBy(Integer.toString(paramName), value);
	}
	
	/**
	 * <p>Discription:[自编号，设置替换值]</p>
	 * @param paramName
	 * @param value
	 * @return
	 * @author hypo zhang  2018-11-15
	 */
	public final StringFormat addFormatValueBy(String paramName, Object value) {
		if (paramName == null || paramName.length() == 0)
			throw new BaseRuntimeException("参数名不能为空。");
		repNameValMap.put(new VarNameholder(paramName, false), value);
		return this;
	}
	
	public void clear() {
		repNameValMap.clear();
	}
	
	public PlaceholderParse getPlaceholderParse() {
		return parse;
	}
	
	private Map<VarNameholder, Object> repNameValMap = new LinkedHashMap<VarNameholder, Object>();
	
	private PlaceholderParse parse;
	
	private boolean append = false;  // 追加在 末尾  (系统编号才追加)
	
	private String appendSeparator = CONSTANT_APPEND_SEPARATOR;   // 追加 分隔符
	
	private static final String CONSTANT_APPEND_SEPARATOR = " ";
	
	// 占位符总结:     ?  {}  ${name}   :name
	// 1、支持占位符 参数位置替换
	// 2、支持参数名称替换
	// 3、支持javaBean属性名替换 (名称替换的变种)
	// 4、支持占位符编号，编号必须是正整数 (占位符位置变种)
	
}
