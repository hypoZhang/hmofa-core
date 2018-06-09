package com.hmofa.core.lang;

public interface IPadding {

	/**
	 * <p>Discription:[左填充]</p>
	 * @param str		字符串
	 * @param totalSize 总长度
	 * @param padValue  填充字符串
	 * @return
	 * @author hypo zhang  2018-05-30
	 */
	String leftPad(CharSequence str, int totalSize, CharSequence padValue);

	/**
	 * <p>Discription:[右填充]</p>
	 * @param str		字符串
	 * @param totalSize 总长度
	 * @param padValue  填充字符串
	 * @return
	 * @author hypo zhang  2018-05-30
	 */
	String rightPad(CharSequence str, int totalSize, CharSequence padValue);
}
