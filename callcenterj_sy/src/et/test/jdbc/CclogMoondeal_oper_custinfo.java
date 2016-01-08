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
public class CclogMoondeal_oper_custinfo {

	/**
	 * ��CRM_SUNTEK_CUSTOMER
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
	 * ��һ�ε���������: 31019
	   �ڶ��ε���������:7143
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

		String sql2 = "select * from CRM_SUNTEK_CUSTOMER";
		ResultSet rs2 = stmt2.executeQuery(sql2);

		int j=0;
		int i=0;
		int t=0;
		while (rs2.next()) {
			String phone = rs2.getObject(1).toString().trim();//����绰��
			String name = rs2.getObject(2).toString().trim();//�����û�����
			String typecode = rs2.getObject(3).toString().trim();//�û����
			String address = rs2.getObject(4).toString().trim();//��������ϵ��ַ
			String agentid = rs2.getObject(8).toString().trim();//Ӧ����ϯid
			String regtime = rs2.getObject(9).toString().trim();//Ӧ��ʱ��
			
			if(phone!=null&&!"".equals(phone)&&name!=null&&!"".equals(name)){
				if("1".equals(typecode)) typecode = "��ͨũ��";
				if("2".equals(typecode)) typecode = "��ֲ��";
				if("3".equals(typecode)) typecode = "��ֳ��";
				if("4".equals(typecode)) typecode = "�ӹ���";
				if("5".equals(typecode)) typecode = "ũ�徭����";
				if("6".equals(typecode)) typecode = "����";
				String sql1 = "select count(*) from oper_custinfo where cust_name='"+name+"' and dict_cust_voc='"+typecode+"' and cust_addr='"+address
				+"' and (cust_tel_home='"+phone+"' or cust_tel_work='"+phone+"' or cust_tel_mob='"+phone+"')";
				ResultSet rs1 = stmt1.executeQuery(sql1);
				while(rs1.next()){
					String ret=rs1.getObject(1).toString().trim();
					if("1".equals(ret)){
						j++;
					}
					if("0".equals(ret)){
						String dateTmp = regtime;//.substring(0,regtime.indexOf("."));
//						System.out.println("dateTmp: "+dateTmp);
						String id = phone+"_"+ dateTmp;//ʱ����λ���ظ���
						String sql3 ="insert into oper_custinfo(cust_id,cust_name,cust_addr,cust_tel_home,cust_tel_work,cust_tel_mob,"+
						"dict_cust_voc,cust_rid,addtime) values('opc_"+id+"','"+name+"','"+address+"','"+phone+"','"+phone+"','"+phone
						+"','"+typecode+"','"+agentid+"','"+regtime+"')";
						int f=stmt3.executeUpdate(sql3);
						if(f>0) {
							t++;
							System.out.println("CRM_SUNTEK_CUSTOMER���ݣ�"+t+"������oper_custinfo�ɹ�");
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
