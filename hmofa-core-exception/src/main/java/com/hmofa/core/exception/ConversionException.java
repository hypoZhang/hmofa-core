package com.hmofa.core.exception;

public class ConversionException extends BaseRuntimeException {

	/***/
	private static final long serialVersionUID = 503264914095663128L;

	public ConversionException() {
	}
	
	public ConversionException(Throwable cause) {
		super(cause);
	}
	
	public ConversionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ConversionException(Throwable ex, Object srcObj, Class<?> dstClass) {
		super("转换数据类型错误。类型：{} 至  {}", ex);
		addAttribute("sourceClass", srcObj.getClass());
		addAttribute("targetClass", dstClass);
		addAttribute("sourceValue", srcObj);
	}
	
}
