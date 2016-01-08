package et.bo.callcenter.dbPoll.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import excellence.common.util.Constants;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * 
 * @author chen gang
 *
 */
public class PollDBHelp extends MyQueryImpl {
	/**
	 * 取base_tree表中记录数据库表easy_cdr_trk记录数
	 * @return
	 */
	public MyQuery getDBCount() {
		MyQuery mq = new MyQueryImpl();
		StringBuffer hql = new StringBuffer();
		hql.append("select bt from BaseTree bt where bt.id = bt.id");
		hql.append(" and bt.type = 'dbConstant'");
		mq.setHql(hql.toString());
		
		return mq;
	}
	
	/**
	 * 获得已经阅读过的记录对象
	 * @param crs
	 * @return
	 */
	public MyQuery getAlreadyReadRecord(String crs) {
		MyQuery mq = new MyQueryImpl();
		StringBuffer hql = new StringBuffer();
		hql.append("select ec from EasyCdrTrkCopy ec where ec.crs = ec.crs");
		hql.append(" and ec.crs = '"+ crs +"'");
		mq.setHql(hql.toString());
		
		return mq;
	}
	
	/**
	 * 返回数据库连接
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		String driver = "net.sourceforge.jtds.jdbc.Driver";
//		String url = "jdbc:oracle:thin:@10.113.1.190:1521:orcl";
//		String url = "jdbc:jtds:sqlserver://192.168.1.103:1433/callcenterj_sy;tds=8.0;lastupdatecount=true";
		String url = Constants.getProperty("PollDBURL");
		try {
			Class.forName(driver).newInstance();
			//System.out.println("加载成功.....");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			conn = DriverManager.getConnection(url, Constants.getProperty("PollDBUSERNAME"),
					Constants.getProperty("PollDBPASSWORD"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Connection conn2 = null;
		Statement st2 = null;
		Connection conn3 = null;
		Statement st3 = null;
		
		String rb = "";
		String tb = "";
		
		conn = getConnection();
		conn2 = getConnection();
		conn3 = getConnection();
		
		try {
			st = conn.createStatement();
			st2 = conn2.createStatement();
			st3 = conn3.createStatement();
			String sql = "select * from cc_talk where ring_begintime > '2008-06-28' order by ring_begintime desc";
			rs = st.executeQuery(sql);
			while(rs.next()) {
				String talk_id = rs.getString(1);
				String main_id = rs.getString(2);
				String ring_begintime = rs.getString(3);
				String touch_begintime = rs.getString(4);
				if(rb.equals(ring_begintime) && tb.equals(touch_begintime)){
					String deletesql = "delete from cc_talk where id = '"+ talk_id +"'";
					st2.execute(deletesql);
					String deletemain = "delete from cc_main where id = '"+ main_id +"'";
					st3.execute(deletemain);
				} else{
					rb = ring_begintime;
					tb = touch_begintime;
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
				if(st2 != null)
					st2.close();
				if(st3 != null)
					st3.close();
				if(conn != null)
					conn.close();
				if(conn2 != null)
					conn2.close();
				if(conn3 != null)
					conn3.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
