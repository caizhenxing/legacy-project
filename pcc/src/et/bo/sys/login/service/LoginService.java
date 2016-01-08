package et.bo.sys.login.service;

import ocelot.common.tree.TreeControlI;
import et.bo.sys.login.UserInfo;

public interface LoginService {

	public boolean login(String userId,String password);
	
	public UserInfo loadUser(String userId);
	
	public TreeControlI loadTree(String userId);
}
