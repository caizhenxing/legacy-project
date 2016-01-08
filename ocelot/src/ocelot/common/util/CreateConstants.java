/*
 * 创建日期 2005-7-5
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package ocelot.common.util;


import java.util.*;

//import net.sf.hibernate.*;

import java.math.BigDecimal;
import java.text.*;
/**
 * @author zhao yifei
 * @version 2.0 2005-08-10
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class CreateConstants {
	private static int seq=10;
	public synchronized static String getPKcode(String s)
	{
		Calendar c=Calendar.getInstance();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer pk=new StringBuffer();
		pk.append(s);
		pk.append(sdf.format(c.getTime()));
		
		if(seq==99)
		{
			seq=10;
			pk.append("99");
		}
		else
			{pk.append(seq);
				seq++;
			}
		return pk.toString();
	}
	public static Date getNowTime()
	{
		return new Date();
	}
	public static Date getTheTime(String t)throws ParseException
	{
		if(t==null||t.equals(""))
			return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.parse(t);
		
	}
	public static Date getTheTime(String t,String style)throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat(style);
		
		return sdf.parse(t);
		
	}
	public static String getTheTime(Date time,String style)throws ParseException
	{
		if(time==null)
			return "";
		SimpleDateFormat sdf=new SimpleDateFormat(style);
		
		return sdf.format(time);
	}
	public static String getTime(Date d)
	{
		if(d==null)
			return "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
	public static Double addDouble(Double d1,Double d2)
	{
		String add1=d1.toString();
		String add2=d2.toString();
		if(add1.startsWith("-")&&!add2.startsWith("-"))
			subDouble(d2,d1);
			
		int i1=add1.indexOf(".");
		int i2=add2.indexOf(".");
		long add1a=Long.parseLong(add1.substring(0,i1));
		long add2a=Long.parseLong(add2.substring(0,i2));
		add1a=add1a+add2a;
		String add1s=add1.substring(i1+1,add1.length());
		String add2s=add2.substring(i2+1,add2.length());
		
		if(add1s.length()>add2s.length())
		{
			char[] temp=new char[add1s.length()-add2s.length()];
			for(int i=0,size=temp.length;i<size;i++)
				temp[i]='0';
			add2s=add2s+new String(temp);
		}
		long add1b=Long.parseLong(add1s);
		long add2b=Long.parseLong(add2s);
		
		char[] temp1=new char[add1s.length()];
		for(int i=0,size=temp1.length;i<size;i++)
			temp1[i]='0';
		long size1=Long.parseLong("1"+new String(temp1));
		add1b=add1b+add2b;
		add1a=add1a+add1b/size1;
		return Double.valueOf(Long.toString(add1a)+"."+Long.toString(add1b%size1));
	}
	public static Double subDouble(Double minuend,Double subtrahend)
	{
		String sub1=minuend.toString();
		String sub2=subtrahend.toString();
		String aaa="";
		if(minuend.compareTo(subtrahend)>0)
		{
			sub1=minuend.toString();
			sub2=subtrahend.toString();
		}
		else
		{
			sub2=minuend.toString();
			sub1=subtrahend.toString();
			aaa="-";
		}
		int i1=sub1.indexOf(".");
		int i2=sub2.indexOf(".");
		long sub1a=Long.parseLong(sub1.substring(0,i1));
		long sub2a=Long.parseLong(sub2.substring(0,i2));
		sub1a=sub1a-sub2a;
		String sub1s=sub1.substring(i1+1,sub1.length());
		String sub2s=sub2.substring(i2+1,sub2.length());
		
		if(sub1s.length()>sub2s.length())
		{
			char[] temp=new char[sub1s.length()-sub2s.length()];
			for(int i=0,size=temp.length;i<size;i++)
				temp[i]='0';
			sub2s=sub2s+new String(temp);
		}
		long sub1b=Long.parseLong(sub1s);
		long sub2b=Long.parseLong(sub2s);
		
		char[] temp1=new char[sub1s.length()];
		for(int i=0,size=temp1.length;i<size;i++)
			temp1[i]='0';
		long size1=Long.parseLong("1"+new String(temp1));
		sub1b=sub1b+size1;
		sub1b=sub1b-sub2b;
		sub1a=sub1a-1;
		sub1a=sub1a+sub1b/size1;
		return Double.valueOf(aaa+Long.toString(sub1a)+"."+Long.toString(sub1b%size1));
		
	}
	/*public static String getCode(Session s,String type,String name)
	{
		
		try
		{
			Transaction tx=s.beginTransaction();
		Query code=s.createQuery("from Ccode as cc  where cc.comp_id.typeId=:typeid and cc.codeName=:name");
		code.setString("typeid",type);
		code.setString("name",name);
		//List l=code.list();
		com.forecast.cuss.pojo.Ccode c=(com.forecast.cuss.pojo.Ccode)code.uniqueResult();
		tx.commit();
		if(c!=null)
		return c.getComp_id().getCodeId();
		return "";
		}catch(HibernateException he)
		{
			he.printStackTrace();
			return null;
		}finally
		{
			
		}
	}
	public static String getCodeName(Session s,String type,String value)
	{
		try
		{
		Transaction tx=s.beginTransaction();
		Query code=s.createQuery("from Ccode as cc  where cc.comp_id.typeId=:typeid and cc.comp_id.codeId=:value");
		code.setString("typeid",type);
		code.setString("value",value);
		//List l=code.list();
		com.forecast.cuss.pojo.Ccode c=(com.forecast.cuss.pojo.Ccode)code.uniqueResult();//l.get(0);
		tx.commit();
		if(c!=null)
		return c.getCodeName();
		return "";
		}catch(HibernateException he)
		{
			he.printStackTrace();
			return null;
		}
	}*/
	public static void main(String[] arg0)
	{
		
		Double a1=new Double(-12.10);
		Double a2=new Double(-158.201);
		System.out.println(a1);
		//System.out.println(Long.parseLong("-3"));
		BigDecimal d1=new BigDecimal(a1.toString());
		BigDecimal d2=new BigDecimal(a2.toString());
	
		System.out.println(d1.add(d2));
		//Double a3=CreateConstants.subDouble(a1,a2);
		/*Date d;
		Date a;
		try {
			d = CreateConstants.getTheTime("2007-01-01 12:12:12");
			a=CreateConstants.getTheTime("2007-01-01 12:12:12");
			System.out.println(a.equals(d));
			System.out.println(a==d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
	}
}
