/*
 * �������� 2005-5-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testJdo;


import java.util.Properties;

import javax.jdo.*;
import javax.jdo.spi.*;
//import org.apache.ojb.jdo.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class FirstTest {

	public static void main(String[] args) {
		Properties p=new Properties();
		p.put("javax.jdo.PersistenceManagerFactoryClass","com.triactive.jdo.PersistenceManagerFactoryImpl");
		p.put("javax.jdo.option.ConnectionURL","jdbc:oracle:thin:@10.5.31.108:1521:orcl");
		p.put("javax.jdo.option.ConnectionUserName","jc");
		p.put("javax.jdo.option.ConnectionPassword","jc");
		p.put("javax.jdo.option.ConnectionDriverName","oracle.jdbc.driver.OracleDriver");
		JDOHelper jh=new JDOHelper();
		PersistenceManagerFactory pmf=jh.getPersistenceManagerFactory(p);
		
		//PersistenceManagerFactory pmf=PersistenceManagerFactoryImpl.getPersistenceManagerFactory(p);
		PersistenceManager pm=pmf.getPersistenceManager();
		Transaction trs=pm.currentTransaction();
		Query q=pm.newQuery();
		System.out.println(pm.isClosed());
		
		trs.begin();
		//String s=(String)q.execute("select * from jbsj_xqsz");
		trs.commit();
		//System.out.println(s);
	}
}
