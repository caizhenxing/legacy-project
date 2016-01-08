package test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.zyf.tools.Tools;

public class Test {

	/**
	 * π¶ƒ‹√Ë ˆ
	 * @param args
	 * 2007-8-30…œŒÁ09:34:49
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties p=new Properties();
//		p.put(LocalJndiDataSource.KEY_JDBC_URL, "jdbc:oracle:thin:@172.16.2.230:1521:qware");
//		p.put(LocalJndiDataSource.KEY_JDBC_DRIVER_CLASS, "oracle.jdbc.driver.OracleDriver");
//		p.put(LocalJndiDataSource.KEY_USERNAME, "qware5");
//		p.put(LocalJndiDataSource.KEY_PASSWORD, "qware5");
//		LocalJndiDataSource.start(p);
//		CommonService cs=(CommonService)ServiceProvider.getService(CommonService.SERVICE_NAME);
//		ViewBean vb=cs.fetchAll(HrInfo.class,"admin","curd");
//		System.out.println(vb.getViewList().size());
//		String sn=new DecimalFormat("0000").format(new Long(1));
//		System.out.print(sn);
//		List l=new ArrayList();
//		l.get(0);
		//Timestamp t=new Timestamp(new Date().getTime());
		System.out.print(StringUtils.abbreviate("abcdefghijklmnopqrstuvwxyz",56));
		
	}

}
