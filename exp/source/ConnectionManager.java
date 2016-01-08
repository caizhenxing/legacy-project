/*
 * �������� 2004-10-25
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
/*
 * Created on 2004-8-3
 * ���ݿ����ӹ�����
 */


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author wanghz
 *
 */
public class ConnectionManager {
	//protected static Logger Log = LogManager.getLoger(ConnectionManager.class);
//	public static final LogUtils Log = new LogUtils(ConnectionManager.class);
	private static String filepath = null;
	static private ConnectionManager instance;
	static private int clients;
	
	private ArrayList drivers = new ArrayList();
	private HashMap pools = new HashMap();
	/**
	 * �õ�ConnectionManager����ʵ��
	 * @return ConnectionManager
	 */
	static synchronized public ConnectionManager getInstance(){
		if(instance==null){
			instance = new ConnectionManager();
		}
		clients++;
		return instance;
	}
	/**
	 * ��ʼ�������ļ�·��
	 * @param path
	 */
	public static void init(String path){
		filepath = path;
	}
	/**
	 * constructor
	 *
	 */
	private ConnectionManager(){
		init();
	}
	/**
	 * ��ʹ��������ݿ����ӷŽ����ݿ����ӳ�
	 * @param name
	 * @param conn
	 */
	public void free(String name,Connection conn){
		ConnectionPool pool = (ConnectionPool)pools.get(name);
		if(pool!=null){
			pool.freeConn(conn);
		}
	}
	/**
	 * �õ�һ���򿪵����ݿ����ӣ������ǰû�п��õ����ӣ�����û�дﵽ����������
	 * �򴴽�һ���µ�����
	 * 
	 * @param name ���ӳص�����
	 * @return Connection ���ݿ����ӻ�null
	 */
	public Connection getConnection(String name){
		ConnectionPool pool = (ConnectionPool)pools.get(name);
		if(pool!=null){
			return pool.getConnection();
		}
		return null;
	}
	/**
	 * ����һ���򿪵����ӣ������ǰû�п��õ����ӣ�����û�дﵽ������������
	 * �򴴽�һ���µ����ӣ�����Ѿ��ﵽ������������ ����ȴ�����ʱ��
	 * @param name ���ӳص�����
	 * @param time �ȴ��ĺ�����
	 * @return Connection ���ݿ����ӻ���Ϊnull
	 */
	public Connection getConnection(String name,long time){
		ConnectionPool pool = (ConnectionPool)pools.get(name);
		if(pool!=null){
			return pool.getConnection(time);
		}
		return null;
	}
	/**
	 * �ر��������ӣ����������������ĵǼ�
	 *
	 */
	public synchronized void release(){
		//�����ǰ�����û������ݿ�������ȴ�
		if(--clients!=0){
			return ;
		}
		//log("Rease all resources.");
		//�õ����ݿ����ӳ��е���������
		Set allpools = pools.keySet();
		Iterator it = allpools.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			ConnectionPool pool = (ConnectionPool)pools.get(key);
			
			//�ر�����
			pool.release();
		}
		
		//�õ����ݿ����ӳ������еǼǵ���������
		Iterator ds = drivers.iterator();
		while(ds.hasNext()){
			Driver driver = (Driver)ds.next();
			try{
				//�����������
				DriverManager.deregisterDriver(driver);
				//log("Deregistered JDBC driver : "+driver.getClass().getName());
			}catch(SQLException e){
				log(e,"�ͷ�����ʱ����"+driver.getClass().getName());
			}
		}
	}
	/**
	 * �������Դ���ConnectionPoolʵ��
	 * ConnectionPool����ʹ����������ԣ�
	 * <poolname>.url
	 * <poolname>.user 
	 * <poolname>.password
	 * <poolname>.maxconn
	 * 
	 * @param props
	 */
	private void createPools(Properties props){
		Enumeration propNames = props.propertyNames();
		while(propNames.hasMoreElements()){
			String name = (String)propNames.nextElement();
			if(name.endsWith(".url")){
				String poolName = name.substring(0,name.lastIndexOf("."));
				String url = props.getProperty(poolName+".url");
				if(url==null){
					log("no url specified for "+poolName);
					continue;
				}
				String user = props.getProperty(poolName+".user");
				String password = props.getProperty(poolName+".password");
				String maxconn = props.getProperty(poolName+".maxconn");
				String pingCommand = props.getProperty(poolName+".ping");
				int max;
				try{
					max = Integer.parseInt(maxconn);					
				}catch(NumberFormatException e){
					log("���Ϸ��������������"+maxconn);
					max = 0;
				}
				ConnectionPool pool = new ConnectionPool(poolName,url,user,password,max);
				pool.setPingCommand(pingCommand);
				pools.put(poolName,pool);
				log("Initialized pool "+poolName);
			}
		}
	}
	
	/**
	 * װ�ز��Ǽ�����JDBC��������
	 * @param props ���ݿ����ӳص�����
	 */
	private void loadDrivers(Properties props){
		String driverClasses = props.getProperty("drivers");
		StringTokenizer st = new StringTokenizer(driverClasses);
		while(st.hasMoreElements()){
			String name = st.nextToken().trim();
			try{
				Driver driver = (Driver)Class.forName(name).newInstance();
				DriverManager.registerDriver(driver);
				drivers.add(driver);
				log("Registered JDBC driver : "+name);
			}catch(Exception e){
				log("Can't register JDBC dirver :"+name+"Exception : "+e);
			}
		}
	}
	
	/**
	 * װ�����Բ�����ֵ��ʼ��ʵ��
	 *
	 */
	private void init(){
		String propfile = filepath;//"D:\\workspace\\z_JavaTest\\ebs_interface\\db.properties";
		log("ConnectionManager �����ļ���"+propfile);
		Properties props = new Properties();
		try{
			FileInputStream is = new FileInputStream(propfile);
			props.load(is);
		}catch(Exception e){
			log("��ȡ�����ļ�ʱ����"+propfile);
		}
		loadDrivers(props);
		createPools(props);
		
	}
	/**
	 * �ڲ���InnerClass ����һ�����ݿ����ӳ�
	 * @author WangHz
	 *
	 */
	class ConnectionPool{
		private int checkedOut;
		private ArrayList freeConns = new ArrayList();
		private int maxConn;
		private String name;
		private String password;
		private String URL;
		private String user;
		private String pingCommand;
		/**
		 * ����һ���µ����ӳ�
		 * 
		 * @param name ���ӳص�����
		 * @param url ���ݿ��jdbc url
		 * @param user ���ݿ���û���
		 * @param psw �û�����
		 * @param maxConn ���������
		 */
		public ConnectionPool(String name,String url,String user,String psw,int maxConn){
			this.name = name;
			this.URL = url;
			this.user = user;
			this.password = psw;
			this.maxConn = maxConn;
		}
		/**
		 * �ͷ����ӣ��������ӳأ���֪ͨ���еȴ����ӵ������߳�
		 * @param conn
		 */
		public synchronized void freeConn(Connection conn){
			//�����ӷ���freeConns����������֮��
			freeConns.add(conn);
			checkedOut--;
			notifyAll();
		}
		/**
		 *�����ӳ��еõ�һ�����ӣ������ǰû�п��е����ӣ����������û�дﵽ����򴴽�һ���µ�
		 */
		public synchronized Connection getConnection(){
			Connection conn = null;
			if(freeConns.size()>0){
				//�õ���һ������
				conn = (Connection)freeConns.get(0);
				//��ʹ���˵����Ӵ����ӳ���ɾ��
				freeConns.remove(0);
				try{
					if(conn.isClosed()){
						log("remove bad connection from "+name);
						conn = getConnection();
					}
					ping(conn);
				}catch(SQLException e){
					log("remove bad connection from "+name);
					conn = getConnection();
				}
			}
			//û������������������������ӳ��������û�г����������
			else if(maxConn==0||checkedOut<maxConn){
				conn = newConnection();
			}
			if(conn!=null){
				checkedOut++;
				//log("Get a connection from pool "+name);
			}
			
			return conn;
		}
		/**
		 *�����ӳ��еõ�һ�����ӣ������ǰû�п��е����ӣ����������û�дﵽ����򴴽�һ���µ�
		 *@param timeout ����Ϊ��λ�ĳ�ʱ��ֵ
		 */
		public synchronized Connection getConnection(long timeout){
			//�õ���ǰʱ��Ϊ��ʼʱ��
			long startTime = new Date().getTime();
			Connection conn;
			while((conn = getConnection()) == null){
				try{
					//log("not enough connection waiting");
					wait(timeout);
				}catch(InterruptedException e){
				}
				if((new Date().getTime() - startTime)>=timeout){
					//��ʱ
					log(this.name+" time out, return null");
					return null;
				}				
			}
			return conn;
		}
		/**
		 * �ر���������
		 */
		public synchronized void release(){
			//�õ����ݿ����ӳ������������
			Iterator allconns = freeConns.iterator();
			while(allconns.hasNext()){
				Connection conn = (Connection)allconns.next();
				try{
					//�ر�
					conn.close();
				}
				catch(SQLException e){
					log("can't close connection for pool "+name);
				}
			}
			freeConns.clear();
		}
		/**
		 * ����һ���µ����ӣ�������ܣ�ʹ��ָ����userid��password
		 */		
		private Connection newConnection(){
			Connection conn = null;
			try{
				if(user == null){
					//�õ�һ���µ����ݿ�����
					conn = DriverManager.getConnection(URL);
				}
				else{
					//�õ�һ���µ����ݿ����ӣ�ʹ�����û��Ϳ���
					conn = DriverManager.getConnection(URL,user,password);
				}
				//log("Create a new Connection in pool "+ name);
			}catch(SQLException e){
				log("can't create a new connection for "+name+"\n Exception : "+e);
				return null;
			}
			return conn;
		}
		/**
		 * ��������
		 * @param conn
		 * @throws SQLException
		 */
		private void ping(Connection conn) throws SQLException {

			if (pingCommand != null) {
				//Log.debug("    ping(" + pingCommand + ")");
				Statement stmt = conn.createStatement();
				try {
					stmt.execute(pingCommand);
					stmt.close();
				} catch (SQLException e) {
//					Log.warn("ping failed:  " + e.getMessage(), e);

					try {
						if (stmt != null) {
							stmt.close();
						}
					} catch (SQLException f) {
						;
					}
					throw e;
				}
			}
		}
		public void setPingCommand(String s){
			this.pingCommand=s;
		}

	}
	
	private void log(String str){
//		Log.info(str);
	}
	private void log(Throwable e,String msg){
		//log(msg+e.toString());
//		Log.info(msg,e);
	}
}
