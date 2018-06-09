package com.hmofa.core.lang.converter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public final class TimeConverter implements IConvertible<Time>{

	public Time convert(Object obj) {
		Date d = Converter.convert(obj, Timestamp.class);
		return d == null ? null : new Time(d.getTime());
	}

}
