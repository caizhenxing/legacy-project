/**
 * 
 */
package et.bo.pcc.policevalid;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Administrator
 *
 */
public interface PostService {
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
	public String validPoliceInfo(IBaseDTO dto);
}
