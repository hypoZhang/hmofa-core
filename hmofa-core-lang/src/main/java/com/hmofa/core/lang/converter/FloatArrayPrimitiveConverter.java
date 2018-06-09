package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class FloatArrayPrimitiveConverter implements IConvertible<float[]> {

	public float[] convert(Object ovalue) {

		if (ovalue instanceof Float[]) {
			return UtilArray.toPrimitive((Float[]) ovalue);
		}

		if (ovalue instanceof Object[]) {
			Object[] array = (Object[]) ovalue;
			float[] values = new float[array.length];
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				if (item != null)
					values[j++] = Converter.convert(item, float.class);
			}
			return j != array.length ? UtilArray.copyOf(j, values) : values;
		}

		if (ovalue instanceof int[]) {
			int[] array = (int[]) ovalue;
			float[] values = new float[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, float.class);
			}
			return values;
		}

		if (ovalue instanceof double[]) {
			double[] array = (double[]) ovalue;
			float[] values = new float[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, float.class);
			}
			return values;
		}

		if (ovalue instanceof long[]) {
			long[] array = (long[]) ovalue;
			float[] values = new float[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, float.class);
			}
			return values;
		}

		if (ovalue instanceof short[]) {
			short[] array = (short[]) ovalue;
			float[] values = new float[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, float.class);
			}
			return values;
		}

		if (ovalue instanceof byte[]) {
			byte[] array = (byte[]) ovalue;
			float[] values = new float[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, float.class);
			}
			return values;
		}

		return null;
	}

}
