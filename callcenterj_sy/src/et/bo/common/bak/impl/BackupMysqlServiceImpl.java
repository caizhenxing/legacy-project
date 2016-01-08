/**
 * 
 */
package et.bo.common.bak.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import et.bo.common.bak.BakService;
import et.bo.common.path.Path;

/**
 * @author zhangfeng
 * 
 */
public class BackupMysqlServiceImpl extends Thread implements BakService {

	// 数据库用户名
	private static final String USER = "root";

	// 数据库密码
	private static final String PASSWORD = "root";

	// 数据库名
	private static final String DATA_NAME = "callcenter_3d_dl";

	// 运行的bat的路径及文件名称
	// private static final String BAT_PATH_RUN =
	// "et\\bo\\common\\bak\\impl\\backupMysql.bat";
	private static final String BAT_PATH_RUN = getClassFilePath()
			+ "\\backupMysql.bat";

	// cmd format
	// {0}bat文件路径
	// {1}数据库用户名
	// {2}数据库密码
	// {3}数据库名
	// {4}备份文件所在路径
	private static final String MESSAGE_FORMAT = "cmd   /c   start  {0} {1} {2} {3} {4}";

	private static final String PRE_BACKUP_FILE_NAME = "Callcenter_DbBak_Day_";

	private static final String SUFFIX_BACKUP_FILE_NAME = ".sql";

	private static String getClassFilePath() {
		String path = "";
		try {
			path = Path.getPathFromClass(BackupMysqlServiceImpl.class);
			path = path.replaceAll("BackupMysqlServiceImpl.class", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.common.bak.BakService#backup()
	 */
	public void backup() {
		// TODO Auto-generated method stub
		new BackupMysqlServiceImpl().start();
	}

	private String CMD_MYSQL = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see excellence.common.backup.BackupDatabaseService#execute()
	 */
	public void executeCMD() {
		// TODO Auto-generated method stub
		new BackupMysqlServiceImpl("backupMysql.bat").start();
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			Runtime.getRuntime().exec(getCmdStr());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public BackupMysqlServiceImpl() {
	}

	public BackupMysqlServiceImpl(String str) {
		this.CMD_MYSQL = "WebRoot\\WEB-INF\\classes\\et\\bo\\common\\bak\\impl\\"
				+ str;
	}

	// private String getCMDStr() {
	// StringBuffer cmd = new StringBuffer();
	// cmd.append("cmd /c start " + this.CMD_MYSQL + " 111");
	// // cmd.append("cmd /c start ");
	// return cmd.toString();
	// }

	private static String getCmdStr() {
		// cmd /c start bacumysql.bat root root callcenter
		Object[] args = { BackupMysqlServiceImpl.BAT_PATH_RUN,
				BackupMysqlServiceImpl.USER, BackupMysqlServiceImpl.PASSWORD,
				BackupMysqlServiceImpl.DATA_NAME,
				BackupMysqlServiceImpl.getFileName() };
		String batCmd = MessageFormat.format(MESSAGE_FORMAT, args);
		// batCmd = "cmd.exe /c start " + batCmd;
		return batCmd;
	}

	private static String getDay() {
		// "yyyy-MM-dd HH:mm:ss";
		String format = "dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	private static String getFileName() {
		StringBuilder sb = new StringBuilder();
		sb.append("D:/backupdatabase/");
		sb.append(PRE_BACKUP_FILE_NAME);
		sb.append(getDay());
		sb.append(SUFFIX_BACKUP_FILE_NAME);
		return sb.toString();
	}

	public static void main(String[] args) {
		// String mysqlPath =
		// BackUpMysqlServiceImpl.class.getResource("").toString();
		// System.out.println(mysqlPath);
		BackupMysqlServiceImpl bms = new BackupMysqlServiceImpl();
		// // System.out.println(bms.getCMDStr());
		//System.out.println(bms.getCmdStr());
		// bms.start();
		//System.out.println("path "
				//+ getClassFilePath());
		new BackupMysqlServiceImpl().start();
	}

}
