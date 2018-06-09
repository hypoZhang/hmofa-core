package com.hmofa.core.lang.converter;

/**
 * <dd>Description:[转换器接口 (类型转换)]</dd>
 * <dt>IConvertible</dt>
 * <dd>Copyright: Copyright (C) 2015  .All Rights Reserved by zhanghaibo</dd>
 * <dd>CreateDate: 2015-11-2</dd>
 * @version 1.0
 * @author 张海波
 */
public interface IConvertible<E> {

	/**
	 * <p>Discription:[将对象转换成目标类型]</p>
	 * @param value
	 * @return
	 * @author zhanghaibo3  2015-11-3
	 * @throws ConversionException 不支持或未成功转换
	 */
	E convert(Object value);
}
