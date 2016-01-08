/**
 * 	@(#)MailBoxService.java   Aug 30, 2006 7:22:58 PM
 *	 �� 
 *	 
 */
package et.bo.oa.communicate.mailbox.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Aug 30, 2006
 * @see
 */
public interface MailBoxService {
	
	/**
	 * ������Ϣ���
	 * @param dto ���� IBaseDTO ������Ϣ
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean addMailBox(IBaseDTO dto);
	
	/**
	 * ������Ϣ�޸�
	 * @param dto ���� IBaseDTO ������Ϣ
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean updateMailBox(IBaseDTO dto);
	
	/**
	 * ������Ϣɾ��
	 * @param dto ���� IBaseDTO ������Ϣ
	 * @version Aug 30, 2006
	 * @return
	 */
	
	public boolean delMailBox(IBaseDTO dto);
	
	/**
	 * ��ѯ������Ϣ
	 * @param dto ���� IBaseDTO �ʼ���Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @param mailboxType ���� String ��������
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List emailBoxIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getEmailBoxSize();
	
	/**
	 * �õ��ʼ�������Ϣ
	 * @param id ���� String �ʼ�id
	 * @version Aug 30, 2006
	 * @return
	 */
	public IBaseDTO getEmailBox(String id);

}
