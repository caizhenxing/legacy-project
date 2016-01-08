package et.bo.sys.login.service.impl;

import java.util.HashMap;

import et.bo.sys.login.bean.UserBean;
import et.bo.sys.login.service.loginService;
import et.bo.sys.right.service.RightService;
import et.po.SysUser;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.dao.BaseDAO;

public class loginServiceImpl implements loginService {

	// static Logger log = Logger.getLogger(UserServiceImpl.class.getName());

	private BaseDAO dao = null;
	
	public static HashMap hashmap = new HashMap();

	//private ModuleService ms = null;
	private RightService rs = null;
	
	public RightService getRs() {
		return rs;
	}

	public void setRs(RightService rs) {
		this.rs = rs;
	}
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	
	public boolean login(String userId, String password) {
		// TODO 需要写出方法的具体实现
		SysUser su = (SysUser) dao.loadEntity(SysUser.class,userId);

		//冻结标志为1表示为正常用户
		if (null != su && password.equals(su.getPassword()) && su.getIsFreeze().equals("0")) {
			return true;
		}
		return false;
	}
	
	/**
	 * @describe 根据用户id得到用户权限树
	 * @param userId
	 * @return
	 */
	public TreeService loadTree(String userId)
	{
		return rs.getModuleTreeImpowers(userId);
	}

	public UserBean loadUserBean(String userId) {
		// TODO Auto-generated method stub
		UserBean ub = new UserBean();
		SysUser su = (SysUser) dao.loadEntity(SysUser.class,userId);
		ub.setUserId(su.getUserId());
		ub.setUserName(su.getUserName());
		ub.setUserDepartment(su.getSysDepartment()==null?"":su.getSysDepartment().getName());
		ub.setUserGroup(su.getSysGroup().getName());
		ub.setUserRole(su.getSysRole().getName());
		ub.setSkill(su.getSkill());
		return ub;
	}
	
}
