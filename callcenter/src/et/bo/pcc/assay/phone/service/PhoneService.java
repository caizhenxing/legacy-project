/**
 * 	@(#)PhoneService.java   Oct 12, 2006 11:01:19 AM
 *	 �� 
 *	 
 */
package et.bo.pcc.assay.phone.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 12, 2006
 * @see
 */
public interface PhoneService {
	
	/**
	 * ��ѯ�绰��Ϣ
	 * @param dto ���� IBaseDTO �绰��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List phoneIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getPhoneSize();
	
	/**
	 * �õ�������Ϣ
	 * @param id ���� String ������
	 * @version Aug 31, 2006
	 * @return
	 */
	public IBaseDTO getPhoneInfo(String id);
}
