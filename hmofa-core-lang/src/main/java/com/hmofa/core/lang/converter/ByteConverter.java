package com.hmofa.core.lang.converter;

import java.math.BigDecimal;

import com.hmofa.core.exception.PrecisionLossException;
import com.hmofa.core.lang.utils.UtilNumber;

public final class ByteConverter implements IConvertible<Byte> {

	public Byte convert(Object ovalue) {
		if (ovalue instanceof Number || ovalue instanceof String) {
			if (UtilNumber.isPrecisionLoss(ovalue, Byte.class))
				throw new PrecisionLossException(null, ovalue, Byte.MAX_VALUE);
		}
		if (ovalue instanceof Integer)
			return Byte.valueOf(((Integer) ovalue).byteValue());
		if (ovalue instanceof Float)
			return Byte.valueOf(((Float) ovalue).byteValue());
		if (ovalue instanceof Short)
			return Byte.valueOf(((Short) ovalue).byteValue());
		if (ovalue instanceof Long)
			return Byte.valueOf(((Long) ovalue).byteValue());
		if (ovalue instanceof Double)
			return Byte.valueOf(((Double) ovalue).byteValue());
		if (ovalue instanceof BigDecimal)
			return Byte.valueOf(((BigDecimal) ovalue).byteValue());
		if (ovalue instanceof String)
			return Converter.convert(((String) ovalue).trim(), BigDecimal.class).byteValue();
		return null;
	}

	/**
	 * Integer -> byte		Float -> byte
	 * Short -> byte		Long -> byte
	 * Double -> byte		BigDecimal -> byte
	 * String -> byte 
	 */
}
