/**
 * 	@(#)PoliceValidService.java   Oct 20, 2006 5:13:36 PM
 *	 。 
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
	public boolean validPoliceInfo(IBaseDTO dto);
	
	/**
	 * 查询电话信息
	 * @param dto 类型 IBaseDTO 电话信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回邮件列表信息
	 */
	public List infoIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getInfoSize();

}
