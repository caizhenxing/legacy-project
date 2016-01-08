
package com.zyf.tools;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.zyf.common.crud.domain.CommonTableView;

/**
 * @author Administrator
 *
 */
public class Tools {

	
	
	
	private static int seq=1;
	private static final int max=999999;
	public synchronized static String getPKCode()
	{
		Calendar c=Calendar.getInstance();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer pk=new StringBuffer();
		pk.append("qware");
		pk.append(sdf.format(c.getTime()));
		
		String seqs=Integer.toString(seq);
		int num=seqs.length();
		
		if(num<6)
		{
			char[] temp=new char[6-num];
			Arrays.fill(temp, '0');
			pk.append(temp);
		}
		pk.append(seqs);
		if(seq<max)
		{
		seq++;
		}else
		{
			seq=1;
		}
		return pk.toString();
	}
	public static Date getNowTime()
	{
		return new Date();
	}
	public static Date getTheTime(String t)
	{
		if(t==null||t.equals(""))
			return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			return sdf.parse(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	public static Date getTheTime(String t,String style)
	{
		SimpleDateFormat sdf=new SimpleDateFormat(style);
		
		try {
			return sdf.parse(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	public static String getTheTime(Date time,String style)
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
	public static String getTimeStyle(Date d)
	{
		
		if(d==null)
			return "";
		String style="yyyy-MM-dd HH:mm:ss";
		if(d.getHours()+d.getMinutes()+d.getSeconds()==0)
			style="yyyy-MM-dd";
		SimpleDateFormat sdf=new SimpleDateFormat(style);
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
	
	public static Object getProperty(Object object,String propertyname)
	{
		
		if(object==null)
			return null;
		try {
//			if(propertyname.indexOf(".")!=-1)
//				return PropertyUtils.getNestedProperty(object, propertyname);
//			else
//				return PropertyUtils.getSimpleProperty(object, propertyname);
			return PropertyUtils.getProperty(object, propertyname);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static boolean setProperty(Object object,String propertyname,Object value)
	{
		
		if(object==null)
			return false;
		try {
//			if(propertyname.indexOf(".")!=-1)
//				PropertyUtils.setNestedProperty(object, propertyname, value);
//			else
//				PropertyUtils.setSimpleProperty(object, propertyname, value);
			PropertyUtils.setProperty(object, propertyname, value);
			return true;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static Set getProperties(Object object)
	{
		Map map;
		Set set=null;
		try {
			map = PropertyUtils.describe(object);
			set=map.keySet();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return set;
	}
	public static List split(String source,String sp)
	{
		String[] temp=source.split(sp);
		return Arrays.asList(temp);
	}
	/**
	 * @param args
	 * 制作时间：上午09:42:30
	 * 制作人：Administrator
	 * 名称：main
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyDescriptor[] a=PropertyUtils.getPropertyDescriptors(CommonTableView.class);
		for(int i=0,size=a.length;i<size;i++)
		{
			if(Set.class.isAssignableFrom(a[i].getPropertyType()))
				System.out.println(a[i].getName());
		}
		
	}
//	public static String changeRMB(double rmb)
//	{
//		StringBuffer r=new StringBuffer();
//		
//		return r.toString();
//	}
	public static String changeMoney(double smallmoney) {
		String value = String.valueOf(smallmoney);
		if (null == value || "".equals(value.trim()))
			return "零";

		String strCheck, strArr, strFen, strDW, strNum, strBig, strNow;
		double d = 0;
		try {
			d = Double.parseDouble(value);
		} catch (Exception e) {
			return "数据" + value + "非法！";
		}

		strCheck = value + ".";
		int dot = strCheck.indexOf(".");
		if (dot > 12) {
			return "数据" + value + "过大，无法处理！";
		}

		try {
			int i = 0;
			strBig = "";
			strDW = "";
			strNum = "";
			long intFen = (long) (d * 100);
			strFen = String.valueOf(intFen);
			int lenIntFen = strFen.length();
			while (lenIntFen != 0) {
				i++;
				switch (i) {
				case 1:
					strDW = "分";
					break;
				case 2:
					strDW = "角";
					break;
				case 3:
					strDW = "元";
					break;
				case 4:
					strDW = "拾";
					break;
				case 5:
					strDW = "佰";
					break;
				case 6:
					strDW = "仟";
					break;
				case 7:
					strDW = "万";
					break;
				case 8:
					strDW = "拾";
					break;
				case 9:
					strDW = "佰";
					break;
				case 10:
					strDW = "仟";
					break;
				case 11:
					strDW = "亿";
					break;
				case 12:
					strDW = "拾";
					break;
				case 13:
					strDW = "佰";
					break;
				case 14:
					strDW = "仟";
					break;
				}
				switch (strFen.charAt(lenIntFen - 1)) // 选择数字
				{
				case '1':
					strNum = "壹";
					break;
				case '2':
					strNum = "贰";
					break;
				case '3':
					strNum = "叁";
					break;
				case '4':
					strNum = "肆";
					break;
				case '5':
					strNum = "伍";
					break;
				case '6':
					strNum = "陆";
					break;
				case '7':
					strNum = "柒";
					break;
				case '8':
					strNum = "捌";
					break;
				case '9':
					strNum = "玖";
					break;
				case '0':
					strNum = "零";
					break;
				}
				// 处理特殊情况
				strNow = strBig;
				// 分为零时的情况
				if ((i == 1) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "整";
				// 角为零时的情况
				else if ((i == 2) && (strFen.charAt(lenIntFen - 1) == '0')) { // 角分同时为零时的情况
					if (!strBig.equals("整"))
						strBig = "零" + strBig;
				}
				// 元为零的情况
				else if ((i == 3) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "元" + strBig;
				// 拾－仟中一位为零且其前一位（元以上）不为零的情况时补零
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '零')
						&& (strNow.charAt(0) != '元'))
					strBig = "零" + strBig;
				// 拾－仟中一位为零且其前一位（元以上）也为零的情况时跨过
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '零')) {
				}
				// 拾－仟中一位为零且其前一位是元且为零的情况时跨过
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '元')) {
				}
				// 当万为零时必须补上万字
				else if ((i == 7) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "万" + strBig;
				// 拾万－仟万中一位为零且其前一位（万以上）不为零的情况时补零
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '零')
						&& (strNow.charAt(0) != '万'))
					strBig = "零" + strBig;
				// 拾万－仟万中一位为零且其前一位（万以上）也为零的情况时跨过
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '万')) {
				}
				// 拾万－仟万中一位为零且其前一位为万位且为零的情况时跨过
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '零')) {
				}
				// 万位为零且存在仟位和十万以上时，在万仟间补零
				else if ((i < 11) && (i > 8)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '万')
						&& (strNow.charAt(2) == '仟'))
					strBig = strNum + strDW + "万零"
							+ strBig.substring(1, strBig.length());
				// 单独处理亿位
				else if (i == 11) {
					// 亿位为零且万全为零存在仟位时，去掉万补为零
					if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '万')
							&& (strNow.charAt(2) == '仟'))
						strBig = "亿" + "零"
								+ strBig.substring(1, strBig.length());
					// 亿位为零且万全为零不存在仟位时，去掉万
					else if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '万')
							&& (strNow.charAt(2) != '仟'))
						strBig = "亿" + strBig.substring(1, strBig.length());
					// 亿位不为零且万全为零存在仟位时，去掉万补为零
					else if ((strNow.charAt(0) == '万')
							&& (strNow.charAt(2) == '仟'))
						strBig = strNum + strDW + "零"
								+ strBig.substring(1, strBig.length());
					// 亿位不为零且万全为零不存在仟位时，去掉万
					else if ((strNow.charAt(0) == '万')
							&& (strNow.charAt(2) != '仟'))
						strBig = strNum + strDW
								+ strBig.substring(1, strBig.length());
					// 其他正常情况
					else
						strBig = strNum + strDW + strBig;
				}
				// 拾亿－仟亿中一位为零且其前一位（亿以上）不为零的情况时补零
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '零')
						&& (strNow.charAt(0) != '亿'))
					strBig = "零" + strBig;
				// 拾亿－仟亿中一位为零且其前一位（亿以上）也为零的情况时跨过
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '亿')) {
				}
				// 拾亿－仟亿中一位为零且其前一位为亿位且为零的情况时跨过
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '零')) {
				}
				// 亿位为零且不存在仟万位和十亿以上时去掉上次写入的零
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '零')
						&& (strNow.charAt(1) == '亿')
						&& (strNow.charAt(3) != '仟'))
					strBig = strNum + strDW
							+ strBig.substring(1, strBig.length());
				// 亿位为零且存在仟万位和十亿以上时，在亿仟万间补零
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '零')
						&& (strNow.charAt(1) == '亿')
						&& (strNow.charAt(3) == '仟'))
					strBig = strNum + strDW + "亿零"
							+ strBig.substring(2, strBig.length());
				else
					strBig = strNum + strDW + strBig;
				strFen = strFen.substring(0, lenIntFen - 1);
				lenIntFen--;
			}
			return strBig;
		} catch (Exception e) {
			return "";
		}
	}
	public static String changeHtml(String html)
	{
		if(StringUtils.isBlank(html))
			return html;
		return html.replaceAll("<","&lt;")
		.replaceAll(">","&gt;")
		.replaceAll("\"","&quot;")
		.replaceAll(" ","&nbsp;");
	}
	public static String abbreviate(String s,int i)
	{
		if(i<=0)
			return s;
		else
			return StringUtils.abbreviate(s,i);
	}
}
