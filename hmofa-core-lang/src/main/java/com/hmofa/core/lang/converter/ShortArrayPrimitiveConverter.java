package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class ShortArrayPrimitiveConverter implements IConvertible<short[]> {

	public short[] convert(Object ovalue) {
		if (ovalue instanceof Short[]) {
			return UtilArray.toPrimitive((Short[]) ovalue);
		}

		if (ovalue instanceof Object[]) {
			Object[] array = (Object[]) ovalue;
			short[] values = new short[array.length];
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				if (item != null)
					values[j++] = Converter.convert(item, short.class);
			}
			return j != array.length ? UtilArray.copyOf(j, values) : values;
		}

		if (ovalue instanceof float[]) {
			float[] array = (float[]) ovalue;
			short[] values = new short[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, short.class);
			}
			return values;
		}

		if (ovalue instanceof double[]) {
			double[] array = (double[]) ovalue;
			short[] values = new short[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, short.class);
			}
			return values;
		}

		if (ovalue instanceof long[]) {
			long[] array = (long[]) ovalue;
			short[] values = new short[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, short.class);
			}
			return values;
		}

		if (ovalue instanceof int[]) {
			int[] array = (int[]) ovalue;
			short[] values = new short[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, short.class);
			}
			return values;
		}

		if (ovalue instanceof byte[]) {
			byte[] array = (byte[]) ovalue;
			short[] values = new short[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, short.class);
			}
			return values;
		}

		return null;
	}

}
