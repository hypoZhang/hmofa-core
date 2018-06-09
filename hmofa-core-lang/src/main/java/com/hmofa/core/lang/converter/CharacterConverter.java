package com.hmofa.core.lang.converter;

import com.hmofa.core.exception.PrecisionLossException;
import com.hmofa.core.lang.coder.StringCoder;
import com.hmofa.core.lang.utils.UtilArray;
import com.hmofa.core.lang.utils.UtilNumber;

public final class CharacterConverter implements IConvertible<Character> {

	public Character convert(Object ovalue) {
		
		if (ovalue instanceof Number) {
			if (UtilNumber.isPrecisionLoss(ovalue, int.class))
				throw new PrecisionLossException(null, ovalue, Integer.MAX_VALUE);
			char[] ch = Character.toChars(((Number) ovalue).intValue());
			return ch == null ? null : ch.length > 1 ? null : ch[0];
		}
		
		if (ovalue instanceof Byte[]) {
			ovalue = UtilArray.toPrimitive((Byte[]) ovalue);
		}
		
		if (ovalue instanceof byte[]) {
			byte[] bytes = (byte[]) ovalue;
			if (UtilArray.isNotEmpty(bytes)) {
				char[] ch = StringCoder.decodeChar(bytes);
				return ch.length > 1 ? null : ch[0];
			}
		}
		
		if (ovalue instanceof String)
			return Character.valueOf((ovalue.toString().charAt(0)));
		
		return null;
	}

	
}
