
package com.cc.common.util;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * 共用辅助方法 包含一些零散的便捷方法。
 */
public class ComUtils {

	public static final String	JSESSION_COOKIE	= "JSESSIONID";
	public static final String	JSESSION_URL	= "jsessionid";

	/**
	 * 获得当前时间。由于freemarker的日期必须有具体类型，所以使用timestamp。
	 */
	public static java.sql.Timestamp now() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * 格式化日期。yyyy-MM-dd
	 * 
	 * @param date
	 */
	public static String dateFormat(Date date) {
		return format.format(date);
	}

	/**
	 * 格式化日期。yyyy-MM-dd hh-mm-ss
	 * 
	 * @param date
	 */
	public static String dataFormatWhole(Date date) {
		return formatw.format(date);
	}

	public static String formatDate(Date date, int style) {
		if (date == null) {
			return "";
		}
		switch (style) {
			case 4:
				return formats.format(date);
			case 3:
				return formatm.format(date);
			case 2:
				return format.format(date);
			default:
				return formatw.format(date);
		}
	}

	public static final DateFormat	format	= new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat	formatw	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat	formatm	= new SimpleDateFormat("MM-dd HH:mm");
	public static final DateFormat	formats	= new SimpleDateFormat("MM-dd");

	public static String formatDate(Date date, String format) {
		SimpleDateFormat outFormat = new SimpleDateFormat(format);
		return outFormat.format(date);
	}

	/**
	 * Formats a Date object to return a date using the global locale.
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
		return outFormat.format(date);
	}

	/**
	 * Formats a Date object to return a date and time using the global locale.
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return outFormat.format(date);
	}

	public static String formatDate2(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate3(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate4(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate5(Date myDate) {
		String strDate = getYear(myDate) + "-" + getMonth(myDate) + "-" + getDay(myDate);
		return strDate;
	}

	public static String formatDate6(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static long Date2Long(int year, int month, int date) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date);
		return cld.getTime().getTime();
	}

	public static long Time2Long(int year, int month, int date, int hour, int minute, int second) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date, hour, minute, second);
		return cld.getTime().getTime();
	}

	public static int getYear(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MINUTE);
	}

	public static int getSecond(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.SECOND);
	}

	public static int getYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MINUTE);
	}

	public static int getSecond(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.SECOND);
	}

	public static int getYear() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static String replaceComma(String text) {
		if (text != null) {
			text = text.replaceAll("，", ",");
		}
		return text;
	}

	public static String replaceBr(String text) {
		if (text != null) {
			text = text.replaceAll("\n", "<BR>");
		}
		return text;
	}

	public static long getLongTime() {
		return System.currentTimeMillis();
	}

	/**
	 * Check a string null or blank.
	 * 
	 * @param param
	 *            string to check
	 * @return boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.length() == 0 || param.trim().equals("")) ? true : false;
	}

	public static String notNull(String param) {
		return param == null ? "" : param.trim();
	}

	public static Date parseString2Date(String s) {
		if (ComUtils.nullOrBlank(s)) {
			return null;
		}
		DateFormat dFormat = DateFormat.getDateInstance();
		try {
			return dFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parseString2Datetime(String s) {
		if (ComUtils.nullOrBlank(s)) {
			return null;
		}
		DateFormat dFormat = DateFormat.getDateTimeInstance();
		try {
			return dFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static FilenameFilter	DIR_FILE_FILTER	= new FilenameFilter() {

														@Override
														public boolean accept(File dir, String name) {
															if (dir.isDirectory()) {
																return true;
															} else {
																return false;
															}
														}
													};
}
