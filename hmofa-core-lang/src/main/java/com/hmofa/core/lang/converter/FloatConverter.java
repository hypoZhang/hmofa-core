package com.hmofa.core.lang.converter;

import java.math.BigDecimal;

import com.hmofa.core.exception.PrecisionLossException;
import com.hmofa.core.lang.utils.UtilArray;
import com.hmofa.core.lang.utils.UtilNumber;



public final class FloatConverter implements IConvertible<Float> {

	public Float convert(Object ovalue) {

		if (UtilNumber.isPrecisionLoss(ovalue, Float.class))
			throw new PrecisionLossException(null, ovalue, Float.MAX_VALUE); // 精度损失， 抛异常

		if (ovalue instanceof String)
			return Float.valueOf(ovalue.toString());
		if (ovalue instanceof BigDecimal)
			return ((BigDecimal) ovalue).floatValue();
		if (ovalue instanceof Integer)
			return Float.valueOf(((Integer) ovalue).floatValue());
		if (ovalue instanceof Long)
			return Float.valueOf(((Long) ovalue).floatValue());
		if (ovalue instanceof Double)
			return Float.valueOf(((Double) ovalue).floatValue());
		if (ovalue instanceof Short)
			return Float.valueOf(((Short) ovalue).floatValue());
		if (ovalue instanceof Byte)
			return Float.valueOf(((Byte) ovalue).floatValue());
		if (ovalue instanceof Byte[])
			ovalue = UtilArray.toPrimitive((Byte[]) ovalue);
		if (ovalue instanceof byte[])
			return UtilNumber.toBigEndianFloat((byte[]) ovalue);
		return null;
	}

}
