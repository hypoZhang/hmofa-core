package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class BooleanArrayPrimitiveConverter implements IConvertible<boolean[]> {

	public boolean[] convert(Object value) {

		if (value instanceof Object[]) {
			Object[] array = (Object[]) value;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BOOLEAN_ARRAY;

			boolean[] bol = new boolean[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, boolean.class);
			}
			return bol;
		}

		if (value instanceof int[]) {
			int[] array = (int[]) value;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BOOLEAN_ARRAY;

			boolean[] bol = new boolean[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, boolean.class);
			}
			return bol;
		}

		if (value instanceof long[]) {
			long[] array = (long[]) value;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BOOLEAN_ARRAY;

			boolean[] bol = new boolean[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, boolean.class);
			}
			return bol;
		}

		if (value instanceof byte[]) {
			byte[] array = (byte[]) value;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BOOLEAN_ARRAY;

			boolean[] bol = new boolean[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, boolean.class);
			}
			return bol;
		}

		if (value instanceof short[]) {
			short[] array = (short[]) value;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BOOLEAN_ARRAY;

			boolean[] bol = new boolean[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, boolean.class);
			}
			return bol;
		}

		if (value instanceof float[]) {
			float[] array = (float[]) value;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BOOLEAN_ARRAY;

			boolean[] bol = new boolean[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, boolean.class);
			}
			return bol;
		}

		if (value instanceof double[]) {
			double[] array = (double[]) value;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BOOLEAN_ARRAY;

			boolean[] bol = new boolean[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, boolean.class);
			}
			return bol;
		}

		return null;
	}

}
