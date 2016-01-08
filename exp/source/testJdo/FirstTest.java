/*
 * 创建日期 2005-5-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testJdo;


import java.util.Properties;

import javax.jdo.*;
import javax.jdo.spi.*;
//import org.apache.ojb.jdo.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
