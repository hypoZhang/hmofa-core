package com.hmofa.core.lang.bundle;

import java.util.regex.Pattern;

import com.hmofa.core.exception.BaseRuntimeException;

public enum Placeholder {

	/** 通常 sql 表达式用到的占位符 */
	SQL("?", null, 1, Pattern.compile("\\?")),

	/** 通常用于表示变量     (只允许英文字母 和 点 及下划线)*/
	VAR("${", "}", 3, Pattern.compile("\\$\\{[a-z|A-Z|.|_]*\\}")),

	/** 字符串替换符     (允许编号，编号应该为正整数) */
	REP("{", "}", 2, Pattern.compile("\\{[0-9]*[1-9][0-9]*\\}|\\{\\}")),

	/** 字符串替换符     按名称替换 (参数名)*/
	REPNAME(":", null, 1, Pattern.compile(":[a-z|A-Z|.|_]*"))
	;

	private final String holderStr;
	private final int size;
	private final Pattern pattern;
	private final String prefix;
	private final String suffix;
	private final boolean pair;

	private Placeholder(String prefix, String suffix, int size, Pattern pattern) {
		this.holderStr = suffix == null || suffix.length() == 0 ? prefix : prefix + suffix;
		this.size = size;
		this.pattern = pattern;
		this.pair = suffix == null || suffix.length() == 0 ? false : true;
		this.prefix = prefix;
		this.suffix = suffix == null || suffix.length() == 0 ? null : suffix;
	}

	public String holderString() {
		return this.holderStr;
	}

	public int size() {
		return this.size;
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
			return REP;
		} else if (":".equals(placeholder)) {
			return REPNAME;
		}
		throw new BaseRuntimeException("不支持的占位符。 ").addAttribute("placeholder string", placeholder);
	}

}
