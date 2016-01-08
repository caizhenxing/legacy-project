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
	 * 添加警员修改成功后的信息
	 * @param
	 * @version Oct 20, 2006
	 * @return
	 */
	public boolean addPoliceValidInfo(IBaseDTO dto);
	
	/**
	 * 验证警员信息是否正确
	 * @param
	 * @version Oct 20, 2006
	 * @return
	 */
	public String validPoliceInfo(IBaseDTO dto);
}
