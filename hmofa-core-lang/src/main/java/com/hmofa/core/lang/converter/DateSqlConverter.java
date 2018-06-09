package com.hmofa.core.lang.converter;

import java.sql.Date;
import java.sql.Timestamp;

public final class DateSqlConverter implements IConvertible<Date> {

	public Date convert(Object obj) {
		java.util.Date d = Converter.convert(obj, Timestamp.class);
		return d == null ? null : new Date(d.getTime());
	}

}
