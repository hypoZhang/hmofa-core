package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class ShortArrayConverter implements IConvertible<Short[]> {

	public Short[] convert(Object obj) {
		short[] array = Converter.convert(obj, short[].class);
		return UtilArray.toWrapClass(array);
	}

}
