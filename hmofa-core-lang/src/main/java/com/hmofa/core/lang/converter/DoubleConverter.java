package com.hmofa.core.lang.converter;

import java.math.BigDecimal;

import com.hmofa.core.exception.PrecisionLossException;
import com.hmofa.core.lang.utils.UtilArray;
import com.hmofa.core.lang.utils.UtilNumber;



public final class DoubleConverter implements IConvertible<Double> {

	public Double convert(Object ovalue) {
		if (UtilNumber.isPrecisionLoss(ovalue, Double.class))
			throw new PrecisionLossException(null, ovalue, Double.MAX_VALUE);

		if (ovalue instanceof String)
			return Double.valueOf(ovalue.toString());
		if (ovalue instanceof BigDecimal)
			return ((BigDecimal) ovalue).doubleValue();
		if (ovalue instanceof Integer)
			return Double.valueOf(((Integer) ovalue).doubleValue());
		if (ovalue instanceof Long)
			return Double.valueOf(((Long) ovalue).doubleValue());
		if (ovalue instanceof Float)
			return Double.valueOf(((Float) ovalue).doubleValue());
		if (ovalue instanceof Short)
			return Double.valueOf(((Short) ovalue).doubleValue());
		if (ovalue instanceof Byte)
			return Double.valueOf(((Byte) ovalue).doubleValue());
		if (ovalue instanceof Byte[])
			ovalue = UtilArray.toPrimitive((Byte[]) ovalue);
		if (ovalue instanceof byte[])
			return UtilNumber.toBigEndianDouble((byte[]) ovalue);
		return null;
	}

}
