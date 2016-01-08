/*
 * @(#)MonitorSys.java	 2008-04-28
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.monitor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.swt.internal.extension.Extension;
import org.springframework.context.ApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;
/**
 * 系统监控
 * @author nie
 */
public class MonitorSys {
	
	private ServletConfig config = null;
	private String second = null;			//CPU连续 （多少秒）
	private String overstep = null;		//超出 （百分之几）后记录数据库
	private String minute = null;			//CPU使用率记录到数据库的时间间隔（分钟）
	private String day = null;				//CPU使用率记录超出多少天后删除
	private String serverName = null;		//服务器名
	
	private int secondInt = 0;
	private int overstepInt = 0;
	private int minuteInt = 0;
	
	private int n = 0;						//用于CPU连续时间的记时变量
	private int b = 0;						//是否已经插入过数据记录
	
	/**
	 * 枸造函数，传入config
	 * @param config
	 */
	public MonitorSys(ServletConfig config){
		this.config = config;
		second = config.getInitParameter("second");
		overstep = config.getInitParameter("overstep");
		minute = config.getInitParameter("minute");
		day = config.getInitParameter("day");
		serverName = config.getInitParameter("servername");
		
		if(isRight()){
			monitor();
		}
	}
	/**
	 * 判断参数是的合法性，如果全法就把second,overstep,minute转换成int
	 * @return boolean
	 */
	public boolean isRight(){
		
		try{
			if(	second != null && overstep != null && minute != null && day != null ){
				if( overstep.indexOf("%") != -1 ){								//如果CPU使用率参数存在百分号
					overstep = overstep.substring(0,overstep.length()-1);		//则先去掉面分号
				}
				secondInt = Integer.valueOf(second).intValue();
				if( secondInt <= 0 ){											//如果值小于0则赋初值
					secondInt = 10;
				}
				overstepInt = Integer.valueOf(overstep).intValue();
				if( overstepInt <= 0 ){
					overstepInt = 10;
				}
				minuteInt = Integer.valueOf(minute).intValue();
				if( minuteInt <= 0 ){
					minuteInt = 10;
				}
			}
			return true;
			
		}catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * 进行系统监控的主要方法
	 */
	public void monitor(){
		
		//首先开启第一个线程，用于删除超过规定天数的数据
		Thread t1 = new Thread() {
			public void run() {
				while (true) {
					
					try {
						String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
						String sql = "delete from monitor_cpu where addtime < dateadd(d,-"+ day +",'"+ date +"')";
						executeUpdate(sql);
						
						Thread.sleep(86400000);//24*60*60*1000
					} catch (Exception e) {
						System.err.println(e);
					}
				}
			}
		};
		t1.start();
		//然后开启第二个线程，用于定时记录CPU使用率到服务器
		Thread t2 = new Thread() {
			public void run() {
				while (true) {
					
					try {
						String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date());
						String sql = "insert into monitor_cpu values ('"+ serverName +"','"+ date +"','"+  Extension.GetCpuUsages() +"','2','"+ date +"')";
						executeUpdate(sql);
						
						Thread.sleep( 1000 * 60 * minuteInt );
					} catch (Exception e) {
						System.err.println(e);
					}
				}
			}
		};
		t2.start();
		//最后开启一个线程，用于CPU超出多少时记录到数据库
		Thread t3 = new Thread() {
			public void run() {
				while (true) {
					
					try {
						
						int usage = Extension.GetCpuUsages();
						if( usage > overstepInt ){				//满足CPU使用率大于多少的条件
							if( b == 0 && n >= secondInt ){		//此次高峰是否已插入过数据记录，又满足记时器大于规定时间
								Date date = new Date();	
								String addtime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
								long l = date.getTime();
								date.setTime( l - (secondInt*1000) );//时间向前推多少秒
								String cputime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
								String sql = "insert into monitor_cpu values ('"+ serverName +"','"+ cputime +"','"+  usage +"','1','"+ addtime +"')";
								b = executeUpdate(sql);			//成功插入数据后，b等于1
								n = 0;							//成功后n等于0，用于再次记时
								
							}else{
								n++;				//加一秒
							}
							
						}else{
							b = 0;		//高峰结束，允许再次计时
						}
						
						Thread.sleep( 1000 );//一秒一次
					} catch (Exception e) {
						System.err.println(e);
					}
				}
			}
		};
		t3.start();

	}
	
	/**
	 * 执行SQL语句
	 * @param sql
	 * @return i 返回int
	 */
	public int executeUpdate(String sql) {

		Connection conn = null;
		Statement stmt = null;

		ServletContext sc = config.getServletContext();
		ApplicationContext ac = (ApplicationContext)sc.getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
		BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			int i = stmt.executeUpdate(sql);

			return i;

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return 0;

	}

}
