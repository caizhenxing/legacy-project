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
 * @author zhangfeng ���ڴ�����
 */
public class DateUtil {

	/**
	 * ���ݴ�������ڷ����ַ������꣭�£���
	 * 
	 * @param date
	 * @return
	 */
	static public String getDateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * ���ݴ�������ڷ��������Ϣ
	 * 
	 * @param date
	 * @return
	 */
	static public String getYear(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(date);
	}

	/**
	 * ���ݴ�������ڷ���(yyyy��MM��dd��)����Ϣ
	 * 
	 * @param date
	 * @return
	 */
	static public String getDateStrC(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd��");
		return format.format(date);
	}

	/**
	 * ���ݴ�������ڷ���(yyyyMMdd)��ʽ���ַ���
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
	 * ���ݴ�������ڷ���(yyyy-MM-dd HH:mm:ss)��ʽ���ַ���
	 * 
	 * @param date
	 * @return
	 */
	static public String getDateTimeStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * ���ݴ�������ڷ���(yyyy��MM��dd�� HHʱmm��ss��)��ʽ���ַ���
	 * 
	 * @param date
	 * @return
	 */
	static public String getDateTimeStrC(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
		return format.format(date);
	}

	/**
	 * ���ص�ǰ���ڵ��ַ�������ָ���ĸ�ʽ
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurDateStr(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * ��ָ�������ڰ���ָ���ĸ�ʽ����
	 * 
	 * @param date
	 *            ����
	 * @param pattern
	 *            ָ�����ַ���ʽ
	 * @return
	 */
	public static String getDateByStr(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * ��ָ����ʽ���ַ������س���������(yyyy-MM-dd)
	 * 
	 * @param s
	 *            �ַ���
	 * @return
	 * @throws ParseException
	 */
	static public Date parseDate(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(s);
	}

	/**
	 * ��ָ����ʽ���ַ������س���������(yyyy��MM��dd��)
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public Date parseDateC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd��");
		return format.parse(s);
	}

	/**
	 * ��ָ����ʽ���ַ������س���������(yyyy-MM-dd HH:mm:ss)
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
	 * ��ָ����ʽ���ַ������س���������(yyyy��MM��dd�� HHʱmm��ss��)
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public Date parseDateTimeC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
		return format.parse(s);
	}

	/**
	 * ��ָ����ʽ���ַ������س���������(HH:mm:ss)
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
	 * ��ָ����ʽ���ַ������س���������(HHʱmm��ss��)
	 * 
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	static public Date parseTimeC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("HHʱmm��ss��");
		return format.parse(s);
	}

	/**
	 * ���ݴ�������ڵõ������Ϣ������int����
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
	 * ���ݴ�������ڵõ��µ���Ϣ������int����
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
	 * ���ݴ�������ڵõ��յ���Ϣ������int����
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
	 * ����Сʱ������(�꣭�£��� �֣��룺ʱ)
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
	 * ������������֮��Ĳ������(��������֮����˶��ٸ���)
	 * 
	 * @param sd
	 *            ��ʼ����
	 * @param ed
	 *            ��������
	 * @return
	 * @throws ParseException
	 */
	static public int diffDateM(Date sd, Date ed) throws ParseException {
		return (ed.getYear() - sd.getYear()) * 12 + ed.getMonth()
				- sd.getMonth() + 1;
	}

	/**
	 * ���������������������
	 * 
	 * @param sd
	 *            ��ʼ����
	 * @param ed
	 *            ��������
	 * @return
	 * @throws ParseException
	 */
	static public int diffDateD(Date sd, Date ed) throws ParseException {
		return Math.round((ed.getTime() - sd.getTime()) / 86400000) + 1;
	}

	/**
	 * �õ���������֮�������·���
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
	 * ��������һ���£��õ���һ���µĽ���
	 * 
	 * @param date
	 *            ����
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
	 * �õ�ָ�����ڵ�ǰ���������
	 * 
	 * @param date
	 *            ָ������
	 * @param dayCount
	 *            ����
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
	 * �õ�ָ�����µĵ�һ��
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return
	 * @throws ParseException
	 */
	static public Date getFirstDay(String year, String month)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(year + "-" + month + "-1");
	}

	/**
	 * �õ�ָ�����µĵ�һ��
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return
	 * @throws ParseException
	 */
	static public Date getFirstDay(int year, int month) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(year + "-" + month + "-1");
	}

	/**
	 * �õ�ָ�����µ����һ��
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
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
	 * �õ�ָ�����µ����һ��
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
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
	 * �õ���ǰ�����յ���Ϣ(����yyyy-MM-dd)
	 * 
	 * @return
	 * @throws ParseException
	 */
	static public String getTodayStr() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		return getDateStr(calendar.getTime());
	}

	/**
	 * �õ���ǰ���ڣ�������������
	 * 
	 * @return
	 * @throws ParseException
	 */
	static public Date getToday() throws ParseException {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * �õ���ǰ���ڣ���������ʱ��������(2008-10-16 13:17:56.156)
	 * 
	 * @return
	 */
	static public String getTodayAndTime() {
		return new Timestamp(System.currentTimeMillis()).toString();
	}

	/**
	 * �õ���ǰ����(����2008��10��16��)
	 * 
	 * @return
	 * @throws ParseException
	 */
	static public String getTodayC() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		return getDateStrC(calendar.getTime());
	}

	/**
	 * �õ���ǰ����(����200810)
	 * 
	 * @return
	 * @throws ParseException
	 */
	static public int getThisYearMonth() throws ParseException {
		Date today = Calendar.getInstance().getTime();
		return (today.getYear() + 1900) * 100 + today.getMonth() + 1;
	}

	/**
	 * ����ָ�����ڵõ��˸�ʽ(����200810)
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	static public int getYearMonth(Date date) throws ParseException {
		return (date.getYear() + 1900) * 100 + date.getMonth() + 1;
	}

	/**
	 * ��ȡ�����������������
	 * 
	 * @param beforedate
	 *            ��ʼ����
	 * @param afterdate
	 *            ��������
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
	 * ��ȡ�����������������
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
	 * ��ȡ�����������������
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
	 * ��ȡ���������
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
	 * ��õ��������������
	 * 
	 * @param beforedate
	 * @return
	 * @throws ParseException
	 */
	static public long getDistinceDay(String beforedate) throws ParseException {
		return getDistinceDay(beforedate, getTodayStr());
	}

	/**
	 * ��������ʱ����
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
	 * ��õ���ǰ�����ʱ����
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
	 * �Ƚ�ָ�����ں͵�ǰ���ڣ������ǰ������ָ������֮�󣬷���true
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
	 * �Զ��ڵ�ǰ���ڼ���һ���ʱ�䣬���ڲ�ѯ��ʱ��ʹ�ã����û�����ͬһ��ʱ����ѯ�Ľ��Ϊ����00:00:00
	 * 
	 * @param endTime
	 *            ����ĸ�ʽΪ�ַ����͵����ڸ�ʽ
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
	 * �Զ�����ǰ��ʱ������Ϊ�����00:00:00�����ڿ�ʼ��ʱ���ѯʱʹ��
	 * 
	 * @param stime ��������ڸ�ʽ���ַ���
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
