/*
 * @(#)MessagesService.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */


package et.bo.messages.service;

import java.util.List;
import java.util.Map;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>��Ϣ����</p>.
 * 
 * @author nie
 */

public interface MessagesService {

	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�.
	 * 
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * 
	 * @return ���ݵ�list
	 */
	public List messagesQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�.
	 * 
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * 
	 * @return ���ݵ�list
	 */
	public List messagesAdminQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * ��ѯ�����б��������
	 * ȡ�������ѯ�б��������.
	 * 
	 * @return �õ�list������
	 */
	public int getMessagesSize(); 
	
	/**
	 * ��ѯ�����б��������
	 * ȡ�������ѯ�б��������.
	 * 
	 * @return �õ�list������
	 */
	public int getMessagesAdminSize(); 
	
	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * 
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * 
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getMessagesInfo(String id);
	
	/**
	 * �޸����ݡ�
	 * �޸�ĳ����¼�����ݡ�.
	 * 
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * 
	 * @return boolean
	 */
	public boolean updateMessages(IBaseDTO dto);
	
	/**
	 * ɾ�����ݡ�
	 * ɾ��ĳ����¼��.
	 * 
	 * @param id Ҫɾ�����ݵı�ʶ
	 */
	public void delMessages(String id);
	
	/**
	 * ���ɾ����
	 * ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����.
	 * 
	 * @param id Ҫ���ɾ�����ݵı�ʶ
	 * 
	 * @return true, if checks if is delete
	 */
	public boolean isDelete(String id);
	
	/**
	 * ������ݡ�
	 * �����ݿ������һ����¼��.
	 * 
	 * @param dto �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addMessages(IBaseDTO dto);
	
	/**
	 * �ظ�����
	 * �����ݿ������һ����¼��.
	 * 
	 * @param dto �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void backMessages(IBaseDTO dto);
	
	/**
	 * ����Ƿ����µĶ���Ϣ.
	 * 
	 * @param user the user
	 * 
	 * @return true �У�false û��
	 */
	public boolean isHaveMessage(String user);
	
	/**
	 * ȡ��û�д���������Ŀ.
	 * 
	 * @param user the user
	 * 
	 * @return true �У�false û��
	 */
	public String getStateCount(String user);
    /**
     * �õ�δ��ȡ����Ϣ
     * return List<MessageInfo>
     */
	void appendAllNonReadMsg();
	/**
	 * ��Ա�б�Ϊ��ѯҳ���ṩ����
	 * 
	 * @return the list< label value bean>
	 */
	public List<LabelValueBean> getUserList();
	
	/**
	 * ɾ������Ϣ��ȫѡɾ����
	 * @param str
	 */
	void delAllMessages(String[] str);
	
	//////////////////////////////zhang feng add test flexgrid///////////////////////////////////////
	
	Map messageList();
	
	///////////////////////////////////////////

}
