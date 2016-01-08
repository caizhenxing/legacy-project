/**
 * 	@(#)CommonInfoManager.java   2006-8-29 ����09:42:22
 *	 �� 
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
 * @author ��һ��
 * @version 2006-8-29
 * @see
 */
public class CommonInfoManager {

	//private static org.apache.commons.logging.Log log4j = LogFactory.getLog(CommonInfoManager.class);
	
	private List<String> users=new CopyOnWriteArrayList<String>();
	private List<LogSys> logs=new CopyOnWriteArrayList<LogSys>();
	List<LogSys> ectypeLog=new ArrayList<LogSys>();
	/**
	 * ��󻺳�log����
	 */
	private int maxLogSize=100;
	private static CommonInfoManager cim=new CommonInfoManager();
	/**
	 * ˽�й��캯��
	 *
	 */
	private CommonInfoManager()
	{
		
	}
	/**
	 * ��������
	 * ��������
	 * @version 2006-8-2
	 * @return CommonInfoManagerΩһʵ��
	 */
	public static CommonInfoManager getInstance()
	{
		return cim;
	}
	/**
	 * �����û���
	 * @param user ���� String �û���
	 * @version 2006-8-29
	 * 
	 */
	public void loginUser(String user)
	{
		if(!users.contains(user))
		users.add(user);
	}
	/**
	 * ɾ���û���
	 * @param user ���� String �û��� 
	 * @version 2006-8-29
	 */
	public void removeUser(String user)
	{
		if(users.contains(user))
			users.remove(user);
	}
	/**
	 * ��¼��־
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
	 * ����log�����ݿ⣬������Ŀ��maxLogSize����
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
			//log4j.error("��־�ļ�����д�����ݿ�", e);
		}
	}
	/**
	 * ���������û���
	 * @version 2006-8-29
	 * @return �����û���
	 */
	public int getUserSize()
	{
		return users.size();
	}
	/**
	 * ���������û��б�
	 * @version 2006-8-29
	 * @return List<String> �����û�id�б�
	 */
	public List getUserList()
	{
		return users;
	}
	public static void main(String[] arg0)
	{
	
	}
}
