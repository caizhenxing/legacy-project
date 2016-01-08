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
 * 
 * @author dengwei
 * 
 */
public class CclogMoondeal_oper_custinfo2 {

	/**
	 * 表CRM_SUNTEK_PHONEBOOK
	 * 原始记录 
	 * 需要再次导入记录  
	 * 
	 * 表oper_custinfo
	 * 以前导过数据记录 
	 * 继续导数据记录 
	 * 共导数据记录 
	 * 继续导数据记录耗时：
	 * 导表结果：成功
	 * 
	 * 第一次导数据条数: 0
	   第二次导数据条数:168
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

		String sql2 = "select * from CRM_SUNTEK_PHONEBOOK order by CSP_REGTIME asc";
		ResultSet rs2 = stmt2.executeQuery(sql2);

		int j=0;
		int i=0;
		int t=0;
		while (rs2.next()) {
			String phone = rs2.getObject(1).toString().trim();//电话号
			String name = rs2.getObject(2).toString().trim();//人名称
			String address = rs2.getObject(3).toString().trim();//呼入人联系地址--工作
			String typecode = rs2.getObject(4).toString().trim();//类别
			String regtime = rs2.getObject(8).toString().trim();//应答时间
			String agentid = rs2.getObject(9).toString().trim();//应答座席id
			
			
			if(phone!=null&&!"".equals(phone)&&name!=null&&!"".equals(name)){
				if(!"媒体".equals(typecode)&&!"企业".equals(typecode)) typecode = "其它";
				String custType = "";
				if("媒体".equals(typecode)) custType = "SYS_TREE_0000002105";
				if("企业".equals(typecode)) custType = "SYS_TREE_0000002104";
				String sql1 = "select count(*) from oper_custinfo where cust_name='"+name+"' and dict_cust_voc='"+typecode+"' and cust_addr='"+address
				+"' and (cust_tel_home='"+phone+"' or cust_tel_work='"+phone+"' or cust_tel_mob='"+phone+"')";
				ResultSet rs1 = stmt1.executeQuery(sql1);
				while(rs1.next()){
					String ret=rs1.getObject(1).toString().trim();
					if("1".equals(ret)){
						j++;
					}
					if("0".equals(ret)){
						String dateTmp = regtime.substring(0,regtime.indexOf("."));
						System.out.println("dateTmp: "+dateTmp);
						String id = phone+"_"+ dateTmp+"_"+agentid;//时间秒位有重复的
						String sql3 ="insert into oper_custinfo(cust_id,cust_name,cust_addr,cust_tel_home,cust_tel_work,cust_tel_mob,"+
						"dict_cust_voc,cust_rid,addtime,dict_cust_type) values('opc_pb_"+id+"','"+name+"','"+address+"','"+phone+"','"+phone+"','"+phone
						+"','"+typecode+"','"+agentid+"','"+regtime+"','"+custType+"')";
						int f=stmt3.executeUpdate(sql3);
						if(f>0) {
							t++;
							System.out.println("CRM_SUNTEK_PHONEBOOK（"+t+"）插入oper_custinfo成功");
							System.out.println("sql3: "+sql3);
						}
						
					}
				}
			}
		}
		System.out.println("第一次导数据条数: "+j);
		System.out.println("第二次导数据条数:"+t);
	}
}
