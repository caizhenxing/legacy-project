/*
 * 创建日期 2004-10-25
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
/*
 * Created on 2004-8-3
 * 数据库连接管理器
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
	 * 得到ConnectionManager对象实例
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
	 * 初始化属性文件路径
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
	 * 将使用完的数据库连接放进数据库连接池
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
	 * 得到一个打开的数据库连接，如果当前没有可用的连接，并且没有达到最大的连接数
	 * 则创建一个新的连接
	 * 
	 * @param name 连接池的名字
	 * @return Connection 数据库连接或null
	 */
	public Connection getConnection(String name){
		ConnectionPool pool = (ConnectionPool)pools.get(name);
		if(pool!=null){
			return pool.getConnection();
		}
		return null;
	}
	/**
	 * 返回一个打开的连接，如果当前没有可用的连接，并且没有达到最大的连接数，
	 * 则创建一个新的连接，如果已经达到了最大的连接数 ，则等待，或超时。
	 * @param name 连接池的名字
	 * @param time 等待的毫秒数
	 * @return Connection 数据库连接或者为null
	 */
	public Connection getConnection(String name,long time){
		ConnectionPool pool = (ConnectionPool)pools.get(name);
		if(pool!=null){
			return pool.getConnection(time);
		}
		return null;
	}
	/**
	 * 关闭所有连接，解除所有驱动程序的登记
	 *
	 */
	public synchronized void release(){
		//如果当前仍有用户与数据库连接则等待
		if(--clients!=0){
			return ;
		}
		//log("Rease all resources.");
		//得到数据库连接池中的所有连接
		Set allpools = pools.keySet();
		Iterator it = allpools.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			ConnectionPool pool = (ConnectionPool)pools.get(key);
			
			//关闭连接
			pool.release();
		}
		
		//得到数据库连接池中所有登记的驱动程序
		Iterator ds = drivers.iterator();
		while(ds.hasNext()){
			Driver driver = (Driver)ds.next();
			try{
				//解除驱动程序
				DriverManager.deregisterDriver(driver);
				//log("Deregistered JDBC driver : "+driver.getClass().getName());
			}catch(SQLException e){
				log(e,"释放驱动时出错："+driver.getClass().getName());
			}
		}
	}
	/**
	 * 基于属性创建ConnectionPool实例
	 * ConnectionPool可以使用下面的属性：
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
					log("不合法的最大连接数："+maxconn);
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
	 * 装载并登记所有JDBC驱动程序
	 * @param props 数据库连接池的属性
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
	 * 装载属性并用其值初始化实例
	 *
	 */
	private void init(){
		String propfile = filepath;//"D:\\workspace\\z_JavaTest\\ebs_interface\\db.properties";
		log("ConnectionManager 属性文件："+propfile);
		Properties props = new Properties();
		try{
			FileInputStream is = new FileInputStream(propfile);
			props.load(is);
		}catch(Exception e){
			log("读取属性文件时出错："+propfile);
		}
		loadDrivers(props);
		createPools(props);
		
	}
	/**
	 * 内部类InnerClass 代表一个数据库连接池
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
		 * 创建一个新的连接池
		 * 
		 * @param name 连接池的名字
		 * @param url 数据库的jdbc url
		 * @param user 数据库的用户名
		 * @param psw 用户口令
		 * @param maxConn 最大连接数
		 */
		public ConnectionPool(String name,String url,String user,String psw,int maxConn){
			this.name = name;
			this.URL = url;
			this.user = user;
			this.password = psw;
			this.maxConn = maxConn;
		}
		/**
		 * 释放连接，插入连接池，并通知所有等待连接的其他线程
		 * @param conn
		 */
		public synchronized void freeConn(Connection conn){
			//将连接放在freeConns中其他连接之后
			freeConns.add(conn);
			checkedOut--;
			notifyAll();
		}
		/**
		 *从连接池中得到一个连接，如果当前没有空闲的连接，如果连接数没有达到最大，则创建一个新的
		 */
		public synchronized Connection getConnection(){
			Connection conn = null;
			if(freeConns.size()>0){
				//得到第一个连接
				conn = (Connection)freeConns.get(0);
				//将使用了的连接从连接池中删除
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
			//没有限制最大连接数，或者连接池里的连接没有超过最大数量
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
		 *从连接池中得到一个连接，如果当前没有空闲的连接，如果连接数没有达到最大，则创建一个新的
		 *@param timeout 毫秒为单位的超时的值
		 */
		public synchronized Connection getConnection(long timeout){
			//得到当前时间为开始时间
			long startTime = new Date().getTime();
			Connection conn;
			while((conn = getConnection()) == null){
				try{
					//log("not enough connection waiting");
					wait(timeout);
				}catch(InterruptedException e){
				}
				if((new Date().getTime() - startTime)>=timeout){
					//超时
					log(this.name+" time out, return null");
					return null;
				}				
			}
			return conn;
		}
		/**
		 * 关闭所有连接
		 */
		public synchronized void release(){
			//得到数据库连接池里的所有连接
			Iterator allconns = freeConns.iterator();
			while(allconns.hasNext()){
				Connection conn = (Connection)allconns.next();
				try{
					//关闭
					conn.close();
				}
				catch(SQLException e){
					log("can't close connection for pool "+name);
				}
			}
			freeConns.clear();
		}
		/**
		 * 创建一个新的连接，如果可能，使用指定的userid和password
		 */		
		private Connection newConnection(){
			Connection conn = null;
			try{
				if(user == null){
					//得到一个新的数据库连接
					conn = DriverManager.getConnection(URL);
				}
				else{
					//得到一个新的数据库连接，使用了用户和口令
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
		 * 测试连接
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
