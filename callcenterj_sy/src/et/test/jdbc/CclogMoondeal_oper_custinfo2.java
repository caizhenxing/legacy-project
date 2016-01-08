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
	 * ��CRM_SUNTEK_PHONEBOOK
	 * ԭʼ��¼ 
	 * ��Ҫ�ٴε����¼  
	 * 
	 * ��oper_custinfo
	 * ��ǰ�������ݼ�¼ 
	 * ���������ݼ�¼ 
	 * �������ݼ�¼ 
	 * ���������ݼ�¼��ʱ��
	 * ���������ɹ�
	 * 
	 * ��һ�ε���������: 0
	   �ڶ��ε���������:168
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

		String sql2 = "select * from CRM_SUNTEK_PHONEBOOK order by CSP_REGTIME asc";
		ResultSet rs2 = stmt2.executeQuery(sql2);

		int j=0;
		int i=0;
		int t=0;
		while (rs2.next()) {
			String phone = rs2.getObject(1).toString().trim();//�绰��
			String name = rs2.getObject(2).toString().trim();//������
			String address = rs2.getObject(3).toString().trim();//��������ϵ��ַ--����
			String typecode = rs2.getObject(4).toString().trim();//���
			String regtime = rs2.getObject(8).toString().trim();//Ӧ��ʱ��
			String agentid = rs2.getObject(9).toString().trim();//Ӧ����ϯid
			
			
			if(phone!=null&&!"".equals(phone)&&name!=null&&!"".equals(name)){
				if(!"ý��".equals(typecode)&&!"��ҵ".equals(typecode)) typecode = "����";
				String custType = "";
				if("ý��".equals(typecode)) custType = "SYS_TREE_0000002105";
				if("��ҵ".equals(typecode)) custType = "SYS_TREE_0000002104";
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
						String id = phone+"_"+ dateTmp+"_"+agentid;//ʱ����λ���ظ���
						String sql3 ="insert into oper_custinfo(cust_id,cust_name,cust_addr,cust_tel_home,cust_tel_work,cust_tel_mob,"+
						"dict_cust_voc,cust_rid,addtime,dict_cust_type) values('opc_pb_"+id+"','"+name+"','"+address+"','"+phone+"','"+phone+"','"+phone
						+"','"+typecode+"','"+agentid+"','"+regtime+"','"+custType+"')";
						int f=stmt3.executeUpdate(sql3);
						if(f>0) {
							t++;
							System.out.println("CRM_SUNTEK_PHONEBOOK��"+t+"������oper_custinfo�ɹ�");
							System.out.println("sql3: "+sql3);
						}
						
					}
				}
			}
		}
		System.out.println("��һ�ε���������: "+j);
		System.out.println("�ڶ��ε���������:"+t);
	}
}
