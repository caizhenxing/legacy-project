/**
 * 	@(#)HandsetNoteService.java   Sep 26, 2006 1:49:18 PM
 *	 �� 
 *	 
 */
package et.bo.oa.communicate.handsetnote.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Sep 26, 2006
 * @see
 */
public interface HandsetNoteService {
	
	/**
	 * �����ֻ���Ϣ�����ݿ�
	 * 
	 * @param dto
	 *            ���� IBaseDTO �ֻ���Ϣ
	 * @param sendtype 
	 * 			  ���� IBaseDTO ��������
	 * @return ���� boolean �������ɹ� ����ture����֮����false
	 * 
	 */
	public boolean saveHandsetNote(IBaseDTO dto,String sendtype);
	
	/**
	 * �޸��ֻ���Ϣ�����ݿ�
	 * 
	 * @param dto
	 *            ���� IBaseDTO �ֻ���Ϣ
	 * @return ���� boolean ����޸ĳɹ� ����ture����֮����false
	 * 
	 */
	public boolean updateHandsetNote(IBaseDTO dto);
	
	/**
	 * ɾ���ֻ���Ϣ����ɾ�����
	 * 
	 * @param dto
	 *            ���� IBaseDTO �ֻ���Ϣ
	 * @return ���� boolean ���ɾ���ɹ� ����ture����֮����false
	 * 
	 */
	public boolean delHandsetNote(String[] selectIt);
	
	/**
	 * ɾ���ֻ���Ϣ����ɾ��
	 * 
	 * @param dto
	 *            ���� IBaseDTO �ֻ���Ϣ
	 * @return ���� boolean ���ɾ���ɹ� ����ture����֮����false
	 * 
	 */
	public boolean delHandsetNoteForever(String[] selectIt);
	
	/**
	 * �ֻ���Ϣ��ѯ�б�
	 * @param dto ���� IBaseDTO �ʼ���Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @param mailboxType ���� String ��������
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List handsetIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getHandsetIndexSize();
	
	/**
	 * �õ��ֻ�������Ϣ
	 * @param
	 * @version Sep 26, 2006
	 * @return
	 */
	public IBaseDTO getHandsetNoteInfo(String id);
}
