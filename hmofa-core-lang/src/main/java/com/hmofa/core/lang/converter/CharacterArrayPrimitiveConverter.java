package com.hmofa.core.lang.converter;

import java.sql.Blob;
import java.sql.Clob;

import com.hmofa.core.exception.ConversionException;
import com.hmofa.core.lang.coder.StringCoder;
import com.hmofa.core.lang.utils.UtilArray;


public final class CharacterArrayPrimitiveConverter implements IConvertible<char[]> {

	public char[] convert(Object ovalue) {
		
		if (ovalue instanceof Byte[]) {
			ovalue = UtilArray.toPrimitive((Byte[]) ovalue);
		}
		
		if (ovalue instanceof byte[])
			return StringCoder.decodeChar((byte[]) ovalue);
		
		if (ovalue instanceof Object[]) {
			Object[] array = (Object[]) ovalue;
			char[] values = new char[array.length];
			int priIndex = 0;
			for (int i = 0; i < array.length; i++) {
				Object item = array[i];
				if (item == null)
					continue;
				Character ch = Converter.convert(item, char.class);
				if (ch == null)
					continue;
				values[priIndex++] = ch;
			}
			if (priIndex != array.length)
				values = UtilArray.copyOf(priIndex, values);
			return values;
		}
		
		if (ovalue instanceof String)
			return ((String) ovalue).toCharArray();
		
		if (ovalue instanceof Blob) {
			try {
				Blob blob = (Blob) ovalue;
				long l = blob.length();
				byte[] buffer = new byte[Converter.convert(l, int.class)];
				blob.getBinaryStream().read(buffer);
				return StringCoder.decodeChar(buffer);
			} catch (Exception ex) {
				throw new ConversionException(ex, ovalue.getClass(), char[].class);
			}
		}
		
		if (ovalue instanceof Clob) {
			try {
				Clob clob = (Clob) ovalue;
				long l = clob.length();
				char[] buffer = new char[Converter.convert(l, int.class)];
				clob.getCharacterStream().read(buffer);
				return buffer;
			} catch (Exception ex) {
				throw new ConversionException(ex, ovalue.getClass(), char[].class);
			}
		}
		
		return null;
	}

	/**
	 * Byte[] -> char[]   byte[] -> char[]  
	 * Object[] -> char[]    String -> char[]
	 * Blob -> char[]     Clob -> char[]
	 */
}
