package et.bo.pcc.policefuzz.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
	
	/**
	 * @describe 得到当前时间(Date类型)
	 * @return  类型  
	 * 
	 */
	public static Date getNowTime()
	{
		return new Date();
	}
	
	public static Date getTimeByStr(String t)
	{
		if(t==null||t.equals(""))
			return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date=sdf.parse(t);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return date;
		
	}
	public static Date getTimeByStr(String t,String style)
	{
		if(t==null||t.equals(""))
			return new Date();
		SimpleDateFormat sdf=new SimpleDateFormat(style);
		Date date=null;
		try {
			date=sdf.parse(t);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return date;
		
	}
	public static String getTheTimeStr(Date time,String style)
	{
		if(time==null)
			return null;
		if(style.equals(""))
			style="yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf=new SimpleDateFormat(style);
		String time1="";
		time1=sdf.format(time);
		return time1;
	}
	
	/**
	 * @describe 得到当前时间(年/月/日 分/秒/时)
	 * @param d 类型 Date 
	 * @return 类型  
	 * 
	 */
	public static String getTheTimeStr(Date d)
	{
		if(d==null)
			return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
	
	public static String getShortTime() {
		String shortTime = "";
		GregorianCalendar liftOffApollo11 = new GregorianCalendar();
		Date d = liftOffApollo11.getTime();
		DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
		shortTime = df.format(d);
		return shortTime;
	}
	
	public static String getNowSTime()
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}
	public static void main(String[] arg0)
	{
		System.out.println(TimeUtil.getNowSTime());
	}
}
