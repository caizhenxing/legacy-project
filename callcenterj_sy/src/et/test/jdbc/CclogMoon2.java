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
 * ����2008-7��2008-11����
 * @author dengwei
 * 
 */
public class CclogMoon2 {

	/**
	 * ��CRM_SUNTEK_CALLINREC
	 * ԭʼ��¼ 137158
	 * ��Ч��¼ 558
	 * ��Ч��¼ 136600
	 * 
	 * ��cc_main��cc_talk
	 * ��ǰ�������ݼ�¼ 110047
	 * ���������ݼ�¼ 26553
	 * �������ݼ�¼ 136600
	 * ���������ݼ�¼��ʱ��1Сʱ����
	 * ���������ɹ�
	 * 
	 * ��һ�ε���������: 136600
	 * ��һ�ε������޸�talk����phone_num����: 136598
	 * �ڶ��ε���������:0
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

		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
		String dbCallcenterURL = "jdbc:sqlserver://192.168.3.229:1433; DatabaseName=callcenterj_sy"; // ���ӷ����������ݿ�callcenterj_sy
		String db16808080URL = "jdbc:sqlserver://192.168.3.229:1433; DatabaseName=16808080"; // ���ӷ����������ݿ�16808080
		String userName = "callcenterj_sy"; // Ĭ���û���s
		String userPwd = "callcenterj_sy"; // ����

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
						String id = phone+"_"+ dateTmp+"_"+agent;//ʱ����λ���ظ���
						String keepTime = String.valueOf(Integer.parseInt(time)*1000);
						String sql3 ="insert into cc_main(id,post_type,tel_num,ring_begintime,process_keeptime,"+
						"process_endtime,is_delete) values('ccm_"+id+"','TRUNK','"+phone+"','"+date+"','"+
						keepTime+"',"+"dateadd(ms,+"+keepTime+",'"+date+"'),'0')";
						int f=stmt3.executeUpdate(sql3);
						if(f>0) {
							System.out.println("ccm����ɹ�");
							System.out.println("sql3: "+sql3);
							t++;
						}
						String sql4 = "insert into cc_talk(id,cclog_id,ring_begintime,touch_begintime,touch_endtime,"+
						"touch_keeptime,respondent,respondent_type,is_delete,phone_num) values('cct_"+id+"','ccm_"+id+"','"+
						date+"','"+date+"',dateadd(ms,+"+keepTime+",'"+date+"'),'"+keepTime+"','"+agent+"','AGENT','0','"+phone+"')";
						int f2=stmt3.executeUpdate(sql4);
						if(f2>0) {
							System.out.println("cct����ɹ�");
							System.out.println("sql4: "+sql4);
						}
					}
				}
			}
		}
		System.out.println("��һ�ε���������: "+j);
		System.out.println("��һ�ε������޸�talk����phone_num����: "+i);
		System.out.println("�ڶ��ε���������:"+t);
	}
}
