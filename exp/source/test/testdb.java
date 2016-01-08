/*
 * 创建日期 2004-12-13
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;
import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.*;
import java.util.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class testdb {

	public static void main(String[] args) {
		try{
		
		OracleDataSource ods = new OracleDataSource();
		HashMap hm=new HashMap();
		ods.setDriverType("thin");
		ods.setServerName("10.5.31.108");
		ods.setNetworkProtocol("tcp");
		ods.setDatabaseName("orcl");
		ods.setPortNumber(1521);
		ods.setUser("jc");
		ods.setPassword("jc");
		Connection conn = ods.getConnection();
		Statement sm=conn.createStatement(
		ResultSet.TYPE_SCROLL_INSENSITIVE,
		ResultSet.CONCUR_UPDATABLE);
		ResultSet rs=sm.executeQuery("select dbbh,sybz from jbsj_dbsz where jzqbh='6'");
		while(rs.next())
		{
			hm.put(rs.getString(1),rs.getString(2));
			
			//System.out.println(rs.getString(1)+"--"+rs.getString(2));
		}
		Set s=hm.entrySet();
		Iterator i=s.iterator();
		while(i.hasNext())
		{
			String s1=(String)((Map.Entry)(i.next())).getValue();
			System.out.println(s1);
			////String s2=(String)hm.get(s1);
			//System.out.println(s2);
		}

//		DatabaseMetaData dbmd=conn.getMetaData();
//		String s=dbmd.getDatabaseProductName();
//		String v=dbmd.getDatabaseProductVersion();
//		System.out.println(s);
//		System.out.println(v);
//		if(conn.isClosed()==false)
//		System.out.println("connected");
//		for (int i=0;i<100;i++) {
//
//			Statement sm =
//				conn.createStatement(
//					ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			Statement sm1 =
//				conn.createStatement(
//					ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			Statement sm3 =
//				conn.createStatement(
//					ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			
//			ResultSet rs1 = sm.executeQuery("select * from jbsj_cssz");
//			ResultSet rs2 = sm1.executeQuery("select * from jbsj_dbsz");
//			ResultSet rs3 = sm3.executeQuery("select * from jbsj_jzqsz");
//			rs1.last();
//			rs2.last();
//			rs3.last();
//			System.out.println(rs1.getRow());
//			System.out.println(rs2.getRow());
//			System.out.println(rs3.getRow());
////			rs1.close();
////			rs2.close();
////			rs3.close();
//			
//			sm.close();
//			sm1.close();
//			sm3.close();
//		}
		conn.close();
		}catch(SQLException se)
		{se.printStackTrace();
		}
	}
}
