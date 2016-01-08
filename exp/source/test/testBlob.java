/*
 * 创建日期 2004-12-17
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;

import java.sql.*;
import java.io.*;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class testBlob {
	Connection conn=null;
	Statement sm=null;
	private static String ip="10.5.31.108";
	private static String com="1521";
	private static String username="jcpt";
	private static String password="jcpt";
	private static String sid="orcl";
	public testBlob()
	{
		try
		{
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//String dbServer=(new jc.basic.basicArg()).getDbServer();
		conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":"+com+":"+sid,username,password);
		sm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}catch(SQLException se)
		{
			
		}catch(ClassNotFoundException cnfe)
		{
			
		}
	}
	
	public static void main(String[] args) {
		try
		{
		
		
		File f=new File("e:\\15.wma");
		BufferedInputStream bs=new BufferedInputStream(new FileInputStream(f));
		byte[] bl=new byte[bs.available()];
		bs.read(bl);
		bs.close();
		//System.out.println(bl[10]);
		testBlob tb=new testBlob();
		ResultSet rs=tb.sm.executeQuery("select b from testlob where key='1' for update");
		Blob b;
		if(rs.next())
		{
		
		b=rs.getBlob(1);
//		OutputStream os=b.setBinaryStream(0);
//		os.write(bl,0,bl.length);
//		os.close();
		//b.setString(0,new String(bl));
		
		InputStream is=b.getBinaryStream();
		//String s=rs.getString(1);
		byte i[]=b.getBytes(0,6);
		is.read(i);
		is.close();
		System.out.println(i.length);
		//rs.updateString(1,"222");
		//rs.updateRow();
		
		}
		
		
		rs.close();
		tb.sm.close();
		tb.conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{

		}
	}
}
