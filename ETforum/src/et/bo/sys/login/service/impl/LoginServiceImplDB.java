package et.bo.sys.login.service.impl;

import et.bo.sys.login.UserInfo;
import et.bo.sys.login.service.LoginService;
import et.bo.sys.module.service.ModuleService;
import et.bo.sys.user.service.UserService;
import et.po.SysUser;
import excellence.common.tree.TreeControlI;

public class LoginServiceImplDB implements LoginService {

	private UserService us=null;
	private ModuleService ms=null;
	public boolean login(String userId, String password) {
		// TODO Auto-generated method stub
		boolean login=us.check(userId,password);
		return login;
	}
	public UserInfo loadUser(String userId) {
		// TODO Auto-generated method stub
		SysUser su=(SysUser)us.uniqueUserPo(userId);
		UserInfo ui=new UserInfo();
		ui.setDep(su.getSysDepartment().getId());
		ui.setGroup(su.getSysGroup().getId());
		ui.setRole(su.getSysRole().getId());
		ui.setUserName(userId);
		return ui;
	}
	public TreeControlI loadTree(String userId) {
		// TODO Auto-generated method stub
		return ms.loadModules(userId);
	}
	public ModuleService getMs() {
		return ms;
	}
	public void setMs(ModuleService ms) {
		this.ms = ms;
	}
	public UserService getUs() {
		return us;
	}
	public void setUs(UserService us) {
		this.us = us;
	}

}
