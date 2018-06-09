package com.hmofa.core.lang.converter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.hmofa.core.lang.utils.UtilDate;
import com.hmofa.core.lang.utils.UtilString;


public final class TimestampConverter implements IConvertible<Timestamp> {

	public Timestamp convert(Object ovalue) {
		
		if (ovalue instanceof java.sql.Date)
			return new Timestamp(((java.sql.Date) ovalue).getTime());
		if (ovalue instanceof Time)
			return new Timestamp(((Time) ovalue).getTime());
		if (ovalue instanceof Date)
			return new Timestamp(((Date) ovalue).getTime());
		
		if (ovalue instanceof Integer)
			ovalue = Converter.convert(ovalue, String.class);
		
		if (ovalue instanceof Long) {
			String str = Converter.convert(ovalue, String.class);
			if (!UtilDate.isDateFormat(str))
				return new Timestamp(((Long) ovalue).longValue());
			ovalue = str;
		}
		
		if (ovalue instanceof CharSequence) {
			String dates = ovalue.toString();
			dates = dates.trim().replace("  ", " ").replace("  ", " ").replace("  ", " ");
			String datepattern = UtilDate.getDateFormatPattern(dates);
			return UtilString.isEmpty(datepattern) ? null : UtilDate.parseTimestamp(dates, datepattern);
		}
		
		return null;
	}

	/**
	 * Date sql.Date time  ->  Timestamp [不同时间类型转换]
	 * Integer Long String ->  Timestamp [匹配支持的日期 表达式，然后格式化为正确的日期]
	 * 
	 */
}
