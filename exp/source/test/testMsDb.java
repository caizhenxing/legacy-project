/*
 * 创建日期 2005-4-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;

import java.sql.*;
import java.util.*;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class testMsDb {
	List sql=new Vector();
	public void insertdb(Vector dbbh)
		{
			 String ip="10.5.31.191";
			 String com="1433";
			 String username="jcpt";
			 String password="jcpt";
			 String sid="jcws";
			 Properties d=new Properties();
			 d.setProperty("user",username);
			 d.setProperty("DatabaseName",sid);
			 d.setProperty("password",password);
			try 
			{
				Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");

//				Iterator dbb=dbbh.iterator();
//						while(dbb.hasNext())
//						{
							Connection conn =
								DriverManager.getConnection(
									"jdbc:microsoft:sqlserver://" + ip +":"+com,
									d);
							Statement stmt =
								conn.createStatement(
									ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
									
							//String dbbha=(String)dbb.next();
				ResultSet rs=stmt.executeQuery("select max(ontime),just_have_total,mno from t_have_energy_h  group by mno;");
				while(rs.next())
				
					//sql.add("insert into jbsj_dbsz(xqbh,csbh,jzqbh,dbbh) values('20050411140040-10.5.31.191','20030509094863-10.5.31.108','"+rs.getString(1)+"','"+rs.getString(2)+"')");
					sql.add("insert into temp_data(lsh,dbbh,jcsj,dbssz) values(jcsjdata.nextval,'"+rs.getString(3)+"',sysdate,'"+rs.getString(2)+"')");
					//System.out.println(rs.getString(1));
					//System.out.println(rs.getString(2));
					//System.out.println("-----------------------------");
						rs.close();
						stmt.close();
						conn.close();
						//}
//				ResultSetMetaData rmt=rs.getMetaData();
//				DatabaseMetaData dmd=conn.getMetaData();
//				ResultSet m=dmd.getPrimaryKeys(null,null,"JBSJ_JZQSZ");
//				ResultSet drs=dmd.getCrossReference(null,null,"JBSJ_JZQSZ",null,null,null);//.getExportedKeys(null,null,"jbsj_dbsz");
//				while(drs.next())
//				for(int i=1;i<15;i++)
//				{
//			
//					String pk = drs.getString(i);
//					System.out.println(Integer.toString(i)+" pk:" + pk);
//				
//		
//				}
//				for(int i=1;i<=rmt.getColumnCount();i++)
//				{
//					System.out.println("column "+Integer.toString(i)+": "+rmt.getColumnName(i)+"  "+rmt.getColumnType(i)+" "+rmt.getColumnTypeName(i)+" "+rmt.getColumnDisplaySize(i)+" "+rmt.getColumnClassName(i));
//				}
				//conn.commit();
				
				
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
		long begin=System.currentTimeMillis();
		testMsDb md=new testMsDb();
		Vector dbbh=new Vector();
		//md.insertdb();
		try
		{
		OracleDataSource ods = new OracleDataSource();
		HashMap hm=new HashMap();
		ods.setDriverType("thin");
		ods.setServerName("10.5.31.191");
		ods.setNetworkProtocol("tcp");
		ods.setDatabaseName("orcl");
		ods.setPortNumber(1521);
		ods.setUser("jc");
		ods.setPassword("forecast_jcpt");
		Connection conn = ods.getConnection();
		Statement sm=conn.createStatement(
		ResultSet.TYPE_SCROLL_INSENSITIVE,
		ResultSet.CONCUR_UPDATABLE);
		ResultSet rs=sm.executeQuery("select dbbh from jbsj_dbsz where xqbh='20050411140040-10.5.31.191' or xqbh='20050411140058-10.5.31.191'");
		//Iterator i=md.sql.iterator();
		//conn.setAutoCommit(false);
		while(rs.next())
		{
			//sm.addBatch((String)i.next());
			dbbh.add(rs.getString(1));
		}
		//sm.executeBatch();
		//conn.commit();
		rs.close();
		System.out.println("dbbh :"+dbbh.size());
		md.insertdb(dbbh);
		System.out.println("数据：" +md.sql.size());
		Iterator i=md.sql.iterator();
		conn.setAutoCommit(false);
		while(i.hasNext())
		{
			String s=(String)i.next();
			sm.addBatch(s);
			System.out.println(s);
			//dbbh.add(rs.getString(1));
		}
		sm.executeBatch();
		conn.commit();
		sm.close();
		conn.close();
		}catch(SQLException se)
		{
			se.printStackTrace();
		}finally
		{
			System.out.print("time is: ");
			System.out.println(begin-System.currentTimeMillis());
		}
	}
}
