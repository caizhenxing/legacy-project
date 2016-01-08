/**
 * 	@(#) InqueryService.java 2008-4-1 ����01:13:09
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.inquiry.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * ִ�е�������ά�������ݿ����
 * 
 * ʵ����et.bo.inquiry.service.impl.InquiryServiceImpl
 * 
 * @author ���Ʒ�
 */
public interface InquiryService {
	/**
	 * ��ѯ���������б�
	 * 
	 * @param dto
	 *            ���ݴ������
	 * @param pi
	 *            ҳ����Ϣ
	 * @return ���ϲ�ѯ�����ĵ�������list
	 */
	public List<DynaBeanDTO> query(IBaseDTO dto, PageInfo pi);

	/**
	 * ��ӵ�������
	 * 
	 * @param dto
	 *            ����������Ϣ
	 */
	public void add(DynaActionFormDTO dto);

	/**
	 * �޸ĵ���������Ϣ
	 * 
	 * @param dto
	 *            ����������Ϣ
	 */
	public void update(DynaActionFormDTO dto);

	/**
	 * ɾ������������Ϣ
	 * 
	 * @param dto
	 *            ����������Ϣ
	 */
	public void delete(DynaActionFormDTO dto);

	/**
	 * ��ѯ��ǰ��Ч�ĵ������� ����ǰʱ���ڵ����������Ч����
	 * 
	 * @return ��ѯ�б�
	 */
	public List<DynaBeanDTO> filter();

	/**
	 * ��õ�ǰ�����б�Ĵ�С
	 * 
	 * @return ��ǰ�����б��С
	 */
	public int getInquirySize();

	/**
	 * ���ݸ�����ID��ȡ�����������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public IBaseDTO getInquiryInfo(String id);

	/**
	 * ��������ID��ȡ������ĵ��鿨��Ϣ�б�
	 * 
	 * @param id ����ID
	 * @return ���鿨��Ϣ�б�
	 */
	public List<DynaBeanDTO> getCardInfo(String id);
	
	/**
	 * ��������ID��ȡ������ĵ��鿨�������Ϣ�б�
	 * 
	 * @param id ����ID
	 * @return ���鿨�������Ϣ�б�
	 */
	public List<DynaBeanDTO> getCardInfoReport(String id);
	/**
	 * ���ݸ�����ID��ȡ���鱨�����Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public IBaseDTO getInquiryInfoReport(String id);
	
	/**
	 * ���ݸ�����ID��ȡ���鱨�����Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public IBaseDTO getInquiryInfoReportLast();
	
	/**
	 * �޸ĵ��鱨�����Ϣ
	 * 
	 * @param dto ���鱨�����Ϣ
	 */
	public void updateReport(DynaActionFormDTO dto);
	
	/**
	 * ɾ���������Ϣ
	 * 
	 * @param dto ���鱨�����Ϣ
	 */
	public void deleteReport(DynaActionFormDTO dto);
	
	
	/**
	 * ���ݵ��鱨��ID���������ϢID
	 * 
	 * @param id ���鱨���ID
	 */
	public String getInquiryId(String id);
	
	/**
	 * ɾ����Ϣ
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
}
