/*
 * 创建日期 2004-11-5
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package jc.basic;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import java.io.*;
import java.util.*;
public class parameter 
{
	protected static boolean iffetch=true;
	protected static String url = "http://10.5.31.103/jc";

	protected static  String url1 = "http://10.5.31.103/jmx-console/a.swf";
	protected String appServer = "localhost"; //---------------jboss app server
	protected String dbServer = "10.5.31.108";

	protected String com = "COM1";
	protected String jndi_ds = "java:/DataSource";

	static protected String dlldir = "D:/DLLPATH/";
	protected String uploaddir = "";
	protected boolean logging = true;
	protected String refreshTime = "3";
	protected String toYingXiaoPath = "";
	protected String uploadFileTPath = "";
	protected static String BeiNengCOMPort = "03";
	protected static  String JCPT_IP = "10.5.31.108";
	protected static  String JCPT_USER = "jc";
	protected static  String JCPT_PASSWORD = "jc";
	protected static  String JCPT_DATABASE_NAME = "ORCL";
	protected static  String JCPT_PORT = "1521" + ":" + JCPT_DATABASE_NAME;
	protected parameter()
	{
		Properties p=new Properties();
		String path="c:/tmp/jcpa.txt";
		File f=new File(path);
		if(iffetch)
		{
			try{
			FileInputStream fi=new FileInputStream(f);
			p.load(fi);
			url=p.getProperty("url");
			url1=p.getProperty("url1");
			appServer=p.getProperty("appServer");
			dbServer=p.getProperty("dbServer");
			com=p.getProperty("com");
			jndi_ds=p.getProperty("jndi_ds");
			dlldir=p.getProperty("dlldir");
			uploaddir=p.getProperty("uploaddir");
			//logging=p.getProperty("");
			refreshTime=p.getProperty("refreshTime");
			toYingXiaoPath=p.getProperty("toYingXiaoPath");
			uploadFileTPath=p.getProperty("uploadFileTPath");
			BeiNengCOMPort=p.getProperty("BeiNengCOMPort");
			JCPT_IP=p.getProperty("JCPT_IP");
			JCPT_USER=p.getProperty("JCPT_USER");
			JCPT_PASSWORD=p.getProperty("JCPT_PASSWORD");
			JCPT_DATABASE_NAME=p.getProperty("JCPT_DATABASE_NAME");
			JCPT_PORT=p.getProperty("JCPT_PORT");
			fi.close();
			}catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * @return
	 */
	public static String getBeiNengCOMPort() {
		return BeiNengCOMPort;
	}

	/**
	 * @return
	 */
	public static String getDlldir() {
		return dlldir;
	}

	/**
	 * @return
	 */
	public static boolean isIffetch() {
		return iffetch;
	}

	/**
	 * @return
	 */
	public static String getJCPT_DATABASE_NAME() {
		return JCPT_DATABASE_NAME;
	}

	/**
	 * @return
	 */
	public static String getJCPT_IP() {
		return JCPT_IP;
	}

	/**
	 * @return
	 */
	public static String getJCPT_PASSWORD() {
		return JCPT_PASSWORD;
	}

	/**
	 * @return
	 */
	public static String getJCPT_PORT() {
		return JCPT_PORT;
	}

	/**
	 * @return
	 */
	public static String getJCPT_USER() {
		return JCPT_USER;
	}

	/**
	 * @return
	 */
	public static String getUrl() {
		return url;
	}

	/**
	 * @return
	 */
	public static String getUrl1() {
		return url1;
	}

	/**
	 * @return
	 */
	public String getAppServer() {
		return appServer;
	}

	/**
	 * @return
	 */
	public String getCom() {
		return com;
	}

	/**
	 * @return
	 */
	public String getDbServer() {
		return dbServer;
	}

	/**
	 * @return
	 */
	public String getJndi_ds() {
		return jndi_ds;
	}

	/**
	 * @return
	 */
	public boolean isLogging() {
		return logging;
	}

	/**
	 * @return
	 */
	public String getRefreshTime() {
		return refreshTime;
	}

	/**
	 * @return
	 */
	public String getToYingXiaoPath() {
		return toYingXiaoPath;
	}

	/**
	 * @return
	 */
	public String getUploaddir() {
		return uploaddir;
	}

	/**
	 * @return
	 */
	public String getUploadFileTPath() {
		return uploadFileTPath;
	}

}
