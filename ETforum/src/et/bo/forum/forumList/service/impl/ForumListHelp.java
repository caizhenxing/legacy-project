/**
 * 	@(#)ForumListHelp.java   2006-11-23 上午09:33:36
 *	 。 
 *	 
 */
package et.bo.forum.forumList.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
public class ForumListHelp extends MyQueryImpl{
	/**
	 * 查询论坛模块
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery moduleQuery(String moduleId){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumTopic.class);
		dc.add(Restrictions.eq("isSys", "1"));
//		System.out.println(moduleId);
		dc.add(Restrictions.eq("parentId", moduleId));
		dc.addOrder(Order.desc("id"));
		mq.setDetachedCriteria(dc);
		return mq;       
	}   
	/**
	 * 查询子论坛模块
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery parentModuleQuery(String moduleId){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumTopic.class);
		dc.add(Restrictions.eq("isSys", "1"));
//		System.out.println(moduleId);
//		dc.add(Restrictions.eqProperty("id", "parentId"));
		dc.add(Restrictions.eq("id", moduleId));
//		dc.addOrder(Order.desc("id"));
		mq.setDetachedCriteria(dc);
		return mq;       
	}   
	/**
	 * 帖子列表查询
	 * @param
	 * @version 2006-11-23
	 * @return
	 */
	public MyQuery postListQuery(String moduleId, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumPosts.class);
		dc.add(Restrictions.eq("itemId", moduleId));
//		System.out.println(moduleId);
		dc.add(Restrictions.eqProperty("id", "parentId"));
//		dc.add(Restrictions.eq("parentId", moduleId));
		dc.addOrder(Order.desc("modTime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}
	/**
	 * 帖子查询
	 * @param
	 * @version 2006-11-28
	 * @return
	 */
	public  MyQuery postQuery(String postId, PageInfo pi){
		MyQuery mq =new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(ForumPosts.class);
		dc.add(Restrictions.eq("parentId", postId));
		dc.addOrder(Order.asc("postAt"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	} 
	
	/**
	 * 回帖列表查询
	 * @param
	 * @version 2006-11-23
	 * @return
	 */
	public MyQuery answerList(String itemId, String postid, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumPosts.class);
		dc.add(Restrictions.eq("parentId", postid));
		dc.add(Restrictions.eq("itemId", itemId));
		dc.addOrder(Order.asc("postAt"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}
	
	public MyQuery titleByPostid(String postid){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumPosts.class);
		dc.add(Restrictions.eqProperty("id", "parentId"));
		dc.add(Restrictions.eq("id", postid));
		mq.setDetachedCriteria(dc);
		return mq;       
	}
	
	/**
	 * 
	 * @param
	 * @version Dec 18, 2006
	 * @return
	 */
	public MyQuery forumUserInfo(String username){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumUserInfo.class);
		dc.add(Restrictions.eq("name", username));
		mq.setDetachedCriteria(dc);
		return mq;       
	}
	

}
