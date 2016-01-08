/**
 * 	@(#)UserInfoService.java   2006-11-22 下午03:53:43
 *	 。 
 *	 
 */
package et.bo.forum.userInfo.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author 叶浦亮
 * @version 2006-12-5
 * @see
 */
public interface UserInfoService {
	/**
	 * 取得用户信息
	 * @param
	 * @version 2006-12-5
	 * @return
	 */
    public IBaseDTO getUserInfo(String userId);
    /**
     * 取的好友列表
     * @param
     * @version 2006-12-5
     * @return
     */
    public List getUserInfoList(String userId, PageInfo pageInfo);
    /**
     * 取得用户条数
     * @param
     * @version 2006-12-5
     * @return
     */
    public int getSize();
    /**
     * 删除好友
     * @param
     * @version 2006-12-5
     * @return
     */
    public void delMyFriend(String userId, String myId);
}
