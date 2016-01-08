/**
 * 	@(#)ForumListHelp.java   2006-11-23 上午09:33:36
 *	 。 
 *	 
 */
package et.bo.forum.userInfo.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.ForumFriend;
import et.po.ForumPosts;
import et.po.ForumTopic;
import et.po.ForumUserInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author 叶浦亮
 * @version 2006-11-23
 * @see
 */
public class UserInfoHelp extends MyQueryImpl{
	/**
	 * 查询用户列表
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery userListQuery(String userId, PageInfo pageInfo){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumFriend.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.addOrder(Order.desc("addDate"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pageInfo.getBegin());
		mq.setFetch(pageInfo.getPageSize());
		return mq;       
	}   
	/**
	 * 查询用户好友
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery userFriendQuery(String userId, String friendId){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumFriend.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("friendId", friendId));
		mq.setDetachedCriteria(dc);
		mq.setFetch(1);
		return mq;       
	}   
	
	

}
