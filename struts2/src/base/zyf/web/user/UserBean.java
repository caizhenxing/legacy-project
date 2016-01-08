/**
 * 
 * 项目名称：struts2
 * 制作时间：May 5, 20093:03:59 PM
 * 包名：base.zyf.web.user
 * 文件名：UserBean.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.web.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.GrantedAuthority;

import base.zyf.common.tree.TreeNodeI;
import base.zyf.common.tree.module.ModuleLoadService;
import base.zyf.common.tree.module.ModuleTreeRight;
import base.zyf.hibernate.usertype.UserEntity;
import base.zyf.spring.SpringRunningContainer;

/**
 * 安全中存储的user信息，继承<code>UserEntity.java</code>可以判断用户
 * @author zhaoyifei
 * @version 1.0
 */
public class UserBean extends UserEntity {
	
	public static final String SESSION_USERBEAN_NAME="userbeaninsessionname";
	
	private GrantedAuthority[] authoritys;
	private Map<String, ModuleTreeRight> moduleRights ;
	private boolean isReloadModule = false;
	/**
	 * @return the isReloadModule
	 */
	public boolean isReloadModule() {
		return isReloadModule;
	}
	/**
	 * @param isReloadModule the isReloadModule to set
	 */
	public void setReloadModule(boolean isReloadModule) {
		this.isReloadModule = isReloadModule;
	}
	/**
	 * @return the moduleTree
	 */
	public TreeNodeI getModuleTree() {
		
			ModuleLoadService mls = (ModuleLoadService) SpringRunningContainer
					.getService(ModuleLoadService.SERVICE_NAME);
			return mls.loadTree();

	}
	/**
	 * @return the authoritys
	 */
	public GrantedAuthority[] getAuthoritys() {
		return authoritys;
	}
	/**
	 * @param authoritys the authoritys to set
	 */
	public void setAuthoritys(GrantedAuthority[] authoritys) {
		this.authoritys = authoritys;
	}
	/**
	 * @return the moduleRights
	 */
	public Map<String, ModuleTreeRight> getModuleRights() {
		if(this.moduleRights == null || isReloadModule)
		{
			ModuleLoadService mls = (ModuleLoadService) SpringRunningContainer
			.getService(ModuleLoadService.SERVICE_NAME);
			this.moduleRights = mls.loadTreeByUser(this.getUserId());
		}
		return moduleRights;
	}
	
	
}
