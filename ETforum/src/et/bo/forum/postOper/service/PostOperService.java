/**
 * 	@(#)PostOperService.java   2006-11-22 ����03:59:03
 *	 �� 
 *	 
 */
package et.bo.forum.postOper.service;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhangfeng
 * @version 2006-11-22
 * @see
 */
public interface PostOperService {
	
	/**
	 * ��������������Ϣ�������ݿ�
	 * @param dto IBaseDTO
	 * @version Nov 29, 2006
	 * @return boolean true �����ɹ� false ����ʧ��
	 */
	public boolean sendPosts(IBaseDTO dto);
	
	/**
	 * ��������������Ϣ�������ݿ�
	 * @param dto IBaseDTO
	 * @version Nov 29, 2006
	 * @return boolean true �����ɹ� false ����ʧ��
	 */
	public boolean answerPosts(IBaseDTO dto);
	
	/**
	 * �༩���ӣ��޸�������Ϣ
	 * @param dto IBaseDTO
	 * @version Nov 29, 2006
	 * @return boolean true �༩�ɹ� false �༩ʧ��
	 */
	public boolean editPosts(IBaseDTO dto);
	
	/**
	 * ɾ�����ӣ�ɾ�����ݼ�¼
	 * @param id String �������߸���id
	 * @version Nov 29, 2006
	 * @return boolean true ɾ���ɹ� false ɾ��ʧ��
	 */
	public String delPosts(String postsid);
	
	/**
	 * �����ղؼ�
	 * @param id String �������߸���id
	 * @version Dec 4, 2006
	 * @return
	 */
	public void addCollection(IBaseDTO dto);

}
