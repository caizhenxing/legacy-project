package ocelot.common.util.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


 /**
 * @author 赵一非
 * @version 2007-1-15
 * @see
 */
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
	/**
	 * @describe 得到当前时间(Date类型)
	 * @return  类型  
	 * 
	 */
	public static String getNowTime(String style)
	{
		SimpleDateFormat sdf=new SimpleDateFormat(style);
		Date d=new Date();
		return sdf.format(d);
	}
	
	public static Date getTimeByStr(String t)
	{
		if(t==null||t.equals(""))
			return null;
		String style=null;
		if(t.length()==19)
			style="yyyy-MM-dd HH:mm:ss";
		if(t.length()==10)
			style="yyyy-MM-dd";
		if(t.length()==8)
			style="HH:mm:ss";
		SimpleDateFormat sdf=new SimpleDateFormat(style);
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
		String style="yyyy-MM-dd HH:mm:ss";
		if(d.getHours()+d.getMinutes()+d.getSeconds()==0)
			style="yyyy-MM-dd";
		SimpleDateFormat sdf=new SimpleDateFormat(style);
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
	public static String getNowTimeSr()
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	public static void main(String[] arg0)
	{
		/*Date d=TimeUtil.getTimeByStr("2006-08-09");
		System.out.println(d.getDate());
		System.out.println(d.getHours());
		System.out.println(d.getMinutes());
		System.out.println(d.getSeconds());*/
		System.out.println(TimeUtil.getNowTime("yyyy-MM-dd"));
	}
}
