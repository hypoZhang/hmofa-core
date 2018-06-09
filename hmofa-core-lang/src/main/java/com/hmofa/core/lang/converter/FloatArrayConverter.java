package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class FloatArrayConverter implements IConvertible<Float[]> {

	public Float[] convert(Object obj) {
		float[] array = Converter.convert(obj, float[].class);
		return UtilArray.toWrapClass(array);
	}

}
