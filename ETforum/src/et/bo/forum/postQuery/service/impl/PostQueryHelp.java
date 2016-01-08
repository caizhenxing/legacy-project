/**
 * 	@(#)PostQueryHelp.java   2006-11-29 下午04:32:58
 *	 。 
 *	 
 */
package et.bo.forum.postQuery.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.ForumCollection;
import et.po.ForumExprienceLevel;
import et.po.ForumPosts;
import et.po.ForumTopten;
import excellence.common.page.PageInfo;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author 叶浦亮
 * @version 2006-11-29
 * @see
 */
public class PostQueryHelp extends MyQueryImpl {
	/**
	 * 我的发帖查询
	 * @param
	 * @version 2006-11-30
	 * @return
	 */
	public MyQuery mySendPostQuery(String userId, PageInfo pageInfo){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumPosts.class);
		dc.add(Restrictions.eq("userkey", userId));
		dc.add(Restrictions.eqProperty("id", "parentId"));
		dc.addOrder(Order.desc("postAt"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pageInfo.getBegin());
		mq.setFetch(pageInfo.getPageSize());
		return mq;   
	}
	/**
	 * 我的回帖查询
	 * @param
	 * @version 2006-11-30
	 * @return
	 */
	public MyQuery myAnswerPostQuery(String userId, PageInfo pageInfo){
		MyQuery mq=new MyQueryImpl();
//		StringBuilder hql = new StringBuilder();
//		hql.append("from ForumPosts where id in (select parentId from ForumPosts where id<>parentId and ");
//		hql.append(" userkey=?) ");
//		hql.append(" order by post_at desc ");
		String hql1 = "from ForumPosts where id in (select parentId from ForumPosts where id<>parentId and userkey='"
            + userId
            + "' ) order by post_at desc";
		mq.setHql(hql1);
//		mq.setHql(hql.toString());
//		mq.setParameter(0, userId);
//		System.out.println(hql.toString());
		mq.setFirst(pageInfo.getBegin());
		mq.setFetch(pageInfo.getPageSize());
		return mq;   
	}
	/**
	 * 我的收藏查询
	 * @param
	 * @version 2006-11-30
	 * @return
	 */
	public MyQuery mySavePostQuery(String userId, PageInfo pageInfo){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumCollection.class);
		dc.add(Restrictions.eq("UId", userId));
		dc.addOrder(Order.desc("collTime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pageInfo.getBegin());
		mq.setFetch(pageInfo.getPageSize());
		return mq;   
	}
	/**
	 * 回复十大查询
	 * @param
	 * @version 2006-11-30
	 * @return
	 */
	public MyQuery topTenQuery(String type){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumTopten.class);
		dc.add(Restrictions.eq("type", type));
		mq.setDetachedCriteria(dc);
		return mq;   
	}
	/**
	 * 回复十大查询
	 * @param
	 * @version 2006-11-30
	 * @return
	 */
	public MyQuery bestNewPostList(){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumPosts.class);
		dc.add(Restrictions.eqProperty("id", "parentId"));
		dc.addOrder(Order.desc("postAt"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		return mq;   
	}
}
