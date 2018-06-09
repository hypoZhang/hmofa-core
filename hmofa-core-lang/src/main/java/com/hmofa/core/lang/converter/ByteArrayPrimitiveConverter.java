package com.hmofa.core.lang.converter;

import java.sql.Blob;
import java.sql.Clob;

import com.hmofa.core.exception.ConversionException;
import com.hmofa.core.lang.coder.StringCoder;
import com.hmofa.core.lang.helper.Hex;
import com.hmofa.core.lang.utils.UtilArray;


public final class ByteArrayPrimitiveConverter implements IConvertible<byte[]> {
	
	public byte[] convert(Object obj) {
		
		if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			byte[] bytes = new byte[array.length];
			int priIndex = 0;
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				if (item == null)
					continue;
				bytes[priIndex++] = Converter.convert(item, byte.class);
			}
			if (priIndex != array.length)
				bytes = UtilArray.copyOf(priIndex, bytes);
			return bytes;
		}
		
		if (obj instanceof String) {
			String value = obj.toString().trim();
			return Hex.decodeBase64(value);
		}
		
		if (obj instanceof Blob) {
			try {
				Blob blob = (Blob) obj;
				long l = blob.length();
				byte[] buffer = new byte[Converter.convert(l, int.class)];
				blob.getBinaryStream().read(buffer);
				return buffer;
			} catch (Exception ex) {
				throw new ConversionException(ex, obj.getClass(), byte[].class);
			}
		}
		
		if (obj instanceof Clob) {
			try {
				Clob clob = (Clob) obj;
				long l = clob.length();
				char[] buffer = new char[Converter.convert(l, int.class)];
				clob.getCharacterStream().read(buffer);
				return StringCoder.encode(buffer);
			} catch (Exception ex) {
				throw new ConversionException(ex, obj.getClass(), byte[].class);
			}
		}
		
		if (obj instanceof int[]) {
			int[] array = (int[]) obj;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BYTE_ARRAY;

			byte[] bol = new byte[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, byte.class);
			}
			return bol;
		}
		
		if (obj instanceof long[]) {
			long[] array = (long[]) obj;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BYTE_ARRAY;

			byte[] bol = new byte[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, byte.class);
			}
			return bol;
		}
		
		if (obj instanceof short[]) {
			short[] array = (short[]) obj;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BYTE_ARRAY;

			byte[] bol = new byte[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, byte.class);
			}
			return bol;
		}
		
		if (obj instanceof float[]) {
			float[] array = (float[]) obj;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BYTE_ARRAY;

			byte[] bol = new byte[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, byte.class);
			}
			return bol;
		}
		
		if (obj instanceof double[]) {
			double[] array = (double[]) obj;
			if (UtilArray.isEmpty(array))
				return UtilArray.EMPTY_BYTE_ARRAY;

			byte[] bol = new byte[array.length];
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				bol[i] = Converter.convert(item, byte.class);
			}
			return bol;
		}
		
		return null;
	}

	/**
	 * Object[] -> byte[]   int[] -> byte[]  
	 * long[] -> byte[]    short[] -> byte[]
	 * float[] -> byte[]   double[] -> byte[] 
	 * Blob -> byte[]     Clob -> byte[]    [默认平台字符集编码]
	 * base64String -> byte[]
	 */
}
