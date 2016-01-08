/**
 * 	@(#)UserInfoService.java   2006-11-22 ����03:53:43
 *	 �� 
 *	 
 */
package et.bo.forum.userInfo.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Ҷ����
 * @version 2006-12-5
 * @see
 */
public interface UserInfoService {
	/**
	 * ȡ���û���Ϣ
	 * @param
	 * @version 2006-12-5
	 * @return
	 */
    public IBaseDTO getUserInfo(String userId);
    /**
     * ȡ�ĺ����б�
     * @param
     * @version 2006-12-5
     * @return
     */
    public List getUserInfoList(String userId, PageInfo pageInfo);
    /**
     * ȡ���û�����
     * @param
     * @version 2006-12-5
     * @return
     */
    public int getSize();
    /**
     * ɾ������
     * @param
     * @version 2006-12-5
     * @return
     */
    public void delMyFriend(String userId, String myId);
}
