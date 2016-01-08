/**
 * 	InquiryCardService.java   2008-4-2 ����02:56:11
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.inquiry.service;

import excellence.framework.base.dto.IBaseDTO;

/**
 * ִ�е���������������Ĳ�ѯ���޸ġ�ɾ������
 * ʵ����et.bo.service.impl.InquiryCardServiceImpl
 * @author ���Ʒ�
 *
 */
public interface InquiryCardService {
	/**
	 * ��������IDȡ��������Ϣ
	 * @param id ����ID
	 * @return
	 */
	public IBaseDTO getInquiryCardInfo(String id);
	/**
	 * �޸�һ��������Ϣ
	 * @param dto ��Ҫ�޸ĵ�������Ϣ����
	 */
	public void update(IBaseDTO dto);
	/**
	 * ɾ��һ��������Ϣ
	 * @param dto Ҫɾ����������Ϣ����
	 */
	public void delete(IBaseDTO dto);
}
