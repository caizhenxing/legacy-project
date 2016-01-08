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
public class CclogMoondeal_ccquestion {

	/**
	 * ��CRM_SUNTEK_TRANSACTION
	 * ԭʼ��¼ 115187
	 * ��Ҫ�ٴε����¼ 27159 ��2008-06-20�Ժ��¼��
	 * 
	 * ��oper_question
	 * ��ǰ�������ݼ�¼ 
	 * ���������ݼ�¼ 27159 ��2008-06-20�Ժ��¼��
	 * �������ݼ�¼ 
	 * ���������ݼ�¼��ʱ��
	 * ���������ɹ�
	 * 
	 * ��һ�ε���������: 295
		�ڶ��ε���������:34144
		cust_idΪ�յ���������:2615
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
		Statement stmt4 = con3.createStatement();

		String sql2 = "select * from CRM_SUNTEK_TRANSACTION where CST_DATETIME > convert(datetime,'2008-06-20',21) order by CST_DATETIME asc ";
		ResultSet rs2 = stmt2.executeQuery(sql2);

		int j=0;
		int i=0;
		int t=0;
		while (rs2.next()) {
			String phone = rs2.getObject(1).toString().trim();//����绰��
			String date = rs2.getObject(2).toString().trim();//����ʱ��
			String headcode = rs2.getObject(3).toString().trim();//��������
			String agentid = rs2.getObject(4).toString().trim();//Ӧ����ϯ
			String svccontent = rs2.getObject(5).toString().trim();//�������
			String finishflag = rs2.getObject(6).toString().trim();//��ɱ�־
			
			if(phone!=null&&!"".equals(phone)&&date!=null&&!"".equals(date)){
				if("0001".equals(headcode)) headcode = "���󷢲�";
				if("0002".equals(headcode)) headcode = "�۸�����";
				if("0003".equals(headcode)) headcode = "���󷢲�";
//				if("0004".equals(headcode)) headcode = "";
				if("0005".equals(headcode)) headcode = "����ͨ";
//				if("0006".equals(headcode)) headcode = "";
				if("0007".equals(headcode)) headcode = "��Ŀ��Ѷ";
				if("0008".equals(headcode)) headcode = "ҽ�Ʒ���";
				if("0009".equals(headcode)) headcode = "����ͨ";
				if("0010".equals(headcode)) headcode = "������ѯ";
//				if("0011".equals(headcode)) headcode = "";
				if("0012".equals(headcode)) headcode = "����ͨ";
				if("0013".equals(headcode)) headcode = "ҽ�Ʒ���";
				if("0014".equals(headcode)) headcode = "��ũͨ";
				if("0015".equals(headcode)) headcode = "��ҵ����";
				String sql1 = "select count(*) from oper_question where cust_tel='"+phone+"' and ring_begintime='"+date+"' and  answer_agent='"+agentid+"'";
				ResultSet rs1 = stmt1.executeQuery(sql1);
				while(rs1.next()){
					String ret=rs1.getObject(1).toString().trim();
					if("1".equals(ret)){
						j++;
					}
					if("0".equals(ret)){
						String sql4 ="select top(1) cust_id from oper_custinfo where (cust_tel_home='"+phone
						+"' or cust_tel_work='"+phone+"' or cust_tel_mob='"+phone+"') and cust_id is not null";
						ResultSet rs3 = stmt4.executeQuery(sql4);
						String custid = "";
						while(rs3.next()){
							custid = rs3.getObject(1).toString().trim();
						}
						if("".equals(custid)) {
							i++;
							continue;
						}
						String dateTmp = date;//.substring(0,date.indexOf("."));
						System.out.println("dateTmp: "+dateTmp);
						String id = phone+"_"+ dateTmp;//ʱ����λ���ظ���
						if("0004".equals(headcode)||"0006".equals(headcode)||"0011".equals(headcode)){
							String sql5 ="insert into oper_question(id,cust_id,cust_tel,rid,dict_question_type1,question_content,answer_content,"+
							"addtime,ring_begintime,answer_agent,is_delete) values('ccq_"+id+"zz','"+custid+"','"+phone+"','"+agentid+"','��ֲ��ѯ','"+
							svccontent+"','"+svccontent+"','"+date+"','"+date+"','"+agentid+"','0')";
							int f1=stmt3.executeUpdate(sql5);
							if(f1>0) {
								System.out.println("ccq����ɹ�");
								System.out.println("sql5: "+sql5);
								t++;
							}
							String sql6 ="insert into oper_question(id,cust_id,cust_tel,rid,dict_question_type1,question_content,answer_content,"+
							"addtime,ring_begintime,answer_agent,is_delete) values('ccq_"+id+"yz','"+custid+"','"+phone+"','"+agentid+"','��ֳ��ѯ','"+
							svccontent+"','"+svccontent+"','"+date+"','"+date+"','"+agentid+"','0')";
							int f2=stmt3.executeUpdate(sql6);
							if(f2>0) {
								System.out.println("ccq����ɹ�");
								System.out.println("sql6: "+sql6);
								t++;
							}
						}else{
							String sql3 ="insert into oper_question(id,cust_id,cust_tel,rid,dict_question_type1,question_content,answer_content,"+
							"addtime,ring_begintime,answer_agent,is_delete) values('ccq_"+id+"','"+custid+"','"+phone+"','"+agentid+"','"+headcode+"','"+
							svccontent+"','"+svccontent+"','"+date+"','"+date+"','"+agentid+"','0')";
							int f=stmt3.executeUpdate(sql3);
							if(f>0) {
								System.out.println("ccq����ɹ�");
								System.out.println("sql3: "+sql3);
								t++;
							}
						}
					}
				}
			}
		}
		System.out.println("��һ�ε���������: "+j);
		System.out.println("�ڶ��ε���������:"+t);
		System.out.println("cust_idΪ�յ���������:"+i);
	}
}
