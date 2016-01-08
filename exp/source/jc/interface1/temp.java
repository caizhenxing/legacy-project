/*
 * 创建日期 2005-2-22
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

package jc.interface1;

import java.util.Iterator;
import java.util.Vector;
import java.util.*;
import java.io.*;
import java.sql.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class temp {

	/**
	 * 
	 */
	private Vector sql;
	//private Vector file=new Vector();
	public temp() {
		super();
		// TODO 自动生成构造函数存根
	}
	private Vector read(BufferedReader br)
	{
		Vector v=new Vector();
		String temp;
		try
		{
			while(true)
			{
				temp=(String)br.readLine();
				if(temp==null) break;
				v.add(temp);
			}
			
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}finally
		{
			return v;
		}
	}
	public void analyseFile(String filepath,String jzqbh,String xqbh)
	{
		sql=new Vector();
		Vector v;
		char[] token={13};
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(new File(filepath)));
			//StreamToken st=new StreamToken(br);
			//char[] temp=new char[];
			//br.read(temp);
			//StringToken st=new StringToken(new String(temp),"\n\r");
			
			v=this.read(br);
			Iterator it=v.iterator();
			while(it.hasNext())
			{
				String s=it.next().toString();
				metercode1 mc=new metercode1();
				mc.read(s);
				//mc.setJzqbh(jzqbh);
				//mc.setXqbh(xqbh);
				//sql.add(mc.writeSqlCONSUMER());
				sql.add(mc.writeSqlDBSZ());
				//System.out.println("string zzdlz: "+mc.getZzdlz());
			}
			
		}catch(IOException fe)
		{
			System.out.println("meter import error: "+fe.toString());
		}
	}
	public Vector returnSql()
	{
		//if(sql.isEmpty())
		//return null;
		return sql;
	}
	public void insertdb()
	{
		 String ip="10.5.31.108";
		 String com="1521";
		 String username="fxgl";
		 String password="fxgl";
		 String sid="orcl";
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn =
				DriverManager.getConnection(
					"jdbc:oracle:thin:@" + ip + ":" + com + ":" + sid,
					username,
					password);
			Statement stmt =
				conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			Iterator it=sql.iterator();
			while(it.hasNext())
			stmt.addBatch(it.next().toString());
			stmt.executeBatch();
			
			conn.commit();
			stmt.close();
			conn.close();
		}
		catch (java.lang.ClassNotFoundException e) {
			System.err.println("DBconn():not found---" + e.getMessage());
			//new jc.basic.JCLog().write("DBconn():not found---" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("DBconn(): " + e.getMessage());
			//new jc.basic.JCLog().write("DBconn(): " + e.getMessage());
		} catch (Exception e) {
			//new jc.basic.JCLog().write("数据库连结出错"+e.toString());
		}
		
	}
	public static void main(String[] args) 
	{	
		temp mi=new temp();
		mi.analyseFile("f:\\ww.txt","","");
		Vector v=mi.returnSql();
		Iterator it=v.iterator();
		while(it.hasNext())
		System.out.println("sql: "+it.next().toString());
		mi.insertdb();
		
	}
}
final class metercode1
{
	private String c1;  
	private String c2;
	private String c3;
	private String c4;  
	private String c5;   
	private String c6;    
	private String c7;   
	private String c8; 
	private String c9;  
	private String c10;   
	private String c11; 
	private String c12; 
	private String c13;  
	private String c14; 
	private String c15; 
	private String c16; 

	public String writeSqlDBSZ()
	{
		StringBuffer sb=new StringBuffer();
		String tableName="fxgl.risk_info_temp";
		sb.append("insert into ");
		sb.append(tableName);
		sb.append(" values(");
		sb.append(this.c1);
		sb.append(",");
		sb.append(this.c2);
		sb.append(",");
		sb.append(this.c3);
		sb.append(",");
		sb.append(this.c4);
		sb.append(",");
		sb.append(this.c5);
		sb.append(",");
		sb.append(this.c6);
		sb.append(",");
		sb.append(this.c7);
		sb.append(",");
		sb.append(this.c8);
		sb.append(",");
		sb.append(this.c9);
		sb.append(",");
		sb.append(this.c10);
		sb.append(",");
		sb.append(this.c11);
		sb.append(",");
		sb.append(this.c12);
		sb.append(",");
		sb.append(this.c13);
		sb.append(",");
		sb.append(this.c14);
		sb.append(",");
		sb.append(this.c15);
		sb.append(",");
		sb.append(this.c16);


		sb.append(")");
		return new String(sb);
	}
//	public String writeSqlCONSUMER()
//	{
//		StringBuffer sb=new StringBuffer();
//		String tableName="JC.JBSJ_CONSUMER";
//		sb.append("insert into ");
//		sb.append(tableName);
//		sb.append(" (yhbm) ");
//		sb.append("values(");
//		sb.append(this.yhbh);
//		sb.append(")");
//		return new String(sb);
//	}
//	public void setXqbh(String xqbh)
//	{
//		this.xqbh="'"+xqbh+"'";
//	}
//	public void setJzqbh(String jzqbh)
//	{
//		this.jzqbh="'"+jzqbh+"'";
//	}
	public void read(String record)
	{
		StringTokenizer st=new StringTokenizer(record,"\t");
		if(st.hasMoreTokens())
		c1="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c2="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c3="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c4="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c5="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c6="'"+st.nextElement()+"'";    
		if(st.hasMoreTokens())
		c7 ="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())  
		c8 ="'"+st.nextElement()+"'";
		if(st.hasMoreTokens()) 
		c9  ="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c10  ="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c11 ="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c12 ="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c13 ="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c14 ="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c15 ="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		c16 ="'"+st.nextElement()+"'";
//		if(st.hasMoreTokens())
//		zzdlg =(String)st.nextElement();
//		if(st.hasMoreTokens())
//		zzdlj =(String)st.nextElement();
//		if(st.hasMoreTokens())
//		zzdlp =(String)st.nextElement();
//		if(st.hasMoreTokens())
//		zzdlz =(String)st.nextElement();
		
		
	}
//	public String getZzdlz()
//	{
//		return this.zzdlz;
//	}
}

