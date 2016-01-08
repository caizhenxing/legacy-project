/**
 * 	@(#)RegisterSearch.java   Dec 15, 2006 4:21:17 PM
 *	 。 
 *	 
 */
package et.bo.user.useroper.register.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.ForumUserInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhangfeng
 * @version Dec 15, 2006
 * @see
 */
public class RegisterSearch extends MyQueryImpl{
	/**
	 * 检查用户是否存在
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery userCheck(String name,String password){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumUserInfo.class);
		if (name!=null&&!name.trim().equals("")) {
			dc.add(Restrictions.eq("id",name));
		}
		if (password!=null&&!password.trim().equals("")) {
			dc.add(Restrictions.eq("password", password));
		}
		mq.setDetachedCriteria(dc);
		return mq;       
	}
	
	/**
	 * 检查用户id根据姓名
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery searchIdByName(String name){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumUserInfo.class);
		if (name!=null&&!name.trim().equals("")) {
			dc.add(Restrictions.eq("name",name));
		}
		mq.setDetachedCriteria(dc);
		return mq;       
	}
}
