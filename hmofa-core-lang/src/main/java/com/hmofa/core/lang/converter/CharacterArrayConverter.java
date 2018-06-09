package com.hmofa.core.lang.converter;

import com.hmofa.core.lang.utils.UtilArray;

public final class CharacterArrayConverter implements IConvertible<Character[]> {

	
	public Character[] convert(Object ovalue) {
				
		if (ovalue instanceof Object[] && !(ovalue instanceof Byte[])) {
			Object[] array = (Object[]) ovalue;
			if (UtilArray.isNotEmpty(array)) {
				Character[] values = new Character[array.length];
				for (int i = 0; i < array.length; i++) {
					Object item = array[i];
					values[i] = Converter.convert(item, char.class);
				}
				return values;
			}
		}
		char[] array = Converter.convert(ovalue, char[].class);
		return UtilArray.toWrapClass(array);
	}

}
