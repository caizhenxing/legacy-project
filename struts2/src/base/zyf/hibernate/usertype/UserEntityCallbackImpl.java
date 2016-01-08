/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺Apr 30, 200911:26:11 AM
 * ������base.zyf.hibernate.usertype
 * �ļ�����UserEntityCallbackImpl.java
 * �����ߣ�zhaoyifei
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
