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
/**
 * <p>Title: 集抄平台</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 辜小峰 赵一非 
 * @time 2004/11/05
 * @version 2.0
 */

public class basicArg {

	//tempfile this is using b/s temp file.
	static parameter p=new parameter();
	
	private String basicPath;
	public String absPath = this.fetchAbsPath();
	//log file this is use log file.
	private String absPathLog = this.fetchAbsPathLog();
	//-----------------------------------------------------------------------------------------------//
	//--static final private String url = "http://10.5.31.103/jc";
	static  private String url=p.getUrl();// ="http://10.5.31.103/jc";
	//--------------this is use ip or pc name.
	public static  String url1 = p.getUrl1();//"http://10.5.31.103/jmx-console/a.swf";
	private String appServer =p.getAppServer();// "localhost"; //---------------jboss app server
	private String dbServer =p.getDbServer();// "10.5.31.108";
	//----------------this is use database,using ip or localhost.

	/**
	 * static final private String url = "http://10.5.31.84:8080/jc";//--------------this is use ip or pc name.
	private String appServer="10.5.31.84";//---------------jboss app server
	private String dbServer="10.5.31.108";//----------------this is use database,using ip or localhost.
	 */
	//-----------------------------------------------------------------------------------------------//
	//private String com="COM3";
	private String com =p.getCom();// "COM1";
	private String jndi_ds =p.getJndi_ds();// "java:/DataSource";
	//private String jndi_ds="DataSource";/////////////////////////
	// this is maybe no use
	static final private String dlldir =p.getDlldir();// "D:/DLLPATH/";
	private String uploaddir =p.getUploaddir();// "";
	private boolean logging =p.isLogging();// true;
	private String refreshTime =p.getRefreshTime();// "3";
	private String toYingXiaoPath =p.getToYingXiaoPath();// "";
	private String uploadFileTPath =p.getUploadFileTPath();// "";
	public static String BeiNengCOMPort =p.getBeiNengCOMPort();// "03";

	public static  String JCPT_IP =p.getJCPT_IP();// "10.5.31.108";
	
	public static  String JCPT_USER =p.getJCPT_USER();// "jc";
	public static  String JCPT_PASSWORD =p.getJCPT_PASSWORD();// "jc";
	public static  String JCPT_DATABASE_NAME =p.getJCPT_DATABASE_NAME();// "ORCL";
	public static  String JCPT_PORT =p.getJCPT_PORT() /*"1521"*/ + ":" + JCPT_DATABASE_NAME;

	public basicArg() {
	}
//	public static void main(String[] arg) {
//		basicArg ba = new basicArg();
//		System.out.println("===" + JCPT_USER);
//		System.out.println("" + ba.fetchUrl1());
//	}
	//fetch timp period
	public String fetchUrl() {
		return url;
	}
	public String fetchUrl1() {
		return url1;
	}
	public String fetchDllDir() {
		return dlldir;
	}
	public String fetchAbsPath() {
		return ifExistPath(this.getBasicPath() + "tempfile\\");
	}
	public String fetchAbsPathLog() {
		return ifExistPath(this.getBasicPath() + "log\\");
	}
	//fetch timp period over
	public String getCom() {
		return com;
	}
	public String getBeiNengCOMPort() {
		return BeiNengCOMPort;
	}
	public String getJndi_ds() {
		return jndi_ds;
	}
	public void setAppServer(String appServer) {
		this.appServer = appServer;
	}
	public String getAppServer() {
		return appServer;
	}
	public void setDbServer(String dbServer) {
		this.dbServer = dbServer;
	}
	public String getDbServer() {
		return dbServer;
	}
	public String getBasicPath() {
		String tempPath = "c:/temp/jcpt/";
		return tempPath;
	}
	public void setBasicPath(String basicPath) {
		this.basicPath = basicPath;
	}
	public boolean isLogging() {
		return logging;
	}
	public void setLogging(boolean logging) {
		this.logging = logging;
	}
	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}
	public String getRefreshTime() {
		return refreshTime;
	}
	public String getUploaddir() {
		String tmp = ifExistPath("c:\\temp");
		return tmp;
	}
	public String getToYingXiaoPath() {
		return ifExistPath("c:\\ToYingXiao");
	}
	private String ifExistPath(String sPath) {
		File p = new File(sPath);
		if (p.isDirectory()) {
			//System.out.println("c:/temp/jcpt/log/ is already exist!");
		} else {
			p.mkdirs();
		}
		return p.getPath() + "\\";
	}
	public String getUploadFileTPath() {
		return ifExistPath("\\uploadTemp");
	}
	public static void main(String[] args)
	{
		
		System.out.println(new basicArg().getAppServer());
		System.out.println(new basicArg().getBasicPath());
		System.out.println(new basicArg().getCom());
		System.out.println(new basicArg().getDbServer());
		System.out.println(new basicArg().com);
		System.out.println(new basicArg().getJndi_ds());
		System.out.println(new basicArg().getUploaddir());
		basicArg b=new basicArg();
		System.out.println(b.getAppServer());
		System.out.println(b.getBasicPath());
		System.out.println(b.getCom());
		System.out.println(b.getDbServer());
		System.out.println(b.getJndi_ds());
	}
}
