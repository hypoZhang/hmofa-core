package com.hmofa.core.lang.converter;

import java.math.BigDecimal;

import com.hmofa.core.exception.PrecisionLossException;
import com.hmofa.core.lang.utils.UtilArray;
import com.hmofa.core.lang.utils.UtilNumber;


public final class LongConverter implements IConvertible<Long> {

	public Long convert(Object ovalue) {
		if (UtilNumber.isPrecisionLoss(ovalue, Long.class))
			throw new PrecisionLossException(null, ovalue, Long.MAX_VALUE); // 精度损失， 抛异常

		if (ovalue instanceof String)
			return Long.valueOf(ovalue.toString());
		if (ovalue instanceof BigDecimal)
			return ((BigDecimal) ovalue).longValue();
		if (ovalue instanceof Float)
			return Long.valueOf(((Float) ovalue).longValue());
		if (ovalue instanceof Integer)
			return Long.valueOf(((Integer) ovalue).longValue());
		if (ovalue instanceof Double)
			return Long.valueOf(((Double) ovalue).longValue());
		if (ovalue instanceof Short)
			return Long.valueOf(((Short) ovalue).longValue());
		if (ovalue instanceof Byte)
			return Long.valueOf(((Byte) ovalue).longValue());
		if (ovalue instanceof Byte[])
			ovalue = UtilArray.toPrimitive((Byte[]) ovalue);
		if (ovalue instanceof byte[])
			return UtilNumber.toBigEndianLong((byte[]) ovalue);
		return null;
	}

}
