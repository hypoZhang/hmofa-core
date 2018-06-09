package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class LongArrayConverter implements IConvertible<Long[]> {

	public Long[] convert(Object obj) {
		long[] array = Converter.convert(obj, long[].class);
		return UtilArray.toWrapClass(array);
	}

}
