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
public class CompareMC {
	private Vector dbmc;
	private Vector jzqmc;
	private String jzq;
	private String xq;
	private String sql;
	public CompareMC(String jzq,String xq)
	{
		this.jzq=jzq;
		this.xq=xq;
		dbmc=new Vector();
	}
	
	private void selectDB()
	{
		sql="Select dbbh From Jbsj_Dbsz Where jzqbh='"+this.jzq+"' and xqbh='"+this.xq+"'  Order By dbbh Asc";
		//insert dbmc from database
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
					ResultSet rs=stmt.executeQuery(sql);
					while(rs.next())
					{
						this.dbmc.add(rs.getString(1));
					}
					
					System.out.println(this.dbmc.size());
					conn.commit();
					stmt.close();
					conn.close();
					//return true;
				} catch (java.lang.ClassNotFoundException e) {
					System.err.println("DBconn():not found---" + e.getMessage());
					//return false;
					//new jc.basic.JCLog().write("DBconn():not found---" + e.getMessage());
				} catch (SQLException e) {
					System.err.println("DBconn(): " + e.getMessage());
					//return false;
					//new jc.basic.JCLog().write("DBconn(): " + e.getMessage());
				} catch (Exception e) {
					//return false;
					//new jc.basic.JCLog().write("数据库连结出错"+e.toString());
				}
	}
	public Vector compare()
	{
		this.selectDB();
		Vector v=new Vector();
		Iterator d=dbmc.iterator();
		
		
		while(d.hasNext())
		{
			String mcd=(String)d.next();
			if(this.jzqmc.contains(mcd))
				v.add(new mc(mcd,mcd));
			else
				v.add(new mc(mcd,""));
		}

		Iterator j=jzqmc.iterator();

		while(j.hasNext())
		{
			String mcj=(String)j.next();
			v.add(new mc("",mcj));
			
		}
		
		return v; 
	}
	public static void main(String[] args) {
		//String d$d=new String();
		CompareMC c=new CompareMC("0718","3");
		Vector v=new Vector();
		for(int i=793005;i<794534;i=i+2)
		{
			v.add(new MeterCode(Integer.toString(i)));
		}
		System.out.println(v.size());
		c.setJzqmc(v);
		//c.selectDB();
		System.out.println(v.size());
		//System.out.println(((MeterCode)c.jzqmc.get(c.jzqmc.size()-1)).getMeterCode());
		Vector a=c.compare();
		Iterator i=a.iterator();
		System.out.println(a.size());
		while(i.hasNext())
		{
			mc m=(mc)i.next();
			System.out.println(m.getDbmc()+"---"+m.getJzqmc());
		}
		
	}
	/**
	 * @param vector
	 */
	public void setJzqmc(Vector vector) {
		this.jzqmc=new Vector();
		Iterator it=vector.iterator();
		while(it.hasNext())
		{
			String s=((MeterCode)it.next()).getMeterCode();
			//System.out.println(s);
			jzqmc.add(s);
		}
		
		Collections.sort(this.jzqmc);

	}

}
class MeterCode {

	private String meterCode;
	public MeterCode() {
	}
	public MeterCode(String meterCode) {
		this.meterCode=meterCode;
	}
	public static void main(String[] args) {
		MeterCode meterCode1 = new MeterCode();
	}
	public void setMeterCode(String meterCode) {
		this.meterCode = meterCode;
	}
	public String getMeterCode() {
		return meterCode;
	}
}