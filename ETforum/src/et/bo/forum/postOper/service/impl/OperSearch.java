/**
 * 	@(#)OperSearch.java   Dec 22, 2006 5:03:16 PM
 *	 。 
 *	 
 */
package et.bo.forum.postOper.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.ForumPostContent;
import et.po.ForumPosts;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhangfeng
 * @version Dec 22, 2006
 * @see
 */
public class OperSearch extends MyQueryImpl{
	/**
	 * 帖子的列表信息
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery postsList(String postsid){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumPosts.class);
		dc.add(Restrictions.eq("parentId", postsid));
		mq.setDetachedCriteria(dc);
		return mq;       
	}
	/**
	 * 帖子内容信息
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery postsContentList(String postsid){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumPostContent.class);
		dc.add(Restrictions.eq("id", postsid));
		mq.setDetachedCriteria(dc);
		return mq;       
	}
}
