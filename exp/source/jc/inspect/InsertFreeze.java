/*
 * 创建日期 2004-12-29
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package jc.inspect;

import java.util.*;
import java.sql.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class InsertFreeze {
	private String jzqbh;
	private String xqbh;
	private Vector sql=new Vector();

	public static void main(String[] args) {
		InsertFreeze ifr=new InsertFreeze();
		ifr.setJzqbh("345");
		ifr.setXqbh("ewr");
		Vector v=new Vector();
		dl d=new dl("2004-08-02 23:32:12","23423","23","324","34","4234","32432");
		v.add(d);
		//ifr.sql(v);
		ifr.insertDB(v);
	}
	/**
	 * @param string
	 */
	public void setJzqbh(String string) {
		jzqbh = string;
	}

	/**
	 * @param string
	 */
	public void setXqbh(String string) {
		xqbh = string;
	}
	public boolean insertDB(Vector v) {
		this.sql(v);

		//
		String ip = "10.5.31.108";
		String com = "1521";
		String username = "jc";
		String password = "jc";
		String sid = "orcl";

		try {
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
			Iterator it = sql.iterator();
			while (it.hasNext())
				stmt.addBatch((String) it.next());
			stmt.executeBatch();

			conn.commit();
			stmt.close();
			conn.close();
			return true;
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("DBconn():not found---" + e.getMessage());
			return false;
			//new jc.basic.JCLog().write("DBconn():not found---" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("DBconn(): " + e.getMessage());
			return false;
			//new jc.basic.JCLog().write("DBconn(): " + e.getMessage());
		} catch (Exception e) {
			return false;
			//new jc.basic.JCLog().write("数据库连结出错"+e.toString());
		}
	}
	public void sql(Vector v) {
		Iterator i = v.iterator();
		while (i.hasNext()) {
			dl d = (dl) i.next();
			StringBuffer sql = new StringBuffer();
			StringBuffer sql1 = new StringBuffer();
			sql.append("insert into temp_data values(");
			sql.append(
				"to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')||JCSJDATA.nextval,");
			sql.append("'" + d.getMeterCode() + "',");
			sql.append("'',");
			sql.append("'" + this.xqbh + "',");
			sql.append("'" + this.jzqbh + "',");
			sql.append("to_date('" + d.getSj() + "','yyyy-mm-dd hh24:mi:ss'),");
			sql.append("'" + d.getFdl() + "',");
			sql.append("'" + d.getGdl() + "',");
			sql.append("'" + d.getJdl() + "',");
			sql.append("'" + d.getPdl() + "',");
			sql.append("'" + d.getZdl() + "',");
			sql.append("'',");
			sql.append("'0',");
			sql.append("'0')");
			//
			sql1.append(
				"Update Temp_Data Set yhbh=(Select yhbh From Jbsj_Dbsz Where dbbh='"
					+ d.getMeterCode()
					+ "' and jzqbh='"
					+ this.jzqbh
					+ "') Where dbbh='"
					+ d.getMeterCode()
					+ "' and jzqbh='"
					+ this.jzqbh
					+ "'");
					System.out.println(sql);
			System.out.println(sql1);
			String s=new String(sql);
			this.sql.add(s);
			this.sql.add(new String(sql1));
		}
	}
}
class dl {
	private String sj; //时间    类似 yyyy-mm-dd hh:mm:ss
	private String meterCode; //电表号
	private String zdl; //总电量（千瓦时）
	private String fdl; //峰电量（千瓦时）
	private String gdl; //谷电量（千瓦时）
	private String jdl; //肩电量（千瓦时）
	private String pdl; //平电量（千瓦时）
	public dl(
		String sj,
		String meterCode,
		String zdl,
		String fdl,
		String gdl,
		String jdl,
		String pdl) {
		this.sj = sj;
		this.meterCode = meterCode;
		this.zdl = zdl;
		this.fdl = fdl;
		this.gdl = gdl;
		this.jdl = jdl;
		this.pdl = pdl;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getSj() {
		return sj;
	}
	public void setZdl(String zdl) {
		this.zdl = zdl;
	}
	public String getZdl() {
		return zdl;
	}
	public void setFdl(String fdl) {
		this.fdl = fdl;
	}
	public String getFdl() {
		return fdl;
	}
	public void setGdl(String gdl) {
		this.gdl = gdl;
	}
	public String getGdl() {
		return gdl;
	}
	public void setJdl(String jdl) {
		this.jdl = jdl;
	}
	public String getJdl() {
		return jdl;
	}
	public void setPdl(String pdl) {
		this.pdl = pdl;
	}
	public String getPdl() {
		return pdl;
	}
	public void setMeterCode(String meterCode) {
		this.meterCode = meterCode;
	}
	public String getMeterCode() {
		return meterCode;
	}
}
