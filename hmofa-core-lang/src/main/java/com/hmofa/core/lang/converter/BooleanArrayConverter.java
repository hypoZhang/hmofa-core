package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class BooleanArrayConverter implements IConvertible<Boolean[]> {

	public Boolean[] convert(Object value) {

		if (value instanceof Object[]) {
			Object[] array = (Object[]) value;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BOOLEAN_OBJECT_ARRAY;

			Boolean[] bol = new Boolean[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, boolean.class);
			}
			return bol;
		}
		boolean[] array = Converter.convert(value, boolean[].class);
		return UtilArray.toWrapClass(array);
	}

}
