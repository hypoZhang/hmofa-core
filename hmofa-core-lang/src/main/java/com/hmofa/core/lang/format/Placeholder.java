package com.hmofa.core.lang.format;

import java.util.regex.Pattern;

import com.hmofa.core.exception.BaseRuntimeException;

/**
 * 分为有参数名称占位符， 无参数名占位符。 <br>
 * 
 * <dd>Description:[占位符定义]</dd>
 * <dt>Placeholder</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2018-11-14</dd>
 * @version 1.0
 * @author hypo zhang
 */
public enum Placeholder {
	
	/** ?  <br>  [通常 sql 表达式用到的占位符] */
	SQL("?", null, Pattern.compile("\\?"), 1),

	/** {}  <br> 字符串替换符     (允许编号，编号应该为正整数, 允许 0 开头) */
	MSG("{", "}", Pattern.compile("\\{[0]*[1-9][0-9]*\\}|\\{\\}"), 2),

	/** ${} <br> 通常用于表示变量     (只允许英文字母 和 点 及下划线)  必须有一到多个字符, 允许 . 或  _ 开头*/
	VAR("${", "}", Pattern.compile("\\$\\{[a-z|A-Z|.|_]+\\}"), 0),
	
	/** 字符串替换符     按名称替换 (参数名, 只允许英文字母 和 点 及下划线) <br><br>
	 * :[_]*[a-z|A-Z][a-z|A-Z|.|_]* &nbsp; [例  :_env.name ]   <br><br>
	 * 必须有一到多个字符, _ 允许开头, 必须有一个 英文字符 才能使用  . 
	 */
	PARAMNAME(":", null, Pattern.compile(":[_]*[a-z|A-Z][a-z|A-Z|.|_]*"), 0)
	;

	private static final int HAVENAME = 0;
	private static final int NO_HAVENAME = 1;
	private static final int NOT_ESSENTIAL = 2;
	
	private final String holderStr;	 // 占位符 全字符
	private final Pattern pattern;   // 匹配占位符 的正则表达试
	private final String prefix;	 // 前缀 (不为空)
	private final String suffix;     // 后缀
	private final int haveName;	 	 // 是否  名称 有参数名称
	private final int size;			 // 占位符  字符总长度
		
	private final boolean pair;		 // 是否成对

	private Placeholder(String prefix, String suffix, Pattern pattern, int haveName) {
		this.pattern = pattern;
		this.prefix = prefix;
		this.suffix = suffix == null || suffix.length() == 0 ? null : suffix;
		this.haveName = haveName;
				
		if (this.prefix == null)
			throw new BaseRuntimeException("占位符  不能为空。");
		if (this.pattern == null)
			throw new BaseRuntimeException("占位符  正则表达式不能为空。");
		
		this.holderStr = suffix == null || suffix.length() == 0 ? prefix : prefix + suffix;
		this.size = holderStr.length();
		this.pair = suffix == null ? false : true;
		
	}
	
	public int size() {
		return this.size;
	}
	
	/**
	 * <p>Discription:[参数完整字符]</p>
	 * @return
	 * @author hypo zhang  2018-11-14
	 */
	public String holderString() {
		return this.holderStr;
	}
	
	/**
	 * <p>Discription:[有参数名称]</p>
	 * @return
	 * @author hypo zhang  2018-11-14
	 */
	public boolean isHaveName() {
		return haveName == HAVENAME;
	}
	
	/**
	 * <p>Discription:[没有参数名称]</p>
	 * @return
	 * @author hypo zhang  2018-11-14
	 */
	public boolean isNoHaveName() {
		return haveName == NO_HAVENAME;
	}
	
	/**
	 * <p>Discription:[参数名，可有可无]</p>
	 * @return
	 * @author hypo zhang  2018-11-14
	 */
	public boolean isNotEssential() {
		return haveName == NOT_ESSENTIAL;
	}

	
	public Pattern pattern() {
		return this.pattern;
	}

	/**
	 * <p>Discription:[获取前缀]</p>
	 * @return
	 * @author 张海波  2017-12-1
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * <p>Discription:[获取后缀]</p>
	 * @return
	 * @author 张海波  2017-12-1
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * <p>Discription:[是否成对的]</p>
	 * @return
	 * @author 张海波  2017-12-1
	 */
	public boolean isPair() {
		return pair;
	}

	public static Placeholder valueOfs(String placeholder) {
		if ("?".equals(placeholder)) {
			return SQL;
		} else if ("${}".equals(placeholder)) {
			return VAR;
		} else if ("{}".equals(placeholder)) {
			return MSG;
		} else if (":".equals(placeholder)) {
			return PARAMNAME;
		}
		throw new BaseRuntimeException("不支持的占位符。 ").addAttribute("placeholder string", placeholder);
	}

}
