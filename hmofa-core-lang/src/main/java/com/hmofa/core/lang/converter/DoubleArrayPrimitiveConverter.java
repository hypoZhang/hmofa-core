package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class DoubleArrayPrimitiveConverter implements IConvertible<double[]> {

	public double[] convert(Object ovalue) {

		if (ovalue instanceof Double[]) {
			return UtilArray.toPrimitive((Double[]) ovalue);
		}

		if (ovalue instanceof Object[]) {
			Object[] array = (Object[]) ovalue;
			double[] values = new double[array.length];
			int j = 0;
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				if (item != null)
					values[j++] = Converter.convert(item, double.class);
			}
			return j != array.length ? UtilArray.copyOf(j, values) : values;
		}

		if (ovalue instanceof int[]) {
			int[] array = (int[]) ovalue;
			double[] values = new double[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, double.class);
			}
			return values;
		}

		if (ovalue instanceof float[]) {
			float[] array = (float[]) ovalue;
			double[] values = new double[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, double.class);
			}
			return values;
		}

		if (ovalue instanceof long[]) {
			long[] array = (long[]) ovalue;
			double[] values = new double[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, double.class);
			}
			return values;
		}

		if (ovalue instanceof short[]) {
			short[] array = (short[]) ovalue;
			double[] values = new double[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, double.class);
			}
			return values;
		}

		if (ovalue instanceof byte[]) {
			byte[] array = (byte[]) ovalue;
			double[] values = new double[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, double.class);
			}
			return values;
		}

		return null;
	}

}
