/**
 * 
 * ����ʱ�䣺2007-8-21����10:20:33
 * �ļ�����CommonTableViewService.java
 * �����ߣ�zhaoyifie
 * 
 */
package base.zyf.web.crud.service;

import java.util.List;

/**
 * @author zhaoyifei
 *
 */
public interface CommonTableViewService{

	String SERVICE_NAME = "common.ViewRows";
	/**
	 * 
	 * �������� �õ��û����Ƶ���ʾ��
	 * @param userId �û�id
	 * @param c �������־û�������
	 * @return �����б�
	 * 2007-8-22����10:51:14
	 */
	public List getRows(String userId,String pageId);
	
	/**
	 * 
	 * �������� �õ��û����Ƶ���������
	 * @param userId �û�id
	 * @param c �������־û�������
	 * @param asc ����ʽ asc��desc
	 * @return �����б�
	 * 2007-8-22����10:51:14
	 */
	public List getRows(String userId,String pageId,String asc);
	/**
	 * 
	 * �������� �õ��û����Ƶ���������
	 * @param userId �û�id
	 * @param c �������־û�������
	 * @param asc �Ƿ���������
	 * @return �����б�
	 * 2007-8-22����10:51:14
	 */
	public List getRows(String userId, String pageId, boolean asc);
	
	/**
	 * 
	 * �������� ���п�����ʾ������
	 * @param c �������־û�������
	 * @return �����б�
	 * 2007-8-22����10:54:05
	 */
	public List getRows(String pageId);
	
	/**
	 * 
	 * �������� �õ��е������ֵ�
	 * @param c
	 * @param rowName
	 * @return
	 * 2007-8-22����11:06:12
	 */
	public String getRowsDict(String pageId,String rowName);
	
	/**
	 * 
	 * �������� ����
	 * @param l
	 * Oct 10, 2007 11:11:15 AM
	 */
	public void saveRows(String[] ids,String userId,String pageId);
	
	/**
	 * 
	 * �������� ����
	 * @param l
	 * Oct 10, 2007 11:11:15 AM
	 */
	public void saveRows(String[] ids,String userId,String pageId,String asc);
	
	/**
	 * 
	 * �������� ��ѯ������Ի���������
	 * @param pageId ҳ��id
	 * @param user �û�
	 * @return <code>"'aa','bb','dd'"</code>
	 * Nov 28, 2007 4:57:48 PM
	 * @version 1.0
	 * @author zhaoyf
	 */
	public String getSearchTags(String pageId,String user);
	
	/**
	 * 
	 * �������� ����
	 * @param l
	 * Oct 10, 2007 11:11:15 AM
	 */
	public void saveRows(String[] ids, String userId, String pageId,boolean asc);
	
	/**
	 * 
	 * �������� �Ƿ���������
	 * @return
	 * Jan 9, 2008 1:27:23 PM
	 * @version 1.0
	 * @author zhaoyf
	 */
	public boolean isSetIsNull();
}
