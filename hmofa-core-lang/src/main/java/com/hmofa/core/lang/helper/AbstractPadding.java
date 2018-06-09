package com.hmofa.core.lang.helper;

import com.hmofa.core.lang.IPadding;
import com.hmofa.core.lang.utils.UtilString;

public abstract class AbstractPadding implements IPadding {

	public String leftPad(CharSequence str, int totalSize, CharSequence padValue) {
		if (str == null)
			return null;
		String padstr = buildPadStr(str, totalSize, padValue);
		return UtilString.isEmpty(padstr) ? str.toString() : concat(padstr, str);
	}

	public String rightPad(CharSequence str, int totalSize, CharSequence padValue) {
		if (str == null)
			return null;
		String padstr = buildPadStr(str, totalSize, padValue);
		return UtilString.isEmpty(padstr) ? str.toString() : concat(str, padstr);
	}

	/** 计算填充字符数   */
	protected abstract int clacPadCharLen(CharSequence str, int totalSize, char[] padValue);

	private String getPadChar(char[] padCharValue, int padCharCount) {
		if (padCharCount == 0)
			return "";
		StringBuilder spad = new StringBuilder();
		if (padCharValue.length >= padCharCount) {
			spad.append(padCharValue, 0, padCharCount);
			return spad.toString();
		}
		int padcount = padCharCount / padCharValue.length;
		int padmod = padCharCount % padCharValue.length;
		for (int i = 0; i < padcount; i++)
			spad.append(padCharValue, 0, padCharValue.length);
		spad.append(padCharValue, 0, padmod);
		return spad.toString();
	}
	
	private String concat(CharSequence concat, CharSequence beConcat) {
		if (concat instanceof StringBuffer)
			return ((StringBuffer) concat).append(beConcat, 0, beConcat.length()).toString();
		if (concat instanceof StringBuilder)
			return ((StringBuilder) concat).append(beConcat, 0, beConcat.length()).toString();
		return concat.toString().concat(beConcat.toString());
	}

	private String buildPadStr(CharSequence str, int totalSize, CharSequence padValue) {
		if (UtilString.isEmpty(padValue))
			return null;
		char[] padCharValue = padValue.toString().toCharArray();
		int padCharCount = clacPadCharLen(str, totalSize, padCharValue);
		return getPadChar(padCharValue, padCharCount);
	}

	protected int getPadLen(int totalSize, int leng) {
		int padlen = totalSize - leng;	// 填充长度 （字符）
		padlen = padlen > maxlength ? maxlength : padlen;
		padlen = padlen < 0 ? 0 : padlen;
		return padlen;
	}
	
	protected int maxlength = 1000;
}
