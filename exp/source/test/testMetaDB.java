/*
 * �������� 2004-12-22
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package test;

import java.sql.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class testMetaDB {

	/**
	 * 
	 */
	public testMetaDB() {
		super();
		// TODO �Զ����ɹ��캯�����
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
			//new jc.basic.JCLog().write("���ݿ��������"+e.toString());
		}
		
	}
	public static void main(String[] args) {
		testMetaDB tmd=new testMetaDB();
		tmd.insertdb();
	}
}
