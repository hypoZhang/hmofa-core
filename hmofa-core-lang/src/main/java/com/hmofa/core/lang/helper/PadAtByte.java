package com.hmofa.core.lang.helper;

import java.util.HashMap;
import java.util.Map;

import com.hmofa.core.lang.coder.StringCoder;


public class PadAtByte extends AbstractPadding {

	private String charsetName = StringCoder.DEFAULT_UNI_CHARSET_NAME;

	public PadAtByte() {
	}

	public PadAtByte(String charsetName) {
		this.charsetName = charsetName;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	protected int clacPadCharLen(CharSequence str, int totalSize, char[] padValue) {
		byte[] strb = StringCoder.encode(str, charsetName);
		int padlen = getPadLen(totalSize, strb.length);

		int padValueCount = 0; // 填充值总长度
		Map<Character, Integer> padlenMap = new HashMap<Character, Integer>();
		for (char ca : padValue) {
			int plen = StringCoder.encode(charsetName, ca).length;
			padlenMap.put(ca, plen);
			padValueCount = padValueCount + plen;
		}

		// 需要填充的 char 数
		int padCharCount = 0;
		if (padValueCount == padlen) {
			padCharCount = padValue.length;
		} else if (padValueCount > padlen) {
			padCharCount = getPadCharCount(padlen, padValue, padlenMap);
		} else {
			int merb = padlen / padValueCount;
			int modb = padlen % padValueCount;
			int modChar = 0;
			if (modb > 0)
				modChar = getPadCharCount(modb, padValue, padlenMap);
			padCharCount = padValue.length * merb + modChar;
		}
		return padCharCount;
	}

	private int getPadCharCount(int padlenB, char[] padChar, Map<Character, Integer> padlenMap) {
		int len = 0;
		int padCharCount = 0;
		for (char ca : padChar) {
			int i = padlenMap.get(ca);
			len = len + i;
			if (len > padlenB)
				break;
			padCharCount++;
		}
		return padCharCount;
	}
}
