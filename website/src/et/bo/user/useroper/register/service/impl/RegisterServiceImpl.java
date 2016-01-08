/**
 * 	@(#)RegisterServiceImpl.java   Nov 30, 2006 3:50:43 PM
 *	 。 
 *	 
 */
package et.bo.user.useroper.register.service.impl;

import java.util.Date;

import et.bo.user.useroper.register.service.RegisterService;
import et.po.ForumUserInfo;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhangfeng
 * @version Nov 30, 2006
 * @see
 */
public class RegisterServiceImpl implements RegisterService {
	
	private BaseDAO dao = null;
	
	private KeyService ks = null;
	
	//登录后有论坛普通用户权限
	private String NORMAL_MANNER = "normal";

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.useroper.register.service.RegisterService#registerUser(excellence.framework.base.dto.IBaseDTO)
	 */
	public void registerUser(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(addForumUser(dto));
	}
	//添加论坛用户
	private ForumUserInfo addForumUser(IBaseDTO dto){
		ForumUserInfo fu = new ForumUserInfo();
		//用户的userkey
//		fu.setId(ks.getNext("forum_user_info"));
		fu.setId((String)dto.get("name"));
		fu.setName((String)dto.get("name"));
		fu.setPassword((String)dto.get("password"));
		fu.setQuestion((String)dto.get("question"));
		fu.setAnswer((String)dto.get("answer"));
		fu.setGroomUser((String)dto.get("groomuser"));
		fu.setEmail((String)dto.get("email"));
		fu.setForumRole(NORMAL_MANNER);
		fu.setRegisterDate(TimeUtil.getNowTime());
		fu.setPoint(0);
		fu.setSendPostNum(0);
		fu.setAnswerPostNum(0);
		return fu;
	}

	/* (non-Javadoc)
	 * @see et.bo.forum.useroper.register.service.RegisterService#updateUserInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public void updateUserInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.updateEntity(upForumUserInfo(dto));
	}
	//修改论坛用户
	private ForumUserInfo upForumUserInfo(IBaseDTO dto){
		ForumUserInfo fu = (ForumUserInfo)dao.loadEntity(ForumUserInfo.class, (String)dto.get("id"));
		if (!((String)dto.get("repassword")).equals("")) {
			fu.setPassword((String)dto.get("repassword"));
		}
		if (!((String)dto.get("question")).equals("")) {
			fu.setQuestion((String)dto.get("question"));
		}
		if (!((String)dto.get("answer")).equals("")) {
			fu.setAnswer((String)dto.get("answer"));
		}
		fu.setQq((String)dto.get("qq"));
		fu.setMsn((String)dto.get("msn"));
		fu.setIcq((String)dto.get("icq"));
		fu.setHomepage((String)dto.get("homepage"));
		fu.setUnderWrite((String)dto.get("underwrite"));
		fu.setIntroself((String)dto.get("introself"));
		fu.setSex((String)dto.get("sex"));
		fu.setBirthday(new Date());
		return fu;
	}


	public boolean checkLogin(String name, String password) {
		// TODO Auto-generated method stub
        boolean flag = false;
		RegisterSearch rs = new RegisterSearch();
        Object[] result = (Object[])dao.findEntity(rs.userCheck(name, password));
        for(int i=0, size=result.length;i<size;i++){
        	flag = true;
        }
		return flag;
	}

	public String getIdByName(String name) {
		// TODO Auto-generated method stub
		String id = "";
		RegisterSearch rs = new RegisterSearch();
		Object[] result = (Object[])dao.findEntity(rs.searchIdByName(name));
		for (int i = 0; i < result.length; i++) {
			ForumUserInfo fu = (ForumUserInfo)result[i];
			id = fu.getId();
		}
		return id;
	}

}
