/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sms.sendAndReceive.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe �����շ���ѯ
 * @author  ������
 * @version 1.0, 2008-03-27//
 * @see
 */
public interface sendAndReceiveService {
	/**
	 * @describe ��ѯ�ѷ�����Ϣ�б�
	 * @param
	 * @return List
	 */ 
	public List sendQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getSendSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(����)
	 */ 
	public IBaseDTO getSendInfo(String id);
	
	/**
	 * @describe ɾ���ѷ�����Ϣ
	 * @param
	 * @return void
	 */ 
	public void delSend(String id,String type);
	
	
	
	
	
	/**
	 * @describe ��ѯδ�����б�
	 * @param
	 * @return List
	 */ 
	public List sendNotQuery(IBaseDTO dto, PageInfo pi);
	
	
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getSendNotSize();
	
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getReceivetSize();
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(����)
	 */ 
	public IBaseDTO getSendNotInfo(String id);
	
	/**
	 * @describe ɾ��δ������Ϣ
	 * @param
	 * @return void
	 */ 
	public void delSendNot(String id,String type);
	
	/**
	 * @describe ��ѯ�յ�������Ϣ
	 * @param
	 * @return List
	 */
	public IBaseDTO receiveOneQuery(String num);
	/**
	 * @describe ��ѯ�յ���Ϣ�б�
	 * @param
	 * @return List
	 */ 
	public List receiveQuery(IBaseDTO dto, PageInfo pi,String num);
	
	
	/**
	 * @describe ��ѯ�յ�������Ϣ����
	 * @param
	 * @return List
	 */ 
	public List receiveSizeQuery();
	

	/**
	 * @describe������ɾ��
	 * @param
	 * @return void
	 */
	public void delMessage(String id);

	
}
