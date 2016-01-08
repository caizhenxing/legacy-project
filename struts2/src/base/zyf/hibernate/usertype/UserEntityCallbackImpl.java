/**
 * 
 * 项目名称：struts2
 * 制作时间：Apr 30, 200911:26:11 AM
 * 包名：base.zyf.hibernate.usertype
 * 文件名：UserEntityCallbackImpl.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.hibernate.usertype;

import base.zyf.hibernate.usertype.UserEntityUserType.UserEntityCallbackHandler;
import base.zyf.spring.SpringRunningContainer;
import base.zyf.web.crud.service.CommonService;

import com.cc.sys.db.SysUser;

/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class UserEntityCallbackImpl implements UserEntityCallbackHandler {

	/* (non-Javadoc)
	 * @see base.zyf.hibernate.usertype.UserEntityUserType.UserEntityCallbackHandler#afterNullSafeGet(base.zyf.hibernate.usertype.UserEntity)
	 */
	public void afterNullSafeGet(UserEntity ue) throws Throwable {
		// TODO Auto-generated method stub
		CommonService cs = (CommonService)SpringRunningContainer.
						getInstance().getBean(CommonService.SERVICE_NAME);
		SysUser su = (SysUser)cs.load(SysUser.class, ue.getUserId());
		ue.setName(su.getUserName());
	}
}
