/**
 * 	@(#)UserManagerService.java   2006-12-25 上午09:16:25
 *	 。 
 *	 
 */
package et.bo.forum.userManager.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @discribe 用户管理
 * @author 叶浦亮
 * @version 2006-12-25
 * @see
 */
public interface UserManagerService {
	/**
	 * @discribe 用户查询
	 * @param
	 * @version 2006-12-25
	 * @return List
	 */
	public List userQuery(IBaseDTO dto, PageInfo pageInfo);
	/**
	 * @discribe 取得条数
	 * @param
	 * @version 2006-12-25
	 * @return
	 */
	public int getSize();
	/**
	 * 用户删除
	 * @param
	 * @version 2006-12-25
	 * @return
	 */
	public void deleteUser(String[] idArray);
}
