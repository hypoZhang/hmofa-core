package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class ByteArrayConverter implements IConvertible<Byte[]> {

	
	public Byte[] convert(Object obj) {
		
		if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			Byte[] values = new Byte[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				values[i] = Converter.convert(item, byte.class);
			}
			return values;
		}
		byte[] array = Converter.convert(obj, byte[].class);
		return UtilArray.toWrapClass(array);
	}

	/**
	 * Object[] -> Byte[]   int[] -> Byte[]  
	 * long[] -> Byte[]    short[] -> Byte[]
	 * float[] -> Byte[]   double[] -> Byte[] 
	 * Blob -> Byte[]     Clob -> Byte[]
	 */
}
