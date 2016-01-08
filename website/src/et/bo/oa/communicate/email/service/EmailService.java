/**
 * @(#)EmailService.java	1.0 06/08/28
 *
 *	 �� 
 *	 
 *
 */
package et.bo.oa.communicate.email.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * �ʼ��շ��ӿ���
 * @author  �ŷ�
 * @author  ��һ��
 * @version 1.0, 06/08/28
 * @see	    PageInfo
 * @see		IBaseDTO
 */
public interface EmailService {
	
	/**
	 * �����ʼ����ݸ���
	 * @param dto ���� IBaseDTO �ʼ���Ϣ
	 * @param adjunctList ���� List �����б�
	 * @return ���� boolean �������ɹ� ����ture����֮����false
	 * 
	 */
	public boolean saveEmailToDraft(IBaseDTO dto,List adjunctList);
	
	/**
	 * ���浽������,ת���ʼ���Ϣ,�ظ��ʼ���Ϣ
	 * @param dto ���� IBaseDTO �ʼ���Ϣ
	 * @param adjunctList ���� List �����б�
	 * @param mailType ���� String �ʼ�����
	 * @return ���� boolean �������ɹ� ����ture����֮����false
	 */
	public boolean saveEmailToAddresser(IBaseDTO dto,List adjunctList,String mailType);
	
	/**
	 * ɾ���ʼ���Ϣ��������
	 * @param selectIt ���� String[] ѡ���ʼ��б���Ϣ
	 * @return ���� boolean �޸�ɾ����־λ ����ture����֮����false
	 */
	public boolean delEmailToDustbin(String[] selectIt);
	
	/**
	 * ����ɾ���ʼ���Ϣ
	 * @param selectIt ���� String[] ѡ���ʼ��б���Ϣ
	 * @return ���� boolean �������ɾ���ɹ� ����ture����֮����false
	 */
	public boolean delEmailForever(String[] selectIt);
	
	/**
	 * ��ѯ�ʼ��б���Ϣ(�����ռ��䣬������,�ѷ��ʼ�,�ݸ���,������)
	 * @param dto ���� IBaseDTO �ʼ���Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @param mailboxType ���� String ��������
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List emailListIndex(IBaseDTO dto,PageInfo pageInfo,String mailboxType);
	public int getEmailIndexSize();
	
	/**
	 * �����ʼ�id�õ���Ӧ���ʼ���Ϣ
	 * @param id ���� String �ʼ���Ϣ
	 * @return ���� IBaseDTO �ʼ��б���Ϣ
	 */
	public IBaseDTO getInEmailInfo(String id);
	
	/**
	 * �õ��ʼ��б���Ϣ
	 * @param
	 * @version Sep 8, 2006
	 * @return
	 */
	public List getEmailBoxList(String userkey);
	
	/**
	 * IBaseDTO
	 * @param
	 * @version Sep 12, 2006
	 * @return
	 */
	public List<IBaseDTO> userList();

}
