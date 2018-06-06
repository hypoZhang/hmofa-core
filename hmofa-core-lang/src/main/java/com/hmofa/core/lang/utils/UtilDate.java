package com.hmofa.core.lang.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hmofa.core.exception.ConversionException;



/**
 * <dd>Description:[时间工具类]</dd> 
 * <dt>UtilString</dt> 
 * <dd>Copyright: Copyright (C) 2015 .All Rights Reserved by zhanghaibo</dd> 
 * <dd>CreateDate: 2015-12-3</dd>
 * 
 * @version 1.0
 * @author 张海波
 */
public class UtilDate {

	private UtilDate() {
	}

	public static Timestamp toFirstDayOfMonth(Date srcTime) {
		return new Timestamp(toFirstDayOfMonth(srcTime.getTime()));
	}

	public static Timestamp toFirstDayOfMonth(Timestamp srcTime) {
		return new Timestamp(toFirstDayOfMonth(srcTime.getTime()));
	}

	public static Timestamp toFirstDayOfMonth(java.sql.Date srcTime) {
		return new Timestamp(toFirstDayOfMonth(srcTime.getTime()));
	}

	/**
	 * <p>Discription:[当前日期，所在自然月第一天]</p>
	 * @param srcTime
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static long toFirstDayOfMonth(long srcTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(srcTime);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static Timestamp toLastDayOfMonth(Date srcTime) {
		return new Timestamp(toLastDayOfMonth(srcTime.getTime()));
	}

	public static Timestamp toLastDayOfMonth(Timestamp srcTime) {
		return new Timestamp(toLastDayOfMonth(srcTime.getTime()));
	}

	public static Timestamp toLastDayOfMonth(java.sql.Date srcTime) {
		return new Timestamp(toLastDayOfMonth(srcTime.getTime()));
	}

	/**
	 * <p>Discription:[当前日期,所在自然月最后一天]</p>
	 * @param srcTime
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static long toLastDayOfMonth(long srcTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(srcTime);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static Timestamp toStartTimeOfDay(Date srcTime) {
		return new Timestamp(toStartTimeOfDay(srcTime.getTime()));
	}

	public static Timestamp toStartTimeOfDay(Timestamp srcTime) {
		return new Timestamp(toStartTimeOfDay(srcTime.getTime()));
	}

	public static Timestamp toStartTimeOfDay(java.sql.Date srcTime) {
		return new Timestamp(toStartTimeOfDay(srcTime.getTime()));
	}

	/**
	 * <p>Discription:[当前日期开始时间]</p>
	 * @param srcTime 当前日期
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static long toStartTimeOfDay(long srcTime) {
		return toRangeTimeOfDay(srcTime, 0, 0, 0, 0);
	}

	public static Timestamp toEndTimeOfDay(Date srcTime) {
		return new Timestamp(toEndTimeOfDay(srcTime.getTime()));
	}

	public static Timestamp toEndTimeOfDay(Timestamp srcTime) {
		return new Timestamp(toEndTimeOfDay(srcTime.getTime()));
	}

	public static Timestamp toEndTimeOfDay(java.sql.Date srcTime) {
		return new Timestamp(toEndTimeOfDay(srcTime.getTime()));
	}

	/**
	 * <p>Discription:[当前日期结束时间]</p>
	 * @param srcTime 当前日期
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static long toEndTimeOfDay(long srcTime) {
		return toRangeTimeOfDay(srcTime, 23, 59, 59, 999);
	}
	
	/**
	 * <p>Discription:[设置当前日期指定时间]</p>
	 * @param srcTime 当前日期
	 * @param hour	 小时
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond 毫秒
	 * @return
	 * @author 张海波  2017-5-11
	 */
	public static long toRangeTimeOfDay(long srcTime, int hour, int minute, int second, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(srcTime);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * <p>Discription:[当前日期, 周几]</p>
	 * @param srcTime  当前日期
	 * @return
	 * @author 张海波  2017-5-11
	 */
	public static int getDayOfWeek(long srcTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(srcTime);
		int idx = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDays[idx];
	}
	
	private static int weekDays[] = { 0, 1, 2, 3, 4, 5, 6 };
	
	
	/**
	 * <p>Discription:[当前日期,当月第几周]</p>
	 * @param srcTime
	 * @return
	 * @author 张海波  2017-5-11
	 */
	public static int getWeekOfMonth(long srcTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(srcTime);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}
	

	/**
	 * <p>Discription:[向前或向后 推N天]</p>
	 * @param srcTime
	 * @param nDay >0 向后  =0 不变 <0 向前
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static Timestamp addDayOf(Date srcTime, int nDay) {
		return new Timestamp(addDayOf(srcTime.getTime(), nDay));
	}

	public static Timestamp addDayOf(Timestamp srcTime, int nDay) {
		return new Timestamp(addDayOf(srcTime.getTime(), nDay));
	}

	public static Timestamp addDayOf(java.sql.Date srcTime, int nDay) {
		return new Timestamp(addDayOf(srcTime.getTime(), nDay));
	}

	/**
	 * <p>Discription:[向前或向后 推N天]</p>
	 * @param srcTime
	 * @param nDay >0 向后  =0 不变 <0 向前
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static long addDayOf(long srcTime, int nDay) {
		return addCalendar(srcTime, Calendar.DAY_OF_MONTH, nDay);
	}

	public static Timestamp addMonthOf(Date srcTime, int nMonth) {
		return new Timestamp(addMonthOf(srcTime.getTime(), nMonth));
	}

	public static Timestamp addMonthOf(Timestamp srcTime, int nMonth) {
		return new Timestamp(addMonthOf(srcTime.getTime(), nMonth));
	}

	public static Timestamp addMonthOf(java.sql.Date srcTime, int nMonth) {
		return new Timestamp(addMonthOf(srcTime.getTime(), nMonth));
	}

	/**
	 * <p>Discription:[向前或向后 推N月]</p>
	 * @param srcTime
	 * @param nMonth  >0 向后  =0 不变 <0 向前
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static long addMonthOf(long srcTime, int nMonth) {
		return addCalendar(srcTime, Calendar.MONTH, nMonth);
	}

	public static Timestamp addWeekOf(Date srcTime, int nWeek) {
		return new Timestamp(addWeekOf(srcTime.getTime(), nWeek));
	}

	public static Timestamp addWeekOf(Timestamp srcTime, int nWeek) {
		return new Timestamp(addWeekOf(srcTime.getTime(), nWeek));
	}

	public static Timestamp addWeekOf(java.sql.Date srcTime, int nWeek) {
		return new Timestamp(addWeekOf(srcTime.getTime(), nWeek));
	}

	/**
	 * <p>Discription:[向前或向后 推N周]</p>
	 * @param srcTime
	 * @param nWeek >0 向后  =0 不变 <0 向前
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static long addWeekOf(long srcTime, int nWeek) {
		return addCalendar(srcTime, Calendar.WEEK_OF_MONTH, nWeek);
	}

	public static Timestamp addYearOf(Date srcTime, int nYear) {
		return new Timestamp(addYearOf(srcTime.getTime(), nYear));
	}

	public static Timestamp addYearOf(Timestamp srcTime, int nYear) {
		return new Timestamp(addYearOf(srcTime.getTime(), nYear));
	}

	public static Timestamp addYearOf(java.sql.Date srcTime, int nYear) {
		return new Timestamp(addYearOf(srcTime.getTime(), nYear));
	}

	/**
	 * <p>Discription:[向前或向后 推N年]</p>
	 * @param srcTime
	 * @param nYear >0 向后  =0 不变 <0 向前
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static long addYearOf(long srcTime, int nYear) {
		return addCalendar(srcTime, Calendar.YEAR, nYear);
	}

	public static Timestamp addHourOf(Date srcTime, int nHour) {
		return new Timestamp(addHourOf(srcTime.getTime(), nHour));
	}

	public static Timestamp addHourOf(Timestamp srcTime, int nHour) {
		return new Timestamp(addHourOf(srcTime.getTime(), nHour));
	}

	public static Timestamp addHourOf(java.sql.Date srcTime, int nHour) {
		return new Timestamp(addHourOf(srcTime.getTime(), nHour));
	}

	/**
	 * <p>Discription:[向前或向后 推N小时]</p>
	 * @param srcTime
	 * @param nHour >0 向后  =0 不变 <0 向前
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static long addHourOf(long srcTime, int nHour) {
		return addCalendar(srcTime, Calendar.HOUR_OF_DAY, nHour);
	}

	private static final long addCalendar(long srcTime, int calendarField, int nAmount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(srcTime);
		calendar.add(calendarField, nAmount);
		return calendar.getTimeInMillis();
	}

	public static final long parseLongDate(String srcTime) {
		return parseDate(srcTime, getDateFormatPattern(srcTime), Locale.getDefault()).getTime();
	}

	public static final long parseLongDate(String srcTime, String pattern) {
		return parseDate(srcTime, pattern, Locale.getDefault()).getTime();
	}
	
	public static final long parseLongDate(String srcTime, String pattern, Locale locale) {
		return parseDate(srcTime, pattern, locale).getTime();
	}
	
	public static final java.sql.Date parseSqlDate(String srcTime) {
		return new java.sql.Date(parseDate(srcTime, getDateFormatPattern(srcTime), Locale.getDefault()).getTime());
	}

	public static final java.sql.Date parseSqlDate(String srcTime, String pattern) {
		return new java.sql.Date(parseDate(srcTime, pattern, Locale.getDefault()).getTime());
	}
	
	public static final java.sql.Date parseSqlDate(String srcTime, String pattern, Locale locale) {
		return new java.sql.Date(parseDate(srcTime, pattern, locale).getTime());
	}
	
	public static final Timestamp parseTimestamp(String srcTime) {
		return new Timestamp(parseDate(srcTime, getDateFormatPattern(srcTime), Locale.getDefault()).getTime());
	}
	
	public static final Timestamp parseTimestamp(String srcTime, String pattern) {
		return new Timestamp(parseDate(srcTime, pattern, Locale.getDefault()).getTime());
	}

	public static final Timestamp parseTimestamp(String srcTime, String pattern, Locale locale) {
		return new Timestamp(parseDate(srcTime, pattern, locale).getTime());
	}
	
	public static final Date parseDate(String srcTime) {
		return parseDate(srcTime, getDateFormatPattern(srcTime), Locale.getDefault());
	}

	public static final Date parseDate(String srcTime, String pattern) {
		return parseDate(srcTime, pattern, Locale.getDefault());
	}

	/**
	 * <p>Discription:[解析日期格式字符串]</p>
	 * @param srcTime
	 * @param pattern
	 * @param locale
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static final Date parseDate(String srcTime, String pattern, Locale locale) {
		if (pattern == null || isSupportPattern(pattern))
			pattern = DEFAULT_TIME_FORMAT;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		try {
			return sdf.parse(srcTime);
		} catch (ParseException ex) {
			throw new ConversionException(ex, srcTime, Date.class);
		}
	}

	private static final boolean isSupportPattern(String patterns) {
		if (patterns == null)
			return false;
//		Map<String, Pattern> dateFormatMap = ClassManager.getDatePatternMap();
		Map<String, Pattern> dateFormatMap = null;
		Pattern pattern = dateFormatMap.get(patterns);
		return pattern != null;
	}

	public static final String formatDate(long srcTime) {
		return formatDate(new Date(srcTime), DEFAULT_TIME_FORMAT, Locale.getDefault());
	}
	
	public static final String formatDate(long srcTime, String pattern) {
		return formatDate(new Date(srcTime), pattern, Locale.getDefault());
	}
	
	public static final String formatDate(long srcTime, String pattern, Locale locale) {
		return formatDate(new Date(srcTime), pattern, locale);
	}

	public static final String formatDate(java.sql.Date srcTime) {
		return formatDate((Date) srcTime, DEFAULT_TIME_FORMAT, Locale.getDefault());
	}

	public static final String formatDate(java.sql.Date srcTime, String pattern) {
		return formatDate((Date) srcTime, pattern, Locale.getDefault());
	}
	
	public static final String formatDate(java.sql.Date srcTime, String pattern, Locale locale) {
		return formatDate((Date) srcTime, pattern, locale);
	}
	
	public static final String formatDate(Timestamp srcTime) {
		return formatDate((Date) srcTime, DEFAULT_TIME_FORMAT, Locale.getDefault());
	}

	public static final String formatDate(Timestamp srcTime, String pattern) {
		return formatDate((Date) srcTime, pattern, Locale.getDefault());
	}

	public static final String formatDate(Timestamp srcTime, String pattern, Locale locale) {
		return formatDate((Date) srcTime, pattern, locale);
	}
	
	public static final String formatDate(Date srcTime) {
		return formatDate(srcTime, DEFAULT_TIME_FORMAT, Locale.getDefault());
	}
	
	public static final String formatDate(Date srcTime, String pattern) {
		return formatDate(srcTime, pattern, Locale.getDefault());
	}

	/**
	 * <p>Discription:[格式化日期]</p>
	 * @param srcTime
	 * @param pattern
	 * @param locale
	 * @return
	 * @author zhanghaibo3  2015-12-3
	 */
	public static final String formatDate(Date srcTime, String pattern, Locale locale) {
		if (UtilString.isEmpty(pattern))
			pattern = DEFAULT_TIME_FORMAT;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		return sdf.format(srcTime);
	}

	public static boolean isDateFormat(CharSequence strDate) {
		String pattern = getDateFormatPattern(strDate);
		return pattern != null;
	}

	/**
	 * <p>Discription:[根据字符串值匹配日期表达式]</p>
	 * @param strDate
	 * @return 未匹配，则返回 null
	 * @author zhanghaibo3  2016-2-6
	 */
	public static String getDateFormatPattern(CharSequence strDate) {
//		Map<String, Pattern> dateFormatMap = ClassManager.getDatePatternMap();
		Map<String, Pattern> dateFormatMap = null;
		Iterator<String> it = dateFormatMap.keySet().iterator();
		while (it.hasNext()) {
			String patternstr = it.next();
			Matcher matcher = dateFormatMap.get(patternstr).matcher(strDate);
			if (matcher.matches())
				return patternstr;
		}
		return null;
	}
	
	public static Date convertTimeZone(Timestamp cur, TimeZone timeZone) {
		return null;
	}

//	private static Map<String, Pattern> dateFormatMap = new HashMap<String, Pattern>(40);

//	private static Map<String, ZoneShort> zoneShortMap = new HashMap<String, ZoneShort>();

	// 粗糙的日期 表达式   2014-09-30 01:01:59.0 可以 2014-9-3 1:0:0.0
	static {
		

		/////////////////////////////////////////////////////
		
//		
//		NST -03:30 纽芬兰（Newfoundland）标准时间
//		NFT -03:30 纽芬兰（Newfoundland）标准时间
//		NDT -02:30 纽芬兰夏时制
//		NT -11:00 阿拉斯加诺姆时间（Nome Time）
		
//		ZoneShort zoneShort = new ZoneShort("NZDT",13F,"新西兰夏时制");
//		zoneShortMap.put(zoneShort.getZoneShort(), zoneShort);
//		
//		zoneShort = new ZoneShort("NZST",12F,"新西兰标准时间");
//		zoneShortMap.put(zoneShort.getZoneShort(), zoneShort);
//		
//		zoneShort = new ZoneShort("NZT",12F,"新西兰时间");
//		zoneShortMap.put(zoneShort.getZoneShort(), zoneShort);
//		
//		zoneShort = new ZoneShort("NOR",1F,"挪威标准时间");
//		zoneShortMap.put(zoneShort.getZoneShort(), zoneShort);
		
		
	}

	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String yyyy_MM_dd_HH_mm_ss__SSS = "yyyy-MM-dd HH:mm:ss,SSS";

	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyyyMM = "yyyyMM";
	public static final String yyyy = "yyyy";

	public static final String DEFAULT_TIME_FORMAT = yyyy_MM_dd_HH_mm_ss_SSS;

	static class ZoneShort {

		protected ZoneShort(String zoneShort, float gmt, String displayName) {
			this.zoneShort = zoneShort;
			this.gmt = gmt;
			this.displayName = displayName;
		}

		private String zoneShort;
		private float gmt;
		private String displayName;
		private String stringCache;
		private String gmtStrCache;

		public String toString() {
			return stringCache = stringCache == null ? zoneShort + " " + getGMTString() + " " + displayName : stringCache;
		}

		public String getGMTString() {
			String sc = gmtStrCache;
			if (sc != null)
				return sc;

			StringBuilder sb = new StringBuilder(10);
			BigDecimal bd = BigDecimal.valueOf(gmt).setScale(2, RoundingMode.HALF_UP);
			sb = bd.floatValue() >= 0 ? sb.append("+") : sb.append("-");
			int pre = bd.precision();
			int scale = bd.scale();
			String str = bd.toString();
			str = str.replace("\\.", ":");
			sb = pre - scale == 1 ? sb.append("0").append(str) : sb.append(str);
			gmtStrCache = sb.toString();
			return gmtStrCache;
		}

		public String getZoneShort() {
			return zoneShort;
		}

		public void setZoneShort(String zoneShort) {
			this.zoneShort = zoneShort;
		}

		public float getGmt() {
			return gmt;
		}

		public void setGmt(float gmt) {
			this.gmt = gmt;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

	}
	
	
	/*
	/* RFC 1123 with 2-digit Year */				//	"EEE, dd MMM yy HH:mm:ss z",
	/* RFC 1123 with 4-digit Year */				//	"EEE, dd MMM yyyy HH:mm:ss z",
	/* RFC 1123 with no Timezone */					//	"EEE, dd MMM yy HH:mm:ss",
	/* Variant of RFC 1123 */						//	"EEE, MMM dd yy HH:mm:ss",
	/* RFC 1123 with no Seconds */					//	"EEE, dd MMM yy HH:mm z",
	/* Variant of RFC 1123 */						//	"EEE dd MMM yyyy HH:mm:ss",
	/* RFC 1123 with no Day */						//	"dd MMM yy HH:mm:ss z",
	/* RFC 1123 with no Day or Seconds */			//	"dd MMM yy HH:mm z",
	/* ISO 8601 slightly modified */				//	"yyyy-MM-dd'T'HH:mm:ssZ",
	/* ISO 8601 slightly modified */				//	"yyyy-MM-dd'T'HH:mm:ss'Z'",
	/* ISO 8601 slightly modified */				//	"yyyy-MM-dd'T'HH:mm:sszzzz",
	/* ISO 8601 slightly modified */				//	"yyyy-MM-dd'T'HH:mm:ss z",
	/* ISO 8601 */									//	"yyyy-MM-dd'T'HH:mm:ssz",
	/* ISO 8601 slightly modified */				//	"yyyy-MM-dd'T'HH:mm:ss.SSSz",
	/* ISO 8601 slightly modified */				//	"yyyy-MM-dd'T'HHmmss.SSSz",
	/* ISO 8601 slightly modified */				//	"yyyy-MM-dd'T'HH:mm:ss",
	/* ISO 8601 w/o seconds */						//	"yyyy-MM-dd'T'HH:mmZ",
	/* ISO 8601 w/o seconds */						//	"yyyy-MM-dd'T'HH:mm'Z'",
	/* RFC 1123 without Day Name */					//	"dd MMM yyyy HH:mm:ss z",
	/* RFC 1123 without Day Name and Seconds */		//	"dd MMM yyyy HH:mm z",
	/* Simple Date Format */						//	"yyyy-MM-dd",
	/* Simple Date Format */						//	"MMM dd, yyyy"  

	/*
		标准/说明 											示例 
		valid RFC 822 (2-digit year) 					Thu, 01 Jan 04 19:48:21 GMT 
		valid RFC 822 (4-digit year) 					Thu, 01 Jan 2004 19:48:21 GMT 
		invalid RFC 822 (no time) 						01 Jan 2004 
		invalid RFC 822 (no seconds) 					01 Jan 2004 00:00 GMT 
		valid W3CDTF (numeric timezone) 				2003-12-31T10:14:55-08:00 
		valid W3CDTF (UTC timezone) 					2003-12-31T10:14:55Z 
		valid W3CDTF (yyyy) 							2003 
		valid W3CDTF (yyyy-mm) 							2003-12 
		valid W3CDTF (yyyy-mm-dd) 						2003-12-31 
		valid ISO 8601 (yyyymmdd) 						20031231 
		valid ISO 8601 (-yy-mm) 						-03-12 
		valid ISO 8601 (-yymm) 							-0312 
		valid ISO 8601 (-yy-mm-dd) 						-03-12-31 
		valid ISO 8601 (yymmdd) 						031231 
		valid ISO 8601 (yyyy-o)	 						2003-335 
		valid ISO 8601 (yyo) 							03335 
		valid asctime 									Sun Jan 4 16:29:06 PST 2004 
		bogus RFC 822 (invalid day/month) 				Thu, 31 Jun 2004 19:48:21 GMT 
		bogus RFC 822 (invalid month) 					Mon, 26 January 2004 16:31:00 EST 
		bogus RFC 822 (invalid timezone) 				Mon, 26 Jan 2004 16:31:00 ET 
		bogus W3CDTF (invalid hour) 					2003-12-31T25:14:55Z 
		bogus W3CDTF (invalid minute) 					2003-12-31T10:61:55Z 
		bogus W3CDTF (invalid second) 					2003-12-31T10:14:61Z 
		bogus (MSSQL) 									2004-07-08 23:56:58.0 
		bogus (MSSQL-ish, without fractional second) 	2004-07-08 23:56:58 
		bogus (Korean) 									2004-05-25 ? 11:23:17 
		bogus (Greek) 									Κυρ, 11 Ιο?λ 2004 12:00:00 EST 
		bogus (Hungarian) 								július-13T9:15-05:00 
	*/
}
