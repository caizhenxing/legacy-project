/*
 * @(#)CallbackService.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */


package et.bo.callback.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>�ͻ�����</p>
 * 
 * @version 2008-04-01
 * @author nie
 */

public interface CallbackService {
	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List callbackQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * ��ѯ�����б��������
	 * ȡ�ò�ѯ�б��������
	 * @return �õ�list������
	 */
	public int getCallbackSize(); 
	/**
	 * ����IDȡ��һ�����ݵ�excellence.framework.base.dto.IBaseDTO����
	 * ȡ��ĳ�����ݵ���ϸ��Ϣ��
	 * @param id ȡ��excellence.framework.base.dto.IBaseDTO�ı�ʶ
	 * @return ����������Ϣ��excellence.framework.base.dto.IBaseDTO����
	 */
	public IBaseDTO getCallbackInfo(String id);
	/**
	 * �޸����ݡ�
	 * �޸�ĳ����¼�����ݡ�
	 * @param dto Ҫ���µĵ�excellence.framework.base.dto.IBaseDTO����
	 * @return boolean
	 */
	public boolean updateCallback(IBaseDTO dto);
	/**
	 * ɾ�����ݡ�
	 * ɾ��ĳ����¼��
	 * @param id Ҫɾ�����ݵı�ʶ
	 */
	public void delCallback(String id);
	/**
	 * ���ɾ����
	 * ����ֶ�"IS_DELETE"��Ϊ"1"ʱΪɾ����Ϊ"0"ʱδɾ����ʵ�����������ִ�е����޸�"IS_DELETE"�ֶεĲ�����
	 * @param id Ҫ���ɾ�����ݵı�ʶ
	 */
	public boolean isDelete(String id);
	/**
	 * ������ݡ�
	 * �����ݿ������һ����¼��
	 * @param dto �����ݵ�excellence.framework.base.dto.IBaseDTO����
	 */
	public void addCallback(IBaseDTO dto);
	/**
	 * ȡ���¿ͻ���¼��ID
	 * @return custID
	 */
	public String getCustId();


}
