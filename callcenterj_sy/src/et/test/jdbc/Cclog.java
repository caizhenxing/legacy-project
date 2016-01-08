/**
 * 
 */
package et.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author zhangfeng
 * 
 */
public class Cclog {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub

		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
		String dbCallcenterURL = "jdbc:sqlserver://192.168.1.103:1433; DatabaseName=callcenterj_sy"; // 连接服务器和数据库callcenterj_sy
		String db16808080URL = "jdbc:sqlserver://192.168.1.103:1433; DatabaseName=16808080"; // 连接服务器和数据库16808080
		String userName = "sa"; // 默认用户名
		String userPwd = "123"; // 密码

		Connection con1 = null;
		Connection con2 = null;

		Class.forName(driverName);
		con1 = DriverManager.getConnection(dbCallcenterURL, userName, userPwd);
		con2 = DriverManager.getConnection(db16808080URL, userName, userPwd);

		Statement stmt1 = con1.createStatement();
		Statement stmt2 = con2.createStatement();

		String sql1 = "select * from cc_main";
		String sql2 = "select * from CRM_SUNTEK_CALLINREC";

		ResultSet rs1 = stmt1.executeQuery(sql1);
		ResultSet rs2 = stmt2.executeQuery(sql2);

		while (rs1.next()) {
			//System.out.println("rs1 的 " + rs1.getObject(1).toString());
		}

		while (rs2.next()) {
			System.out.println("rs2 的 的 " + rs2.getObject(1).toString());
		}

	}

}
