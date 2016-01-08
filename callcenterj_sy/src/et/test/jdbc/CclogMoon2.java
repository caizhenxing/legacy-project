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
 * 插入2008-7至2008-11数据
 * @author dengwei
 * 
 */
public class CclogMoon2 {

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
	 * 第一次导数据条数: 136600
	 * 第一次导数据修改talk表中phone_num条数: 136598
	 * 第二次导数据条数:0
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
		String userName = "callcenterj_sy"; // 默认用户名s
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

		String sql2 = "select * from CRM_SUNTEK_CALLINREC where CSC_DATETIME > convert(datetime,'2008-10-16',21) order by CSC_DATETIME asc";
		ResultSet rs2 = stmt2.executeQuery(sql2);

		int j=0;
		int i=0;
		int t=0;
		while (rs2.next()) {
			String phone = rs2.getObject(1).toString().trim();
			String date = rs2.getObject(2).toString().trim();
			String agent = rs2.getObject(3).toString().trim();
			String time = rs2.getObject(4).toString().trim();
			
			if(phone!=null&&!"".equals(phone)&&date!=null&&!"".equals(date)){
				String sql1 = "select count(*) from cc_main where tel_num='"+phone+"' and ring_begintime='"+date+"'";
				ResultSet rs1 = stmt1.executeQuery(sql1);
				while(rs1.next()){
					String ret=rs1.getObject(1).toString();
					if("1".equals(ret)){
						j++;
					}
					if("0".equals(ret)){
						System.out.println("phone: "+phone);
						System.out.println("date: "+date);
						System.out.println("agentid: "+agent);
						System.out.println("time: "+time);
						System.out.println("***************");
						String dateTmp = date.substring(0,date.indexOf("."));
						System.out.println("dateTmp: "+dateTmp);
						String id = phone+"_"+ dateTmp+"_"+agent;//时间秒位有重复的
						String keepTime = String.valueOf(Integer.parseInt(time)*1000);
						String sql3 ="insert into cc_main(id,post_type,tel_num,ring_begintime,process_keeptime,"+
						"process_endtime,is_delete) values('ccm_"+id+"','TRUNK','"+phone+"','"+date+"','"+
						keepTime+"',"+"dateadd(ms,+"+keepTime+",'"+date+"'),'0')";
						int f=stmt3.executeUpdate(sql3);
						if(f>0) {
							System.out.println("ccm插入成功");
							System.out.println("sql3: "+sql3);
							t++;
						}
						String sql4 = "insert into cc_talk(id,cclog_id,ring_begintime,touch_begintime,touch_endtime,"+
						"touch_keeptime,respondent,respondent_type,is_delete,phone_num) values('cct_"+id+"','ccm_"+id+"','"+
						date+"','"+date+"',dateadd(ms,+"+keepTime+",'"+date+"'),'"+keepTime+"','"+agent+"','AGENT','0','"+phone+"')";
						int f2=stmt3.executeUpdate(sql4);
						if(f2>0) {
							System.out.println("cct插入成功");
							System.out.println("sql4: "+sql4);
						}
					}
				}
			}
		}
		System.out.println("第一次导数据条数: "+j);
		System.out.println("第一次导数据修改talk表中phone_num条数: "+i);
		System.out.println("第二次导数据条数:"+t);
	}
}
