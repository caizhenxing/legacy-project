/**
 * 	@(#)UserManagerServiceImpl.java   2006-12-25 …œŒÁ09:17:10
 *	 °£ 
 *	 
 */
package et.bo.forum.userManager.service.impl;

import java.util.ArrayList;
import java.util.List;
import et.bo.forum.userManager.service.UserManagerService;
import et.po.ForumUserInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author “∂∆÷¡¡
 * @version 2006-12-25
 * @see
 */
public class UserManagerServiceImpl implements UserManagerService {
	
    private int num = 0;
	
	private BaseDAO dao = null;

	public void deleteUser(String[] idArray) {
		// TODO Auto-generated method stub
		 for (int i = 0; i < idArray.length; i++) {
	            String userId = idArray[i];
	            ForumUserInfo fui = (ForumUserInfo) dao.loadEntity(
	            		ForumUserInfo.class, userId);
	            try {
					dao.removeEntity(fui);
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }	
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public List userQuery(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		UserManagerHelp umh = new UserManagerHelp();
		Object[] result = (Object[])dao.findEntity(umh.userListQuery(dto,pageInfo));
		num = dao.findEntitySize(umh.userListQuery(dto, pageInfo));
		for(int i=0,size=result.length;i<size;i++){
			ForumUserInfo fui = (ForumUserInfo)result[i];
			list.add(poToDTO(fui));
		}
		return list;
	}
	private DynaBeanDTO poToDTO(ForumUserInfo fui){
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", fui.getId());
		dbd.set("name",fui.getName());
		dbd.set("registerDate", fui.getRegisterDate());
		dbd.set("sendPostNum", "100");
		dbd.set("answerPostNum", "200");
		dbd.set("point", fui.getPoint());
		dbd.set("forumRole", fui.getForumRole());
		return dbd;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

}
