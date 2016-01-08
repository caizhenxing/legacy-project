/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-4-22
 */
package et.bo.sms.modermSend.service;

import java.util.List;


import et.bo.sms.modermSend.service.impl.SMSContent;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author ������
 * @version 1.0
 * 
 */
public interface SmsSendNewService {

	/**
	 * @describe ��������
	 * @param
	 * @return void
	 */
	public void sendToOne(SMSContent smscontent);

	/**
	 * @describe Ⱥ������
	 * @param
	 * @return void
	 */
	public void sendToGroup(SMSContent smscontent);

	/**
	 * @describe �ڱ��涨ʱ������Ϣ�Ͳݸ�ʱ,���ô˷���.
	 * @param
	 * @return void
	 */
	public boolean saveDraft(SMSContent smscontent, IBaseDTO dto);

	/**
	 * @describe �ڷ�����Ϣ�ɹ���ʱ�򣬰���Ӧ����Ϣ���ݷֱ�д�뷢����Ϣ���ݱ���ѷ�����Ϣ��.
	 * @param
	 * @return void
	 */
	public boolean saveSuccess(SMSContent smscontent);

	/**
	 * ����Ⱥ��id��ѯ��Ⱥ����ϵ��
	 * 
	 * @param groupId
	 * @return
	 */
	public List linkManQuery(String groupId);

	/**
	 * ����ͨѶ¼Ⱥ���б�
	 * 
	 * @return
	 */
	public List linkGroupQuery();
	
	

	/**
	 * ����Ⱥ��id��ѯ��Ⱥ����ϵ��
	 * 
	 * @param groupId
	 * @return String
	 */
	public String telByLinkMan(String id);
	
	/**
	 * ��ѯ�����б�,����ȫ����¼��list��
	 * ȡ�ò�ѯ�б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List custinfoAllQuery();
	
	public List getUserList(String userType);
}