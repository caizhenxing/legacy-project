/**
 * 
 */
package et.bo.sys.bak.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import et.bo.common.path.Path;
import et.bo.sys.bak.service.BakService;
import et.po.DbBackup;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author dengwei
 * 
 */
public class BackupMysqlServiceImpl extends Thread implements BakService {

	private BaseDAO dao = null;

	private KeyService ks = null;
	
	private int num = 0;
	
	// 数据库用户名
	private  String USER;// = "root";

	// 数据库密码
	private  String PASSWORD;// = "root";

	// 数据库名
	private  String DATA_NAME;// = "callcenter";
	
//	 bat文件名  add by dengwei 
	private String batName; 
	
	
//	 数据库备份路径名  add by dengwei 
	private String savePath; 
	
	// 数据库类型 add by dengwei 
	private String dbType; 
	
	private  String BAT_PATH_RUN;
	
	private String recoverPath;//还原数据库备份文件路径；

	// cmd format
	// {0}bat文件路径
	// {1}数据库用户名
	// {2}数据库密码
	// {3}数据库名
	// {4}备份文件所在路径
	private   String MESSAGE_FORMAT = "cmd   /c   start  {0} {1} {2} {3} {4}";

	private   String PRE_BACKUP_FILE_NAME;

	private   String SUFFIX_BACKUP_FILE_NAME = ".sql";

	private  String getClassFilePath() {
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
	 * spring定时里调用的方法
	 * 
	 * @see et.bo.common.bak.BakService#backup()
	 */
	public void backup() {
		// TODO Auto-generated method stub
		boolean flag=backupCheck();
		if(true == flag){
			BackupMysqlServiceImpl bms = new BackupMysqlServiceImpl();
			bms.setUSER(USER);
			bms.setPASSWORD(PASSWORD);
			bms.setDATA_NAME(DATA_NAME);
			bms.setBatName(batName);
			bms.setSavePath(savePath);
			bms.setDbType(dbType);
			bms.setBAT_PATH_RUN(BAT_PATH_RUN);
			bms.setPRE_BACKUP_FILE_NAME(PRE_BACKUP_FILE_NAME);
			bms.setMESSAGE_FORMAT(MESSAGE_FORMAT);
			bms.start();
		}
		//还原数据库文件测试
//		startRecoverImmediate("D:/backupdatabase/sys_manageC_DbBak_byday_Day31.sql");
	}
	
	/**
	 * 设置必须字段属性
	 *
	 */
	public void backupSet(){
//		*************打印***************
//		System.out.println("USER: "+USER);
//		System.out.println("PASSWORD: "+PASSWORD);
//		System.out.println("DATA_NAME: "+DATA_NAME);
//		System.out.println("batName: "+batName);
//		System.out.println("savePath: "+savePath);
//		System.out.println("dbType: "+dbType);
		//*****************************************
		if("mysql".equals(dbType)){
			if("".equals(PASSWORD)){
				batName="backupNoPassMysql.bat";
			}
			MESSAGE_FORMAT = "cmd   /c   start  {0} {1} {2} {3} {4}";
		}
		if("oracle".equals(dbType)){
			MESSAGE_FORMAT	="exp {0}/{1}@@{2} file={3} ";
		}
		BAT_PATH_RUN = getClassFilePath()+batName;
		System.out.println("path BAT_PATH_RUN: "+BAT_PATH_RUN);
		if(!new File(savePath).exists()){
			System.out.println("!new File(file).exists(): "+!new File(savePath).exists());
			new File(savePath).mkdir();
		}
	}
	/**
	 * 立即备份
	 *
	 */
	public void startBakupImmediate(){
		PRE_BACKUP_FILE_NAME = DATA_NAME+"_C_DbBak_byday_Day"+getDay();
		System.out.println("PRE_BACKUP_FILE_NAME: "+PRE_BACKUP_FILE_NAME);
		BackupMysqlServiceImpl bms = new BackupMysqlServiceImpl();
		bms.setUSER(USER);
		bms.setPASSWORD(PASSWORD);
		bms.setDATA_NAME(DATA_NAME);
		bms.setBatName(batName);
		bms.setSavePath(savePath);
		bms.setDbType(dbType);
		bms.setBAT_PATH_RUN(BAT_PATH_RUN);
		bms.setPRE_BACKUP_FILE_NAME(PRE_BACKUP_FILE_NAME);
		bms.setMESSAGE_FORMAT(MESSAGE_FORMAT);
		bms.start();
	}
	//**************************还原接口*****************************
	/**
	 * 立即还原
	 *
	 */
	public void startRecoverImmediate(String recoverPath){
		if("mysql".equals(dbType)){
			if("".equals(PASSWORD)){
				batName="recoverNoPassMysql.bat";
			}else
				batName="recoverMysql.bat";
			MESSAGE_FORMAT = "cmd   /c   start  {0} {1} {2} {3} {4}";
			System.out.println(MESSAGE_FORMAT+" ......");
		}
		if("oracle".equals(dbType)){
			MESSAGE_FORMAT	="imp {0}/{1}@@{2} file={3} ";
		}
		BAT_PATH_RUN = getClassFilePath()+batName;
		this.recoverPath = recoverPath;
		System.out.println("recoverPath: "+recoverPath);
		BackupMysqlServiceImpl bms = new BackupMysqlServiceImpl();
		bms.setUSER(USER);
		bms.setPASSWORD(PASSWORD);
		bms.setDATA_NAME(DATA_NAME);
		bms.setBatName(batName);
		bms.setSavePath(savePath);
		bms.setDbType(dbType);
		bms.setBAT_PATH_RUN(BAT_PATH_RUN);
		bms.setPRE_BACKUP_FILE_NAME(PRE_BACKUP_FILE_NAME);
		bms.setMESSAGE_FORMAT(MESSAGE_FORMAT);
		bms.setRecoverPath(recoverPath);
		bms.runRecover();
	}
	
	public void runRecover() {
		// TODO Auto-generated method stub
		try {
			if("mysql".equals(dbType)){
				Runtime.getRuntime().exec(getCmdStrMysqlRecover());
			}
			if("oracle".equals(dbType)){
				Runtime.getRuntime().exec(getCmdStrOracleRecover());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private  String getCmdStrMysqlRecover() {
		Object[] args = { this.getBAT_PATH_RUN(),
				this.getUSER(), this.getPASSWORD(),
				this.getDATA_NAME(),
				this.getRecoverPath()};
		String batCmd = MessageFormat.format(MESSAGE_FORMAT, args);
		System.out.println(batCmd);
		return batCmd;
	}
	
	private  String getCmdStrOracleRecover() {
		Object[] args={
				this.getUSER(), 
				this.getPASSWORD(),
				this.getDATA_NAME(),
				this.getRecoverPath()};
		String batCmd = MessageFormat.format(MESSAGE_FORMAT, args);
		return batCmd;
	}

//**************************************************************************
	/**
	 * 备份检查方法：按月、季、周、日方式备份
	 * 如果满足条件，则生成数据库备份文件。
	 * @return
	 */
	private boolean backupCheck(){
		backupSet();
		MyQuery mq=new MyQueryImpl();
		StringBuffer hql = new StringBuffer();
		hql.append("select c from DbBackup c order by id desc ");
		mq.setHql(hql.toString());
		Object[] result = (Object[]) dao.findEntity(mq);
		DbBackup u = (DbBackup) result[0];
//		*************打印***************
		System.out.println("getBaktype(): "+u.getBaktype());
		System.out.println("getBakdate(): "+u.getBakdate());
		System.out.println("getBaktime(): "+u.getBaktime());
//		*****************************************
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//当前日期
		String strDate = sdf.format(cal.getTime());
		System.out.println("Calendar: "+strDate);
		
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");//当前时间
		String strTime = sdfTime.format(cal.getTime());
		System.out.println("Calendar: "+strTime);
		  
		PRE_BACKUP_FILE_NAME = DATA_NAME+"_C_DbBak_by"+u.getBaktype()+"_";
		System.out.println("PRE_BACKUP_FILE_NAME: "+PRE_BACKUP_FILE_NAME);
		
		if("day".equals(u.getBaktype())) {
			if(strTime.equals(u.getBaktime())){
				PRE_BACKUP_FILE_NAME=PRE_BACKUP_FILE_NAME+"Day"+getDay();
				System.out.println("PRE_BACKUP_FILE_NAME: "+PRE_BACKUP_FILE_NAME);
				return true;
			}
		}
		
		if("week".equals(u.getBaktype())) {
			String strWeek=getWeek(new Date());//当前星期  
			String weekDate = getWeek(TimeUtil.getTimeByStr(u.getBakdate().toString(),"yyyy-MM-dd"));
			if(strWeek.equals(weekDate)&&strTime.equals(u.getBaktime())){
				PRE_BACKUP_FILE_NAME=PRE_BACKUP_FILE_NAME+weekDate;
				System.out.println("PRE_BACKUP_FILE_NAME: "+PRE_BACKUP_FILE_NAME);
				return true;
			}
		}
		if("quarter".equals(u.getBaktype())) {
			String  sea=getSeason(strDate.substring(5,7));
			String tempSeason=getSeason(u.getBakdate().toString().substring(5,7));
			int tempMon=Integer.parseInt(u.getBakdate().toString().substring(5,7))%3;
			int mon=Integer.parseInt(strDate.substring(5,7))%3;
			String tempDate =u.getBakdate().toString().substring(8,10);
			strDate=strDate.substring(8);
			if(sea.equals(tempSeason)&&mon==tempMon&&strDate.equals(tempDate)&&strTime.equals(u.getBaktime())){
				PRE_BACKUP_FILE_NAME=PRE_BACKUP_FILE_NAME+"Quarter"+sea;
				System.out.println("PRE_BACKUP_FILE_NAME: "+PRE_BACKUP_FILE_NAME);
				return true;
			}
		}
		
		if("month".equals(u.getBaktype())) {
			String tempDate =u.getBakdate().toString().substring(8,10);
			strDate=strDate.substring(8);
			String mon=u.getBakdate().toString().substring(5,7);
			System.out.println("tempDate: "+tempDate);
			if(strDate.equals(tempDate)&&strTime.equals(u.getBaktime())){
				PRE_BACKUP_FILE_NAME=PRE_BACKUP_FILE_NAME+"Month"+mon;
				System.out.println("PRE_BACKUP_FILE_NAME: "+PRE_BACKUP_FILE_NAME);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 取季度
	 * @param strMon
	 * @return
	 */
	private String getSeason(String strMon){
		int strMonth=Integer.parseInt(strMon);
		String season="";
		if(strMonth>=1&&strMonth<=3) season="1";
		if(strMonth>=4&&strMonth<=6) season="2";
		if(strMonth>=7&&strMonth<=9) season="3";
		if(strMonth>=10&&strMonth<=12) season="4";
		return season;
	}
	/**
	 * 取星期
	 * @param curDate
	 * @return
	 */
	private String getWeek(Date curDate){
	  int   i=curDate.getDay();   
	 String strWeek="";
	  if   (i==0){ 
		  strWeek="星期天";
	  }else{
		  strWeek="星期"+i;
	  }
	  return strWeek;
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
			if("mysql".equals(dbType)){
				Runtime.getRuntime().exec(getCmdStrMysql());
			}
			if("oracle".equals(dbType)){
				Runtime.getRuntime().exec(getCmdStrOracle());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public BackupMysqlServiceImpl() {
		
	}

	public BackupMysqlServiceImpl(String str) {
		this.CMD_MYSQL = "WebRoot\\WEB-INF\\classes\\et\\bo\\sys\\bak\\service\\impl\\"+ str;
	}

	private  String getCmdStrMysql() {
		Object[] args = { this.getBAT_PATH_RUN(),
				this.getUSER(), this.getPASSWORD(),
				this.getDATA_NAME(),
				this.getFileName()};
		String batCmd = MessageFormat.format(MESSAGE_FORMAT, args);
		return batCmd;
	}
	
	private  String getCmdStrOracle() {
		Object[] args={
				this.getUSER(), 
				this.getPASSWORD(),
				this.getDATA_NAME(),
				this.getFileName()};
		String batCmd = MessageFormat.format(MESSAGE_FORMAT, args);
		return batCmd;
	}

	private  String getDay() {
		// "yyyy-MM-dd HH:mm:ss";
		String format = "dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	private  String getFileName() {
		StringBuilder sb = new StringBuilder();
		sb.append(savePath); //add by dengwei 
//		sb.append("D:/backupdatabase/");
		sb.append(PRE_BACKUP_FILE_NAME);
//		sb.append(getDay());
		sb.append(SUFFIX_BACKUP_FILE_NAME);
		return sb.toString();
	}

	public  void main(String[] args) {
		BackupMysqlServiceImpl bms = new BackupMysqlServiceImpl();
		System.out.println(bms.getCmdStrMysql());
		System.out.println("path "
				+ BackupMysqlServiceImpl.class.getResource("").toString());
		new BackupMysqlServiceImpl().start();
	}

	//*****************业务接口方法*****************
	
	public void addBak(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createBak(dto));
	}
	
	private DbBackup createBak(IBaseDTO dto) {
		DbBackup dbup = new DbBackup();
		dbup.setId(ks.getNext("db_backup"));
		dbup.setBaktype(dto.get("dbtype").toString());
		dbup.setBakdate(TimeUtil.getTimeByStr(dto.get("begindate").toString(), "yyyy-MM-dd"));
		dbup.setBaktime(dto.get("begintime").toString());
		dbup.setRemark(dto.get("remark").toString());
		return dbup;
	}
	
	public boolean updateBak(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(modifyBak(dto));
		return false;
	}
	
	private DbBackup modifyBak(IBaseDTO dto){
		DbBackup dbup = (DbBackup)dao.loadEntity(DbBackup.class, dto.get("id").toString());
		dbup.setBaktype(dto.get("dbtype").toString());
		dbup.setBakdate(TimeUtil.getTimeByStr(dto.get("begindate").toString(), "yyyy-MM-dd"));
		dbup.setBaktime(dto.get("begintime").toString());
		dbup.setRemark(dto.get("remark").toString());
		return dbup;
	}
	
	public void delBak(String id) {
		// TODO Auto-generated method stub
		DbBackup u = (DbBackup)dao.loadEntity(DbBackup.class, id);
		dao.removeEntity(u);
	}
	
	public List bakQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		BakHelp bh = new BakHelp();
		Object[] result = (Object[]) dao.findEntity(bh.bakQuery(dto, pi));
		num = dao.findEntitySize(bh.bakQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			DbBackup u = (DbBackup) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", u.getId());
			dbd.set("dbtype",u.getBaktype());
			dbd.set("begindate",TimeUtil.getTheTimeStr(u.getBakdate(),"yyyy-MM-dd"));
			dbd.set("begintime",u.getBaktime());
			dbd.set("remark",u.getRemark());
			list.add(dbd);
		}
		return list;
	}
	
	/**
	 * 
	 * 通过ID显示所有信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public IBaseDTO getBakInfo(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		DbBackup u = (DbBackup)dao.loadEntity(DbBackup.class,id);
		dto.set("id", u.getId());
		dto.set("dbtype",u.getBaktype());
		dto.set("begindate",TimeUtil.getTheTimeStr(u.getBakdate(),"yyyy-MM-dd"));
		dto.set("begintime",u.getBaktime());
		System.out.println(TimeUtil.getTheTimeStr(u.getBakdate(),"yyyy-MM-dd")+" ssssssssssssss");
		dto.set("remark",u.getRemark());
		return dto;
	}

	/**
	 * 
	 * 得到显示条数
	 * @param 
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
	public int getBakSize() {
		// TODO Auto-generated method stub
		return num;
	}
	//*****************************************
	
	
	public String getDATA_NAME() {
		return DATA_NAME;
	}

	public void setDATA_NAME(String data_name) {
		DATA_NAME = data_name;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String password) {
		PASSWORD = password;
	}

	public String getUSER() {
		return USER;
	}

	public void setUSER(String user) {
		USER = user;
	}

	public String getBAT_PATH_RUN() {
		return BAT_PATH_RUN;
	}

	public void setBAT_PATH_RUN(String bat_path_run) {
		BAT_PATH_RUN = bat_path_run;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getBatName() {
		return batName;
	}

	public void setBatName(String batName) {
		this.batName = batName;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public String getPRE_BACKUP_FILE_NAME() {
		return PRE_BACKUP_FILE_NAME;
	}

	public void setPRE_BACKUP_FILE_NAME(String pre_backup_file_name) {
		PRE_BACKUP_FILE_NAME = pre_backup_file_name;
	}

	public String getMESSAGE_FORMAT() {
		return MESSAGE_FORMAT;
	}

	public void setMESSAGE_FORMAT(String message_format) {
		MESSAGE_FORMAT = message_format;
	}

	public String getRecoverPath() {
		return recoverPath;
	}

	public void setRecoverPath(String recoverPath) {
		this.recoverPath = recoverPath;
	}
}
