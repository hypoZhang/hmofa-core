package com.hmofa.core.exception;

public class PrecisionLossException extends ConversionException {

	public PrecisionLossException(Throwable ex, Object srcValue, Object dstValue) {
		super("转换过程丢失数据精度。类型：{} 至  {} | 原值：{} 至目标最大值 {}", ex);
		Class<?> srcClass = srcValue == null ? null : srcValue.getClass();
		Class<?> dstClass = dstValue == null ? null : dstValue.getClass();
		addAttribute("sourceClass", srcClass);
		addAttribute("targetClass", dstClass);
		addAttribute("sourceValue", srcValue);
		addAttribute("targetValue", dstValue);
	}

	private static final long serialVersionUID = 7817702920095829020L;

}
