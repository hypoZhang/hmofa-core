package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class IntegerArrayConverter implements IConvertible<Integer[]> {
	
	public Integer[] convert(Object obj) {
		int[] array = Converter.convert(obj, int[].class);
		return UtilArray.toWrapClass(array);
	}

}
