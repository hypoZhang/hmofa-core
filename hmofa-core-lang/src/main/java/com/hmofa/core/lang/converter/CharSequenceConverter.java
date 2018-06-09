package com.hmofa.core.lang.converter;

import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

import com.hmofa.core.exception.ConversionException;
import com.hmofa.core.lang.coder.StringCoder;
import com.hmofa.core.lang.helper.Hex;
import com.hmofa.core.lang.utils.UtilArray;
import com.hmofa.core.lang.utils.UtilDate;
import com.hmofa.core.lang.utils.UtilNumber;



public final class CharSequenceConverter implements IConvertible<CharSequence> {

	public CharSequence convert(Object value) {

		if (value instanceof StringBuilder || value instanceof StringBuffer)
			return value.toString();

		if (value instanceof Number && !(value instanceof Byte)) {
			return UtilNumber.formatNumeric(value);
		}
		if (value instanceof Byte)
			return Hex.encodeBase64(new Byte[] { (Byte) value });

		if (value instanceof Boolean)
			return ((Boolean) value).equals(true) ? "true" : "false";

		if (value instanceof Character)
			return String.valueOf(value);

		if (value instanceof Time)
			return UtilDate.formatDate(((Time) value).getTime());

		if (value instanceof Date)
			return UtilDate.formatDate(((Date) value).getTime());

		if (value instanceof java.sql.Date)
			return UtilDate.formatDate(((java.sql.Date) value).getTime());

		if (value instanceof Timestamp)
			return UtilDate.formatDate(((Timestamp) value).getTime());

		if (value instanceof byte[])
			return Hex.encodeBase64((byte[]) value);

		if (value instanceof Byte[])
			return Hex.encodeBase64((Byte[]) value);

		if (value instanceof Character[]) {
			value = UtilArray.toPrimitive((Character[]) value);
		}

		if (value instanceof char[])
			return new String((char[]) value);

		if (value instanceof Object[]) {
			Object[] oo = (Object[]) value;
			StringBuilder buf = new StringBuilder(oo.length * 4);
			int index = 0;
			for (Object o : oo) {
				if (o == null)
					o = "";
				buf.append(Converter.convert(o, String.class));
				if (++index != oo.length)
					buf.append(",");
			}
			return buf.toString();
		}

		if (value instanceof Clob) {
			try {
				Clob clob = (Clob) value;
				long l = clob.length();
				int length = Converter.convert(l, int.class);
				char[] buffer = new char[length];
				clob.getCharacterStream().read(buffer);
				return new String(buffer);
			} catch (Exception ex) {
				throw new ConversionException(ex, value.getClass(), String.class);
			}
		}

		if (value instanceof Blob) {
			try {
				Blob blob = (Blob) value;
				long l = blob.length();
				int length = Converter.convert(l, int.class);
				byte[] buffer = new byte[length];
				blob.getBinaryStream().read();
				return Hex.encodeBase64(buffer);
			} catch (Exception ex) {
				throw new ConversionException(ex, value.getClass(), String.class);
			}
		}

		if (value instanceof boolean[]) {
			boolean[] bool = (boolean[]) value;
			StringBuilder buf = new StringBuilder(bool.length * 5);
			int index = 0;
			for (boolean b : bool) {
				buf.append(b == true ? "true" : "false");
				if (++index != bool.length)
					buf.append(",");
			}
			return buf.toString();
		}

		if (value instanceof int[]) {
			int[] bool = (int[]) value;
			StringBuilder buf = new StringBuilder(bool.length * 5);
			int index = 0;
			for (int b : bool) {
				buf.append(b);
				if (++index != bool.length)
					buf.append(",");
			}
			return buf.toString();
		}

		if (value instanceof long[]) {
			long[] bool = (long[]) value;
			StringBuilder buf = new StringBuilder(bool.length * 5);
			int index = 0;
			for (long b : bool) {
				buf.append(UtilNumber.formatNumeric(b));
				if (++index != bool.length)
					buf.append(",");
			}
			return buf.toString();
		}

		if (value instanceof double[]) {
			double[] bool = (double[]) value;
			StringBuilder buf = new StringBuilder(bool.length * 5);
			int index = 0;
			for (double b : bool) {
				buf.append(UtilNumber.formatNumeric(b));
				if (++index != bool.length)
					buf.append(",");
			}
			return buf.toString();
		}

		if (value instanceof float[]) {
			float[] bool = (float[]) value;
			StringBuilder buf = new StringBuilder(bool.length * 5);
			int index = 0;
			for (float b : bool) {
				buf.append(UtilNumber.formatNumeric(b));
				if (++index != bool.length)
					buf.append(",");
			}
			return buf.toString();
		}

		if (value instanceof short[]) {
			short[] bool = (short[]) value;
			StringBuilder buf = new StringBuilder(bool.length * 5);
			int index = 0;
			for (short b : bool) {
				buf.append(b);
				if (++index != bool.length)
					buf.append(",");
			}
			return buf.toString();
		}

		if (value instanceof Charset)
			return StringCoder.charsetName((Charset) value);

		if (value instanceof TimeZone)
			return ((TimeZone) value).getID();
		
		return value.toString();
	}

	/**
	 * StringBuilder StringBuffer -> String
	 * Number(Integer,Long,Short,Float,Double)  -> String [非科学计数法]
	 * Byte Byte[] byte[] -> String [base64 String]
	 * Boolean Boolean[] boolean[] -> String ["true" "false"]
	 * Character Character[] char[] -> String
	 * Clob -> String    Blob -> String [base64 String]
	 * Time Timestamp Date sql.Date  -> String  [yyyy-MM-dd HH:mm:ss.SSS] 
	 * Charset -> String [Charset.name]    Resource  -> String [url]
	 * Object[] -> String [以上所支持类型]
	 * int[] short[] long[] double[] float[]  -> String [同number 以逗号分隔]
	 */
}
