/**
 * 
 */
package et.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import excellence.common.util.time.TimeUtil;

/**
 * 修改第一次导入的11多万数据的phone_num
 * @author dengwei
 * 
 */
public class CclogMoon1 {

	/**
	 * 表CRM_SUNTEK_CALLINREC
	 * 原始记录 137158
	 * 无效记录 558
	 * 有效记录 136600
	 * 
	 * 表cc_main、cc_talk
	 * 以前导过数据记录 110047
	 * 继续导数据记录 26553
	 * 共导数据记录 136600
	 * 继续导数据记录耗时：1小时左右
	 * 导表结果：成功
	 * 
	 */
	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub

		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
		String dbCallcenterURL = "jdbc:sqlserver://192.168.3.229:1433; DatabaseName=callcenterj_sy"; // 连接服务器和数据库callcenterj_sy
		String db16808080URL = "jdbc:sqlserver://192.168.3.229:1433; DatabaseName=16808080"; // 连接服务器和数据库16808080
		String userName = "callcenterj_sy"; // 默认用户名
		String userPwd = "callcenterj_sy"; // 密码

		Connection con1 = null;
		Connection con2 = null;
		Connection con3 = null;

		Class.forName(driverName);
		con1 = DriverManager.getConnection(dbCallcenterURL, userName, userPwd);
		con2 = DriverManager.getConnection(db16808080URL, userName, userPwd);
		con3 = DriverManager.getConnection(dbCallcenterURL, userName, userPwd);

		Statement stmt1 = con1.createStatement();
		Statement stmt2 = con2.createStatement();
		Statement stmt3 = con3.createStatement();

		String sql0 = "select b.id,a.tel_num from cc_main a,cc_talk b where a.id=b.cclog_id and a.tel_num is not null and b.phone_num is null order by b.ring_begintime asc ";
		ResultSet rs0 = stmt1.executeQuery(sql0);

		int j=0;
		int i=0;
		int t=0;
		while (rs0.next()) {
			String talkid = rs0.getObject(1).toString().trim();
			String phone = rs0.getObject(2).toString().trim();
			
			if(phone!=null&&!"".equals(phone)&&talkid!=null&&!"".equals(talkid)){
				String sql5 ="update cc_talk set phone_num='"+phone+"' where id='"+talkid+"'";
				int f3=stmt3.executeUpdate(sql5);
				if(f3>0){
					System.out.println("修改talk表中phone_num成功");
					System.out.println("sql5: "+sql5);
					i++;
				}
			}
		}
		System.out.println("第一次导数据条数: "+j);
		System.out.println("第一次导数据修改talk表中phone_num条数: "+i);
		System.out.println("第二次导数据条数:"+t);
	}
	/**
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.format();
	 */
}
