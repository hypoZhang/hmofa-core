package com.hmofa.core.lang.converter;

import java.math.BigDecimal;

import com.hmofa.core.exception.PrecisionLossException;
import com.hmofa.core.lang.utils.UtilArray;
import com.hmofa.core.lang.utils.UtilNumber;


public final class IntegerConverter implements IConvertible<Integer> {

	public Integer convert(Object ovalue) {
		if (UtilNumber.isPrecisionLoss(ovalue, Integer.class))
			throw new PrecisionLossException(null, ovalue, Integer.MAX_VALUE); // 精度损失， 抛异常

		if (ovalue instanceof String)
			return Integer.valueOf(ovalue.toString());
		if (ovalue instanceof BigDecimal)
			return ((BigDecimal) ovalue).intValue();
		if (ovalue instanceof Float)
			return Integer.valueOf(((Float) ovalue).intValue());
		if (ovalue instanceof Long)
			return Integer.valueOf(((Long) ovalue).intValue());
		if (ovalue instanceof Double)
			return Integer.valueOf(((Double) ovalue).intValue());
		if (ovalue instanceof Short)
			return Integer.valueOf(((Short) ovalue).intValue());
		if (ovalue instanceof Byte)
			return Integer.valueOf(((Byte) ovalue).intValue());
		if (ovalue instanceof Byte[])
			ovalue = UtilArray.toPrimitive((Byte[]) ovalue);
		if (ovalue instanceof byte[])
			return UtilNumber.toBigEndianInteger((byte[]) ovalue);
		if (ovalue instanceof Character)
			return Character.getNumericValue((Character) ovalue);

		return null;
	}

}
