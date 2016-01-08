/**
 * 	@(#)PointHelp.java   2006-11-29 下午02:36:29
 *	 。 
 *	 
 */
package et.bo.forum.point.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.ForumExprienceLevel;
import et.po.ForumTopic;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author 叶浦亮
 * @version 2006-11-29
 * @see
 */
public class PointHelp extends MyQueryImpl {
	/**
	 * 查询用户等级
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery userLevelQuery(int point){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumExprienceLevel.class);
//		dc.add(Restrictions.ge("point", (Integer)point));
		dc.add(Restrictions.lt("point", (Integer)point));
//		dc.add(Restrictions.eq("remark", "1"));
//		System.out.println(moduleId);
//		dc.add(Restrictions.eq("parentId", moduleId));
		dc.addOrder(Order.desc("point"));
		mq.setDetachedCriteria(dc);
		mq.setFetch(1);
		return mq;       
	}   
}
