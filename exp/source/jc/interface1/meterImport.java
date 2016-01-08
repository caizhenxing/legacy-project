/*
 * 创建日期 2004-12-20
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
public class meterImport {

	/**
	 * 
	 */
	private Vector sql;
	//private Vector file=new Vector();
	public meterImport() {
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
				metercode mc=new metercode();
				mc.read(s);
				mc.setJzqbh(jzqbh);
				mc.setXqbh(xqbh);
				sql.add(mc.writeSqlCONSUMER());
				sql.add(mc.writeSqlDBSZ());
				System.out.println("string zzdlz: "+mc.getZzdlz());
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
		 String username="jc";
		 String password="jc";
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
		meterImport mi=new meterImport();
		mi.analyseFile("e:\\meter.txt","","");
		Vector v=mi.returnSql();
		Iterator it=v.iterator();
		while(it.hasNext())
		System.out.println("sql: "+it.next().toString());
		mi.insertdb();
		
	}
}
final class metercode
{
	private String dbbh;  
	private String yhbh;
	private String xqbh;
	private String jzqbh;  
	private String csbh="null";   
	private String xh="null";    
	private String xw="null";   
	private String bl=""; 
	private String eddy="";  
	private String eddl="";   
	private String dyxs=""; 
	private String dlxs=""; 
	private String csdlf="";  
	private String csdlg=""; 
	private String csdlj=""; 
	private String csdlp=""; 
	private String csdlz=""; 
	private String zzdlf="";  
	private String zzdlg="";  
	private String zzdlj="";  
	private String zzdlp="";  
	private String zzdlz=""; 
	public String writeSqlDBSZ()
	{
		StringBuffer sb=new StringBuffer();
		String tableName="JC.JBSJ_DBSZ";
		sb.append("insert into ");
		sb.append(tableName);
		sb.append(" (DBBH,YHBH,xqbh,jzqbh,CSBH,XH,XW,BL,EDDY ,EDDL ,");
		sb.append("DYXS  ,DLXS  ,CSDLF ,CSDLG ,CSDLJ ,CSDLP,");
		sb.append("CSDLZ ,ZZDLF ,ZZDLG ,ZZDLJ ,ZZDLP ,ZZDLZ) ");
		sb.append("values(");
		sb.append(this.dbbh);
		sb.append(",");
		sb.append(this.yhbh);
		sb.append(",");
		sb.append(this.xqbh);
		sb.append(",");
		sb.append(this.jzqbh);
		sb.append(",");
		sb.append(this.csbh);
		sb.append(",");
		sb.append(this.xh);
		sb.append(",");
		sb.append(this.xw);
		sb.append(",");
		sb.append(this.bl);
		sb.append(",");
		sb.append(this.eddy);
		sb.append(",");
		sb.append(this.eddl);
		sb.append(",");
		sb.append(this.dyxs);
		sb.append(",");
		sb.append(this.dlxs);
		sb.append(",");
		sb.append(this.csdlf);
		sb.append(",");
		sb.append(this.csdlg);
		sb.append(",");
		sb.append(this.csdlj);
		sb.append(",");
		sb.append(this.csdlp);
		sb.append(",");
		sb.append(this.csdlz);
		sb.append(",");
		sb.append(this.zzdlf);
		sb.append(",");
		sb.append(this.zzdlg);
		sb.append(",");
		sb.append(this.zzdlj);
		sb.append(",");
		sb.append(this.zzdlp);
		sb.append(",");
		sb.append(this.zzdlz);
		sb.append(")");
		return new String(sb);
	}
	public String writeSqlCONSUMER()
	{
		StringBuffer sb=new StringBuffer();
		String tableName="JC.JBSJ_CONSUMER";
		sb.append("insert into ");
		sb.append(tableName);
		sb.append(" (yhbm) ");
		sb.append("values(");
		sb.append(this.yhbh);
		sb.append(")");
		return new String(sb);
	}
	public void setXqbh(String xqbh)
	{
		this.xqbh="'"+xqbh+"'";
	}
	public void setJzqbh(String jzqbh)
	{
		this.jzqbh="'"+jzqbh+"'";
	}
	public void read(String record)
	{
		StringTokenizer st=new StringTokenizer(record,"\t");
		if(st.hasMoreTokens())
		dbbh="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		this.yhbh="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		this.csbh="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		this.xh="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		this.xw="'"+st.nextElement()+"'";
		if(st.hasMoreTokens())
		bl=(String)st.nextElement();    
		if(st.hasMoreTokens())
		eddy =(String)st.nextElement();
		if(st.hasMoreTokens())  
		eddl =(String)st.nextElement();
		if(st.hasMoreTokens()) 
		dyxs  =(String)st.nextElement();
		if(st.hasMoreTokens())
		dlxs  =(String)st.nextElement();
		if(st.hasMoreTokens())
		csdlf =(String)st.nextElement();
		if(st.hasMoreTokens())
		csdlg =(String)st.nextElement();
		if(st.hasMoreTokens())
		csdlj =(String)st.nextElement();
		if(st.hasMoreTokens())
		csdlp =(String)st.nextElement();
		if(st.hasMoreTokens())
		csdlz =(String)st.nextElement();
		if(st.hasMoreTokens())
		zzdlf =(String)st.nextElement();
		if(st.hasMoreTokens())
		zzdlg =(String)st.nextElement();
		if(st.hasMoreTokens())
		zzdlj =(String)st.nextElement();
		if(st.hasMoreTokens())
		zzdlp =(String)st.nextElement();
		if(st.hasMoreTokens())
		zzdlz =(String)st.nextElement();
		
		
	}
	public String getZzdlz()
	{
		return this.zzdlz;
	}
}
