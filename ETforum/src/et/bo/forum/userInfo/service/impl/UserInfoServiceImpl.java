/**
 * 	@(#)UserInfoServiceImpl.java   2006-12-5 ÏÂÎç03:58:58
 *	 ¡£ 
 *	 
 */
package et.bo.forum.userInfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.forum.point.service.PointService;
import et.bo.forum.userInfo.service.UserInfoService;
import et.po.ForumFriend;
import et.po.ForumUserInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhangfeng
 * @version 2006-12-5
 * @see
 */
public class UserInfoServiceImpl implements UserInfoService {
	
    int num = 0;
	
	private BaseDAO dao = null;
	
	private PointService pointService = null;

	/* (non-Javadoc)
	 * @see et.bo.forum.userInfo.service.UserInfoService#getSize()
	 */
	public int getSize() {
		// TODO Auto-generated method stub
		return num;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.userInfo.service.UserInfoService#getUserInfo(java.lang.String)
	 */
	public IBaseDTO getUserInfo(String userId) {
		// TODO Auto-generated method stub
		ForumUserInfo fui = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, userId);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("id", fui.getId());
		dto.set("name", fui.getName());
		dto.set("point", String.valueOf(fui.getPoint()));
		dto.set("userLevel", pointService.getUserLevel(fui.getPoint()));
		dto.set("email", fui.getEmail());
		dto.set("question", fui.getQuestion());
		dto.set("answer", fui.getAnswer());
		dto.set("qq", fui.getQq());
		dto.set("point", String.valueOf(fui.getPoint()));
		dto.set("lastLogin", fui.getLastLogin());
		dto.set("registerIp", fui.getRegisterIp());
		dto.set("registerDate", fui.getRegisterDate());
		dto.set("newlyIp", fui.getNewlyIp());
		dto.set("sendPostNum", String.valueOf(fui.getSendPostNum()));
		dto.set("answerPostNum", String.valueOf(fui.getAnswerPostNum()));
		return dto;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.userInfo.service.UserInfoService#getUserInfoList(java.lang.String)
	 */
	public List getUserInfoList(String userId, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		UserInfoHelp uih = new UserInfoHelp();
		Object[] result = (Object[])dao.findEntity(uih.userListQuery(userId,pageInfo));
		num = dao.findEntitySize(uih.userListQuery(userId, pageInfo));
		for(int i=0,size=result.length;i<size;i++){
			ForumFriend fui = (ForumFriend)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("userId", fui.getUserId());
			dbd.set("friendId", fui.getFriendId());
			dbd.set("friendName", fui.getFriendName());
			dbd.set("addDate", fui.getAddDate());
			list.add(dbd);
		}
		return list;
	}

	public void delMyFriend(String userId, String friendId){
		UserInfoHelp uih = new UserInfoHelp();
		Object[] result = (Object[])dao.findEntity(uih.userFriendQuery(userId, friendId));
		if(result.length!=0){
			ForumFriend ff = (ForumFriend)result[0];
			if(ff!=null){
				dao.removeEntity(ff);
			}
		}
		
	}
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public PointService getPointService() {
		return pointService;
	}

	public void setPointService(PointService pointService) {
		this.pointService = pointService;
	}

}
