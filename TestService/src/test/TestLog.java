/**
 * 	@(#)TestLog.java   2006-12-4 ÏÂÎç05:09:21
 *	 ¡£ 
 *	 
 */
package test;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

 /**
 * @author ddddd
 * @version 2006-12-4
 * @see
 */
public class TestLog {

	/**
	 * @param
	 * @version 2006-12-4
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a="D:\\tomcat\\webapps\\ETOA/WEB-INF/classes/et/config/log4j/log4j.xml";
		DOMConfigurator.configure(a);
		Logger log = Logger.getLogger(TestLog.class);
		log.info("aaaaaaaaaaaaaaaa");
	}

}
