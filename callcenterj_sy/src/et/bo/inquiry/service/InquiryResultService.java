/**
 * 	@(#) InquiryResultService.java 2008-4-9 ����02:05:49
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.inquiry.service;

import java.util.List;

import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * ִ�е������ı��桢��ѯ�����ݿ����
 * ʵ����et.bo.inquiry.service.impl.InquiryResultServiceImpl
 * @author ���Ʒ�
 *
 */
public interface InquiryResultService {
	/**
	 * ȡ�ô˴ε����ʾ���
	 * @param topic
	 */
	public String getTopic();
	/**
	 * ȡ�ô˴ε�����
	 * @param InquiryNum
	 */
	public String getInquiryNum();
	/**
	 * �����û��������Ľ����Ϣ
	 * @param resultJSON �û������������Ϣ��JSON��
	 */
	public void save(List answerList);
	/**
	 * ��ȡָ��ID�ĵ�������Ϣ
	 * @param id ������ID
	 * @return
	 */
	public List<DynaBeanDTO> getResultInfo(String id);
}
