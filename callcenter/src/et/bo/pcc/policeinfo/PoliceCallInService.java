/**
 * 	@(#)PoliceCallInService.java   Oct 11, 2006 3:17:06 PM
 *	 �� 
 *	 
 */
package et.bo.pcc.policeinfo;

import java.util.Set;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhang
 * @version Oct 11, 2006
 * @see
 */
public interface PoliceCallInService {
	
	/**
	 * ��ӹ���������Ϣ�Ͳ�ѯ����
	 * @param policeCallInDTO IBaseDTO ����������Ϣ����ˮ�ţ����ŵȣ�
	 * @param policeCallInInfo Set ����pojo����� 
	 * @version Oct 11, 2006
	 * @return true ����ɹ� false ����ʧ��
	 */
	public boolean addPoliceCallInInfo(et.bo.callcenter.business.impl.PoliceCallin poc);
	
}
