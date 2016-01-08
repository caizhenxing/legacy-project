/**
 * 
 */
package base.zyf.common.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import base.zyf.common.util.time.TimeUtil;


/**
 * @author zhangfeng 日期处理类
 */
public class DateUtil {

	/**
	 * 根据传入的日期返回字符串，年－月－日
	 * 
	 * @param date
	 * @return
	 */
	static public String getDateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * 根据传入的日期返回年的信息
	 * 
	 * @param date
	 * @return
	 */
	static public String getYear(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(date);
	}

	/**
	 * 根据传入的日期返回(yyyy年MM月dd日)的信息
	 * 
	 * @param date
	 * @return
	 */
	static public String getDateStrC(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}

	/**
	 * 根据传入的日期返回(yyyyMMdd)样式的字符串
	 * 
	 * @param date
	 * @return
	 */
	static public String getDateStrCompact(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 根据传入的日期返回(yyyy-MM-dd HH:mm:ss)格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	static public String getDateTimeStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 根据传入的日期返回(yyyy年MM月dd日 HH时mm分ss秒)格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	static public String getDateTimeStrC(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return format.format(date);
	}

	/**
	 * 返回当前日期的字符串，以指定的格式
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurDateStr(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * 将指定的日期按照指定的格式返回
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            指定的字符格式
	 * @return
	 */
	public static String getDateByStr(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 将指定格式的字符串返回成日期类型(yyyy-MM-dd)
	 * 
	 * @param s
	 *            字符串
	 * @return
	 * @throws ParseException
	 */
	static public Date parseDate(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(s);
	}

	/**
	 * 将指定格式的字符串返回成日期类型(yyyy年MM月dd日)
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public Date parseDateC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.parse(s);
	}

	/**
	 * 将指定格式的字符串返回成日期类型(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public Date parseDateTime(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(s);
	}

	/**
	 * 将指定格式的字符串返回成日期类型(yyyy年MM月dd日 HH时mm分ss秒)
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public Date parseDateTimeC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return format.parse(s);
	}

	/**
	 * 将指定格式的字符串返回成日期类型(HH:mm:ss)
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public Date parseTime(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.parse(s);
	}

	/**
	 * 将指定格式的字符串返回成日期类型(HH时mm分ss秒)
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public Date parseTimeC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("HH时mm分ss秒");
		return format.parse(s);
	}

	/**
	 * 根据传入的日期得到年的信息，返回int类型
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public int yearOfDate(Date s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(s);
		return Integer.parseInt(d.substring(0, 4));
	}

	/**
	 * 根据传入的日期得到月的信息，返回int类型
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public int monthOfDate(Date s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(s);
		return Integer.parseInt(d.substring(5, 7));
	}

	/**
	 * 根据传入的日期得到日的信息，返回int类型
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public int dayOfDate(Date s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(s);
		return Integer.parseInt(d.substring(8, 10));
	}

	/**
	 * 根据小时数返回(年－月－日 分：秒：时)
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	static public String getDateTimeStr(java.sql.Date date, double time) {
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int day = date.getDate();
		String dateStr = year + "-" + month + "-" + day;
		Double d = new Double(time);
		String timeStr = String.valueOf(d.intValue()) + ":00:00";

		return dateStr + " " + timeStr;
	}

	/**
	 * 返回两个日期之间的差的月数(两个日期之间差了多少个月)
	 * 
	 * @param sd
	 *            开始日期
	 * @param ed
	 *            结束日期
	 * @return
	 * @throws ParseException
	 */
	static public int diffDateM(Date sd, Date ed) throws ParseException {
		return (ed.getYear() - sd.getYear()) * 12 + ed.getMonth()
				- sd.getMonth() + 1;
	}

	/**
	 * 返回两个日期所差的天数
	 * 
	 * @param sd
	 *            开始日期
	 * @param ed
	 *            结束日期
	 * @return
	 * @throws ParseException
	 */
	static public int diffDateD(Date sd, Date ed) throws ParseException {
		return Math.round((ed.getTime() - sd.getTime()) / 86400000) + 1;
	}

	/**
	 * 得到两个日期之间相差的月份数
	 * 
	 * @param sym
	 * @param eym
	 * @return
	 * @throws ParseException
	 */
	static public int diffDateM(int sym, int eym) throws ParseException {
		return (Math.round(eym / 100) - Math.round(sym / 100)) * 12
				+ (eym % 100 - sym % 100) + 1;
	}

	/**
	 * 日期增加一个月，得到下一个月的今天
	 * 
	 * @param date
	 *            日期
	 * @return
	 * @throws ParseException
	 */
	static public java.sql.Date getNextMonthFirstDate(java.sql.Date date)
			throws ParseException {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(date);
		scalendar.add(Calendar.MONTH, 1);
		scalendar.set(Calendar.DATE, 1);
		return new java.sql.Date(scalendar.getTime().getTime());
	}

	/**
	 * 得到指定日期的前几天的日期
	 * 
	 * @param date
	 *            指定日期
	 * @param dayCount
	 *            几天
	 * @return
	 * @throws ParseException
	 */
	static public java.sql.Date getFrontDateByDayCount(java.sql.Date date,
			int dayCount) throws ParseException {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(date);
		scalendar.add(Calendar.DATE, -dayCount);
		return new java.sql.Date(scalendar.getTime().getTime());
	}

	/**
	 * 得到指定年月的第一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return
	 * @throws ParseException
	 */
	static public Date getFirstDay(String year, String month)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(year + "-" + month + "-1");
	}

	/**
	 * 得到指定年月的第一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return
	 * @throws ParseException
	 */
	static public Date getFirstDay(int year, int month) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(year + "-" + month + "-1");
	}

	/**
	 * 得到指定年月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return
	 * @throws ParseException
	 */
	static public Date getLastDay(String year, String month)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(year + "-" + month + "-1");

		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(date);
		scalendar.add(Calendar.MONTH, 1);
		scalendar.add(Calendar.DATE, -1);
		date = scalendar.getTime();
		return date;
	}

	/**
	 * 得到指定年月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return
	 * @throws ParseException
	 */
	static public Date getLastDay(int year, int month) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(year + "-" + month + "-1");

		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(date);
		scalendar.add(Calendar.MONTH, 1);
		scalendar.add(Calendar.DATE, -1);
		date = scalendar.getTime();
		return date;
	}

	/**
	 * 得到当前年月日的信息(返回yyyy-MM-dd)
	 * 
	 * @return
	 * @throws ParseException
	 */
	static public String getTodayStr() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		return getDateStr(calendar.getTime());
	}

	/**
	 * 得到当前日期，返回日期类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	static public Date getToday() throws ParseException {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 得到当前日期，包括分秒时及毫秒数(2008-10-16 13:17:56.156)
	 * 
	 * @return
	 */
	static public String getTodayAndTime() {
		return new Timestamp(System.currentTimeMillis()).toString();
	}

	/**
	 * 得到当前日期(例：2008年10月16日)
	 * 
	 * @return
	 * @throws ParseException
	 */
	static public String getTodayC() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		return getDateStrC(calendar.getTime());
	}

	/**
	 * 得到当前日期(例：200810)
	 * 
	 * @return
	 * @throws ParseException
	 */
	static public int getThisYearMonth() throws ParseException {
		Date today = Calendar.getInstance().getTime();
		return (today.getYear() + 1900) * 100 + today.getMonth() + 1;
	}

	/**
	 * 根据指定日期得到此格式(例：200810)
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	static public int getYearMonth(Date date) throws ParseException {
		return (date.getYear() + 1900) * 100 + date.getMonth() + 1;
	}

	/**
	 * 获取两个日期相隔的月数
	 * 
	 * @param beforedate
	 *            开始日期
	 * @param afterdate
	 *            结束日期
	 * @return
	 * @throws ParseException
	 */
	static public long getDistinceMonth(String beforedate, String afterdate)
			throws ParseException {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		long monthCount = 0;
		try {
			java.util.Date d1 = d.parse(beforedate);
			java.util.Date d2 = d.parse(afterdate);

			monthCount = (d2.getYear() - d1.getYear()) * 12 + d2.getMonth()
					- d1.getMonth();
			// dayCount = (d2.getTime()-d1.getTime())/(30*24*60*60*1000);

		} catch (ParseException e) {
			System.out.println("Date parse error!");
			// throw e;
		}
		return monthCount;
	}

	/**
	 * 获取两个日期相隔的天数
	 * 
	 * @param beforedate
	 * @param afterdate
	 * @return
	 * @throws ParseException
	 */
	static public long getDistinceDay(String beforedate, String afterdate)
			throws ParseException {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		long dayCount = 0;
		try {
			java.util.Date d1 = d.parse(beforedate);
			java.util.Date d2 = d.parse(afterdate);

			dayCount = (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);

		} catch (ParseException e) {
			System.out.println("Date parse error!");
			// throw e;
		}
		return dayCount;
	}

	/**
	 * 获取两个日期相隔的天数
	 * 
	 * @param beforedate
	 * @param afterdate
	 * @return
	 * @throws ParseException
	 */
	static public long getDistinceDay(Date beforedate, Date afterdate)
			throws ParseException {
		long dayCount = 0;

		try {
			dayCount = (afterdate.getTime() - beforedate.getTime())
					/ (24 * 60 * 60 * 1000);

		} catch (Exception e) {
			// System.out.println("Date parse error!");
			// // throw e;
		}
		return dayCount;
	}

	/**
	 * 获取相隔的天数
	 * 
	 * @param beforedate
	 * @param afterdate
	 * @return
	 * @throws ParseException
	 */
	static public long getDistinceDay(java.sql.Date beforedate,
			java.sql.Date afterdate) throws ParseException {
		long dayCount = 0;

		try {
			dayCount = (afterdate.getTime() - beforedate.getTime())
					/ (24 * 60 * 60 * 1000);

		} catch (Exception e) {
			// System.out.println("Date parse error!");
			// // throw e;
		}
		return dayCount;
	}

	/**
	 * 获得到今天相隔的天数
	 * 
	 * @param beforedate
	 * @return
	 * @throws ParseException
	 */
	static public long getDistinceDay(String beforedate) throws ParseException {
		return getDistinceDay(beforedate, getTodayStr());
	}

	/**
	 * 获得相隔的时间数
	 * 
	 * @param beforeDateTime
	 * @param afterDateTime
	 * @return
	 * @throws ParseException
	 */
	static public long getDistinceTime(String beforeDateTime,
			String afterDateTime) throws ParseException {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long timeCount = 0;
		try {
			java.util.Date d1 = d.parse(beforeDateTime);
			java.util.Date d2 = d.parse(afterDateTime);

			timeCount = (d2.getTime() - d1.getTime()) / (60 * 60 * 1000);

		} catch (ParseException e) {
			System.out.println("Date parse error!");
			throw e;
		}
		return timeCount;
	}

	/**
	 * 获得到当前相隔的时间数
	 * 
	 * @param beforeDateTime
	 * @return
	 * @throws ParseException
	 */
	static public long getDistinceTime(String beforeDateTime)
			throws ParseException {
		return getDistinceTime(beforeDateTime, new Timestamp(System
				.currentTimeMillis()).toLocaleString());
	}

	/**
	 * 比较指定日期和当前日期，如果当前日期在指定日期之后，返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean dateCompare(String str) {
		boolean bea = false;
		SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd");
		String isDate = sdf_d.format(new java.util.Date());
		java.util.Date date1;
		java.util.Date date0;
		try {
			date1 = sdf_d.parse(str);
			date0 = sdf_d.parse(isDate);
			if (date0.after(date1)) {
				bea = true;
			}
		} catch (ParseException e) {
			bea = false;
		}
		return bea;
	}

	/**
	 * 自动在当前日期加上一天的时间，是在查询的时候使用，当用户输入同一天时，查询的结果为明天00:00:00
	 * 
	 * @param endTime
	 *            传入的格式为字符串型的日期格式
	 * @return
	 */
	public static String addoneDate(String endTime) {
		Date date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd"));
		ca.add(ca.DATE, 1);
		date = ca.getTime();
		endTime = TimeUtil.getTheTimeStr(date, "yyyy-MM-dd HH:mm:ss");
		return endTime;
	}

	/**
	 * 自动将当前的时间设置为今天的00:00:00，用于开始的时候查询时使用
	 * 
	 * @param stime 传入的日期格式的字符串
	 * @return
	 */
	public static String addHMS(String stime) {
		String hmsTime = "";
		hmsTime = stime + " 00:00:00";
		return hmsTime;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// System.out.println(DateUtil.getTodayStr());
			// System.out.println(DateUtil.getToday());
			// System.out.println(DateUtil.getTodayAndTime());
			// System.out.println(DateUtil.getTodayC());
			// System.out.println(DateUtil.getThisYearMonth());
			System.out.println(DateUtil.getYearMonth(new Date()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
