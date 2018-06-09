package com.hmofa.core.exception;

/**
 * <dd>Description:[空参数异常]</dd>
 * <dt>NullArgumentException</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2015-11-2</dd>
 * @version 1.0
 * @author 张海波
 */
public class NullArgumentException extends BaseRuntimeException {

	private static final long serialVersionUID = 8675542554353799722L;

	public NullArgumentException() {
		super("");
	}

	public NullArgumentException(String argName) {
		super(argName);
	}

}
