/**
 * 	@(#)NewsInfoHelp.java   2007-1-16 …œŒÁ10:54:04
 *	 °£ 
 *	 
 */
package et.bo.newsInfo.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.AssetsInfo;
import et.po.AssetsOper;
import et.po.NewsArticle;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @describe
 * @author “∂∆÷¡¡
 * @version 2007-1-16
 * @see
 */
public class NewsInfoHelp extends MyQueryImpl {
	/**
	 * 
	 * @describe
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public MyQuery newsQuery(String type)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(NewsArticle.class);
		MyQuery mq =new MyQueryImpl();
		mq.setDetachedCriteria(dc);
		mq.setFetch(10);
		return mq;
	}
	public MyQuery newsAreaQuery(String classId, String newsNum)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(NewsArticle.class);
		MyQuery mq =new MyQueryImpl();
		dc.add(Expression.eq("classid",classId));
		dc.addOrder(Order.desc("updatetime"));
		mq.setDetachedCriteria(dc);
		mq.setFetch(Integer.parseInt(newsNum));
		return mq;
	}
}
