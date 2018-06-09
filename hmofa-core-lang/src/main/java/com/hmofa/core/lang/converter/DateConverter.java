package com.hmofa.core.lang.converter;

import java.sql.Timestamp;
import java.util.Date;


public final class DateConverter implements IConvertible<Date> {

	public Date convert(Object ovalue) {
		return Converter.convert(ovalue, Timestamp.class);
	}
	
}
