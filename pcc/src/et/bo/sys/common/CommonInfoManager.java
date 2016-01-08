/**
 * 	@(#)CommonInfoManager.java   2006-8-29 上午09:42:22
 *	 。 
 *	 
 */
package et.bo.sys.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ocelot.framework.base.container.SpringRunningContainer;
import ocelot.framework.base.dto.IBaseDTO;
import ocelot.framework.base.dto.impl.DynaBeanDTO;

import et.bo.sys.log.service.LogService;

/**
 * @author 赵一非
 * @version 2006-8-29
 * @see
 */
public class CommonInfoManager {

	//private static org.apache.commons.logging.Log log4j = LogFactory.getLog(CommonInfoManager.class);
	
	private List<String> users=new CopyOnWriteArrayList<String>();
	private List<LogSys> logs=new CopyOnWriteArrayList<LogSys>();
	List<LogSys> ectypeLog=new ArrayList<LogSys>();
	/**
	 * 最大缓冲log数量
	 */
	private int maxLogSize=100;
	private static CommonInfoManager cim=new CommonInfoManager();
	/**
	 * 私有构造函数
	 *
	 */
	private CommonInfoManager()
	{
		
	}
	/**
	 * 工厂方法
	 * 饿汉加载
	 * @version 2006-8-2
	 * @return CommonInfoManager惟一实例
	 */
	public static CommonInfoManager getInstance()
	{
		return cim;
	}
	/**
	 * 加入用户名
	 * @param user 类型 String 用户名
	 * @version 2006-8-29
	 * 
	 */
	public void loginUser(String user)
	{
		if(!users.contains(user))
		users.add(user);
	}
	/**
	 * 删除用户名
	 * @param user 类型 String 用户名 
	 * @version 2006-8-29
	 */
	public void removeUser(String user)
	{
		if(users.contains(user))
			users.remove(user);
	}
	/**
	 * 记录日志
	 * @param user a <code>String</code> is log's user id .
	 * @param operType a <code>String</code> is log's type id .
	 * @param mod a <code>String</code> is log's module id .
	 * @param ip a <code>String</code> is log's ip .
	 * @param remark a <code>String</code> is log's remark .
	 * @version 2006-8-29
	 */
	public void addLog(String user,String operType,String mod,String ip,String remark)
	{
		LogSys log=new LogSys(user,operType,mod,ip,remark);
		logs.add(log);
		if(logs.size()>=this.maxLogSize)
			insertLogDB();
	}
	/**
	 * 插入log到数据库，启动数目由maxLogSize设置
	 * @param
	 * @version 2006-8-2
	 * @return
	 */
	private void insertLogDB()
	{
		SpringRunningContainer src=SpringRunningContainer.getInstance();
		LogService ls=(LogService)src.getBean("logService");
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Iterator<LogSys> a=ectypeLog.iterator();
		while(a.hasNext())
		{
			IBaseDTO dto=new DynaBeanDTO();
			LogSys log=a.next();
			dto.setObject(log);
			l.add(dto);
		}
		Iterator<LogSys> i=logs.iterator();
		while(i.hasNext())
		{
			IBaseDTO dto=new DynaBeanDTO();
			LogSys log=i.next();
			dto.setObject(log);
			l.add(dto);
			ectypeLog.add(log);
			logs.remove(log);
		}
		
		try
		{
		ls.addLog(l);
		ectypeLog.clear();
		}catch(Exception e)
		{
			//log4j.error("日志文件不能写进数据库", e);
		}
	}
	/**
	 * 返回在线用户数
	 * @version 2006-8-29
	 * @return 在线用户数
	 */
	public int getUserSize()
	{
		return users.size();
	}
	/**
	 * 返回在线用户列表
	 * @version 2006-8-29
	 * @return List<String> 返回用户id列表
	 */
	public List getUserList()
	{
		return users;
	}
	public static void main(String[] arg0)
	{
	
	}
}
