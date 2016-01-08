/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Nov 15, 200711:17:50 AM
 * 文件名：Bizlogger.java
 * 制作者：zhaoyf
 * 
 */
package com.zyf.common.log.server;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.zyf.common.log.servlet.Log4jServlet;

/**
 * @author zhaoyf
 *
 */
public class Bizlogger {

	static 
	{
		URL log4Jresource=Bizlogger.class.getResource("log4j.xml");
		DOMConfigurator.configure(log4Jresource);
		Logger log = Logger.getLogger(Log4jServlet.class);
		log.info("log properties file is success load");
		log.info("log4j started!");
		System.setProperty("org.apache.commons.logging.Log","org.apache.commons.longging.impl.log4JLogger");
	}
	List bizList=new ArrayList(100);
	
	private static Bizlogger bl=new Bizlogger();
	
	public void addLog(String userName,String action)
	{
		
	}
	
	public void addLog(String userName,String action,String id)
	{
		
	}
	private Bizlogger()
	{
		
	}
	
	public static Bizlogger getFactroy()
	{
		return bl;
	}
}
