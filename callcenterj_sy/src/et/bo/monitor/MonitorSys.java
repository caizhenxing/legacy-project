/*
 * @(#)MonitorSys.java	 2008-04-28
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
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
 * ϵͳ���
 * @author nie
 */
public class MonitorSys {
	
	private ServletConfig config = null;
	private String second = null;			//CPU���� �������룩
	private String overstep = null;		//���� ���ٷ�֮�������¼���ݿ�
	private String minute = null;			//CPUʹ���ʼ�¼�����ݿ��ʱ���������ӣ�
	private String day = null;				//CPUʹ���ʼ�¼�����������ɾ��
	private String serverName = null;		//��������
	
	private int secondInt = 0;
	private int overstepInt = 0;
	private int minuteInt = 0;
	
	private int n = 0;						//����CPU����ʱ��ļ�ʱ����
	private int b = 0;						//�Ƿ��Ѿ���������ݼ�¼
	
	/**
	 * ���캯��������config
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
	 * �жϲ����ǵĺϷ��ԣ����ȫ���Ͱ�second,overstep,minuteת����int
	 * @return boolean
	 */
	public boolean isRight(){
		
		try{
			if(	second != null && overstep != null && minute != null && day != null ){
				if( overstep.indexOf("%") != -1 ){								//���CPUʹ���ʲ������ڰٷֺ�
					overstep = overstep.substring(0,overstep.length()-1);		//����ȥ����ֺ�
				}
				secondInt = Integer.valueOf(second).intValue();
				if( secondInt <= 0 ){											//���ֵС��0�򸳳�ֵ
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
	 * ����ϵͳ��ص���Ҫ����
	 */
	public void monitor(){
		
		//���ȿ�����һ���̣߳�����ɾ�������涨����������
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
		//Ȼ�����ڶ����̣߳����ڶ�ʱ��¼CPUʹ���ʵ�������
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
		//�����һ���̣߳�����CPU��������ʱ��¼�����ݿ�
		Thread t3 = new Thread() {
			public void run() {
				while (true) {
					
					try {
						
						int usage = Extension.GetCpuUsages();
						if( usage > overstepInt ){				//����CPUʹ���ʴ��ڶ��ٵ�����
							if( b == 0 && n >= secondInt ){		//�˴θ߷��Ƿ��Ѳ�������ݼ�¼���������ʱ�����ڹ涨ʱ��
								Date date = new Date();	
								String addtime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
								long l = date.getTime();
								date.setTime( l - (secondInt*1000) );//ʱ����ǰ�ƶ�����
								String cputime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
								String sql = "insert into monitor_cpu values ('"+ serverName +"','"+ cputime +"','"+  usage +"','1','"+ addtime +"')";
								b = executeUpdate(sql);			//�ɹ��������ݺ�b����1
								n = 0;							//�ɹ���n����0�������ٴμ�ʱ
								
							}else{
								n++;				//��һ��
							}
							
						}else{
							b = 0;		//�߷�����������ٴμ�ʱ
						}
						
						Thread.sleep( 1000 );//һ��һ��
					} catch (Exception e) {
						System.err.println(e);
					}
				}
			}
		};
		t3.start();

	}
	
	/**
	 * ִ��SQL���
	 * @param sql
	 * @return i ����int
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
