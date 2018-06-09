package com.hmofa.core.lang.converter;

import java.math.BigDecimal;

import com.hmofa.core.exception.PrecisionLossException;
import com.hmofa.core.lang.utils.UtilArray;
import com.hmofa.core.lang.utils.UtilNumber;


public final class ShortConverter implements IConvertible<Short> {

	public Short convert(Object ovalue) {
		if (UtilNumber.isPrecisionLoss(ovalue, Short.class))
			throw new PrecisionLossException(null, ovalue, Short.MAX_VALUE);

		if (ovalue instanceof String)
			return Short.valueOf(ovalue.toString());
		if (ovalue instanceof BigDecimal)
			return ((BigDecimal) ovalue).shortValue();
		if (ovalue instanceof Float)
			return Short.valueOf(((Float) ovalue).shortValue());
		if (ovalue instanceof Integer)
			return Short.valueOf(((Integer) ovalue).shortValue());
		if (ovalue instanceof Double)
			return Short.valueOf(((Double) ovalue).shortValue());
		if (ovalue instanceof Byte)
			return Short.valueOf(((Byte) ovalue).shortValue());
		if (ovalue instanceof Long)
			return Short.valueOf(((Long) ovalue).shortValue());
		if (ovalue instanceof Byte[])
			ovalue = UtilArray.toPrimitive((Byte[]) ovalue);
		if (ovalue instanceof byte[])
			return UtilNumber.toBigEndianShort((byte[]) ovalue);
		
		return null;
	}

}
