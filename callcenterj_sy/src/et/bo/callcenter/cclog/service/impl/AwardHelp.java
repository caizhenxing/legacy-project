package et.bo.callcenter.cclog.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import excellence.common.util.Constants;

public class AwardHelp {

	private String url = Constants.getProperty("AWARDHELPURL");

	private String uid = Constants.getProperty("AWARDHELPUSERNAME");

	private String pwd = Constants.getProperty("AWARDHELPPASSWORD");

	private String driver = "net.sourceforge.jtds.jdbc.Driver";

	private Connection con = null;

	private Statement sta = null;

	private ResultSet rs = null;

	public AwardHelp() {
		openCon();

	}

	private void openCon() {
		try {
			if (con == null) {
				Class.forName(driver);
				con = DriverManager.getConnection(url, uid, pwd);
			}

		} catch (Exception e) {
			//System.out.println(e);
		}
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public ResultSet getRs() {
		return rs;
	}
	
	public ResultSet getScrollRs(String sql) {
		try {
			sta = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = sta.executeQuery(sql);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			return rs;
		}
	}
	
	public ResultSet getRs(String sql) {
		try {
			sta = con.createStatement();
			rs = sta.executeQuery(sql);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			return rs;
		}
	}

	public int insertInfo(String sql) {
		int i = 0;
		try {
			sta = con.createStatement();
			i = sta.executeUpdate(sql);

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			return i;
		}
	}

	public ArrayList getList(String sql) {
		ArrayList list = new ArrayList();
		ArrayList row;
		try {
			sta = con.createStatement();
			rs = sta.executeQuery(sql);
			while (rs.next()) {
				row = new ArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.add(rs.getString(i));
				}
				list.add(row);
			}
			sta.close();
			rs.close();
		} catch (Exception e) {
			System.out.print(e);
		} finally {

			return list;
		}
	}

	public void close() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
