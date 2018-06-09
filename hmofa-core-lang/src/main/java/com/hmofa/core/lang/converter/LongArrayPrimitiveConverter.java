package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class LongArrayPrimitiveConverter implements IConvertible<long[]> {

	public long[] convert(Object ovalue) {

		if (ovalue instanceof Long[]) {
			return UtilArray.toPrimitive((Long[]) ovalue);
		}
		
		if (ovalue instanceof Object[]) {
			Object[] array = (Object[]) ovalue;
			long[] values = new long[array.length];
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				if (item != null)
					values[j++] = Converter.convert(item, long.class);
			}
			return j != array.length ? UtilArray.copyOf(j, values) : values;
		}

		if (ovalue instanceof float[]) {
			float[] array = (float[]) ovalue;
			long[] values = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, long.class);
			}
			return values;
		}

		if (ovalue instanceof double[]) {
			double[] array = (double[]) ovalue;
			long[] values = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, long.class);
			}
			return values;
		}

		if (ovalue instanceof int[]) {
			int[] array = (int[]) ovalue;
			long[] values = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, long.class);
			}
			return values;
		}

		if (ovalue instanceof short[]) {
			short[] array = (short[]) ovalue;
			long[] values = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, long.class);
			}
			return values;
		}

		if (ovalue instanceof byte[]) {
			byte[] array = (byte[]) ovalue;
			long[] values = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, long.class);
			}
			return values;
		}

		return null;
	}

}
