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
 * �޸ĵ�һ�ε����11�������ݵ�phone_num
 * @author dengwei
 * 
 */
public class CclogMoon1 {

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
		String userName = "callcenterj_sy"; // Ĭ���û���
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
					System.out.println("�޸�talk����phone_num�ɹ�");
					System.out.println("sql5: "+sql5);
					i++;
				}
			}
		}
		System.out.println("��һ�ε���������: "+j);
		System.out.println("��һ�ε������޸�talk����phone_num����: "+i);
		System.out.println("�ڶ��ε���������:"+t);
	}
	/**
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.format();
	 */
}
