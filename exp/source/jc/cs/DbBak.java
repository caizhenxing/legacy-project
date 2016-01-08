/*
 * 创建日期 2004-12-23
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package jc.cs;
import java.util.*;
import java.io.*;
/**
 * @author zhaoyifei
 *
 */
public class DbBak {

	/**
	 * 
	 */
	private int mon;
	private static HashMap week,month;
	private int dat;
	private int date;
	private int day;
	private String userid="";
	private String password="";
	private String sid="";
	private String owner="";
	private int hour;
	private int minute;
	//String bak="exp "+userid+"/"+password+"@"+sid+" owner="+owner+"  buffer=65536 file=";
	String filepath="e:\\数据库备份\\";
	static
	{
		week=new HashMap();
		month=new HashMap();
		week.put("1","mon");
		week.put("2","tues");
		week.put("3","wed");
		week.put("4","thurs");
		week.put("5","fir");
		week.put("6","sat");
		week.put("7","sun");
		month.put("0","jan");
		month.put("1","feb");
		month.put("2","mar");
		month.put("3","apr");
		month.put("4","may");
		month.put("5","june");
		month.put("6","july");
		month.put("7","aug");
		month.put("8","sep");
		month.put("9","oct");
		month.put("10","nov");
		month.put("11","dec");
		
	}
	public DbBak() {
		super();
		// TODO 自动生成构造函数存根
		Date d=new Date();
		//Timer t=new Timer();
		//Calendar c=Calendar.getInstance();
		mon=d.getMonth();
		date=d.getDate();
		day=d.getDay();
		System.out.println(mon);
		System.out.println(date);
		System.out.println(day);
	}

	public void week()
	{
		String bak="exp "+userid+"/"+password+"@"+sid+" owner="+owner+"  buffer=65536 file=";
		StringBuffer cmd=new StringBuffer();
		cmd.append(bak);
		cmd.append(this.filepath);
		//Integer.toString(this.day)
		cmd.append((String)week.get(Integer.toString(this.day)));
		if(this.time())
		try
		{
			System.out.println(cmd.substring(0));
		Process process = Runtime.getRuntime().exec(cmd.toString());
		}catch(IOException ioe)
		{
			
		}
	}
	public void month()
	{
		String bak="exp "+userid+"/"+password+"@"+sid+" owner="+owner+"  buffer=65536 file=";
		StringBuffer cmd=new StringBuffer();
		cmd.append(bak);
		cmd.append(this.filepath);
		//Integer.toString(this.day)
		cmd.append((String)month.get(Integer.toString(this.mon)));
		if(this.dat==this.date)
		if(this.time())
		try
		{
			System.out.println(cmd.substring(0));
		Process process = Runtime.getRuntime().exec(cmd.toString());
		}catch(IOException ioe)
		{
			
		}
	}
	private boolean time()
	{
		Date d=new Date();
		if(this.hour==d.getHours()&&this.minute==d.getMinutes())
		return true;
		return false;
	}
	
	public static void main(String[] args) {
		DbBak db=new DbBak();
		db.setHour(10);
		db.setMinute(14);
		db.setOwner("jc");
		db.setPassword("jc");
		db.setSid("orcl_10.5.31.191");
		db.setUserid("jc");
		db.setDat(24);
		db.week();
		db.month();
	}
	/**
	 * @param string
	 */
	public void setOwner(String string) {
		owner = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setSid(String string) {
		sid = string;
	}

	/**
	 * @param string
	 */
	public void setUserid(String string) {
		userid = string;
	}



	/**
	 * @param i
	 */
	public void setHour(int i) {
		hour = i;
	}

	/**
	 * @param i
	 */
	public void setMinute(int i) {
		minute = i;
	}

	/**
	 * @param i
	 */
	public void setDat(int i) {
		dat = i;
	}

}
