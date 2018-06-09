package com.hmofa.core.lang.helper;


/**
 * <dd>Description:[按字符进行填充]</dd>
 * <dt>PadAtChar</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2016-9-9</dd>
 * @version 1.0
 * @author 张海波
 */
public class PadAtChar extends AbstractPadding {
	
	protected int clacPadCharLen(CharSequence str, int totalSize, char[] padValue) {
		int leng = str.length();
		return getPadLen(totalSize, leng);
	}
	
}
