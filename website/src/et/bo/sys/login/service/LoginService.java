package et.bo.sys.login.service;

import et.bo.sys.login.UserInfo;
import excellence.common.tree.TreeControlI;

public interface LoginService {

	public boolean login(String userId,String password);
	
	public UserInfo loadUser(String userId);
	
	public TreeControlI loadTree(String userId);
}
