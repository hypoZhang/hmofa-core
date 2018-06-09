package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class DoubleArrayConverter implements IConvertible<Double[]> {

	public Double[] convert(Object obj) {
		double[] array = Converter.convert(obj, double[].class);
		return UtilArray.toWrapClass(array);
	}

}
