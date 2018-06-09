package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class IntegerArrayPrimitiveConverter implements IConvertible<int[]> {

	public int[] convert(Object ovalue) {

		if (ovalue instanceof Integer[]) {
			return UtilArray.toPrimitive((Integer[]) ovalue);
		}

		if (ovalue instanceof Object[]) {
			Object[] array = (Object[]) ovalue;
			int[] values = new int[array.length];
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				if (item != null)
					values[j++] = Converter.convert(item, int.class);
			}
			return j != array.length ? UtilArray.copyOf(j, values) : values;
		}

		if (ovalue instanceof float[]) {
			float[] array = (float[]) ovalue;
			int[] values = new int[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, int.class);
			}
			return values;
		}

		if (ovalue instanceof double[]) {
			double[] array = (double[]) ovalue;
			int[] values = new int[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, int.class);
			}
			return values;
		}

		if (ovalue instanceof long[]) {
			long[] array = (long[]) ovalue;
			int[] values = new int[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, int.class);
			}
			return values;
		}

		if (ovalue instanceof short[]) {
			short[] array = (short[]) ovalue;
			int[] values = new int[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, int.class);
			}
			return values;
		}

		if (ovalue instanceof byte[]) {
			byte[] array = (byte[]) ovalue;
			int[] values = new int[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, int.class);
			}
			return values;
		}

		return null;
	}

}
