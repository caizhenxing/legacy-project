/*
 * 创建日期 2004-12-22
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
public class testMetaDB {

	/**
	 * 
	 */
	public testMetaDB() {
		super();
		// TODO 自动生成构造函数存根
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
			ResultSet rs=stmt.executeQuery("select * from jcsj_data");
			ResultSetMetaData rmt=rs.getMetaData();
			DatabaseMetaData dmd=conn.getMetaData();
			ResultSet m=dmd.getPrimaryKeys(null,null,"JBSJ_JZQSZ");
			ResultSet drs=dmd.getCrossReference(null,null,"JBSJ_JZQSZ",null,null,null);//.getExportedKeys(null,null,"jbsj_dbsz");
			ResultSet p=dmd.getProcedures(null,null,null);
			while(p.next())
			for(int i=1;i<8;i++)
			{
			
				String pk = p.getString(i);
				System.out.println(Integer.toString(i)+" 00:" + pk);
				
		
			}
//			for(int i=1;i<=rmt.getColumnCount();i++)
//			{
//				System.out.println("column "+Integer.toString(i)+": "+rmt.getColumnName(i)+"  "+rmt.getColumnType(i)+" "+rmt.getColumnTypeName(i)+" "+rmt.getColumnDisplaySize(i)+" "+rmt.getColumnClassName(i));
//			}
			conn.commit();
			stmt.close();
			conn.close();
		}
		catch (java.lang.ClassNotFoundException e) {
			System.err.println("DBconn():not found---" + e.toString());
			//new jc.basic.JCLog().write("DBconn():not found---" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("DBconn(): " + e.toString());
			//new jc.basic.JCLog().write("DBconn(): " + e.getMessage());
		} catch (Exception e) {
			//new jc.basic.JCLog().write("数据库连结出错"+e.toString());
		}
		
	}
	public static void main(String[] args) {
		testMetaDB tmd=new testMetaDB();
		tmd.insertdb();
	}
}
