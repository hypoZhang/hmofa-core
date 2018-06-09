package com.hmofa.core.exception;

/**
 * <dd>Description:[非法参数异常]</dd>
 * <dt>IllegalArgumentException</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2015-11-2</dd>
 * @version 1.0
 * @author 张海波
 */
public class IllegalArgumentException extends BaseRuntimeException {

	/***/
	private static final long serialVersionUID = 3487267720226683326L;

	public IllegalArgumentException(String message) {
		super(message);
	}
}

// java.lang.IllegalArgumentException