/**
 * 	@(#)PoliceValidService.java   Oct 20, 2006 5:13:36 PM
 *	 �� 
 *	 
 */
package et.bo.pcc.policevalid;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 20, 2006
 * @see
 */
public interface PoliceValidService {
	
	/**
	 * ��Ӿ�Ա�޸ĳɹ������Ϣ
	 * @param
	 * @version Oct 20, 2006
	 * @return
	 */
	public boolean addPoliceValidInfo(IBaseDTO dto);
	
	/**
	 * ��֤��Ա��Ϣ�Ƿ���ȷ
	 * @param
	 * @version Oct 20, 2006
	 * @return
	 */
	public boolean validPoliceInfo(IBaseDTO dto);
	
	/**
	 * ��ѯ�绰��Ϣ
	 * @param dto ���� IBaseDTO �绰��Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List infoIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getInfoSize();

}
