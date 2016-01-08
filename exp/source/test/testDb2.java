/*
 * 创建日期 2005-4-12
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;

import java.sql.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class testDb2 {
	Connection conn=null;
	Statement sm=null;
	private static String ip="10.5.31.133";
	private static String com="6789";//"6789";50000
	private static String username="administrator";
	private static String password="222141";
	private static String sid="toolsdb";
	public void connectDb()
	{
		try
		{
		
		Class.forName("COM.ibm.db2.jdbc.net.DB2Driver");//com.ibm.db2.jcc.DB2Driver
		//String dbServer=(new jc.basic.basicArg()).getDbServer();
		conn = DriverManager.getConnection("jdbc:db2://"+ip+":"+com+"/"+sid,username,password);
		sm=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		//sm=conn.createStatement();
		DatabaseMetaData dmd=conn.getMetaData();
		System.out.println(dmd.getDatabaseProductName().substring(0,3));
		System.out.println("22222"+conn.isClosed());
		conn.close();
		}catch(SQLException se)
		{
			se.printStackTrace();
		}catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
	}
	public static void main(String[] args) {
		testDb2 db=new testDb2();
		db.connectDb();
	}
}
