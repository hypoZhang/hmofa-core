package com.hmofa.core.exception;

/**
 * <dd>Description:[异常信息，已编码]</dd>
 * <dt>BasicCodingException</dt>
 * <dd>Copyright: © 2018 zhang haibo. All Rights Reserved.</dd>
 * <dd>CreateDate: 2018-08-15</dd>
 * @version 1.0
 * @author hypo zhang
 */
public class BasicCodingException extends BaseRuntimeException {
	
	private static final long serialVersionUID = 5356461606332988L;

	public BasicCodingException() {
		super();
	}
	
	public BasicCodingException(int coding) {
		setCoding(coding);
	}
	
	public BasicCodingException(int coding, Throwable cause) {
		this(cause);
		setCoding(coding);
	}
	
	public BasicCodingException(Throwable cause) {
		super(cause);
	}
	
	public BasicCodingException setCoding(int coding) {
		return this;
	}
}
