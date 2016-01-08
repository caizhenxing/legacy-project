package et.bo.forum.moduleManager.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.ForumTopic;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ModuleManagerHelp extends MyQueryImpl {
	/**
	 * 查询论坛模块
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery moduleQuery(){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumTopic.class);
		dc.add(Restrictions.eq("isSys", "1"));
		dc.add(Restrictions.eq("parentId", "1"));
		dc.addOrder(Order.desc("paixu"));
		mq.setDetachedCriteria(dc);
		return mq;       
	}
	/**
	 * 查询论坛模块
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery moduleQuery(String id){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumTopic.class);
		dc.add(Restrictions.eq("isSys", "1"));
		dc.add(Restrictions.eq("parentId", id));
		mq.setDetachedCriteria(dc);
		return mq;       
	}  
	/**
	 * 查询论坛模块
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery moduleLabelValue(){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumTopic.class);
		dc.add(Restrictions.eq("isSys", "1"));
		dc.add(Restrictions.eq("parentId", "1"));
		mq.setDetachedCriteria(dc);
		return mq;       
	}
	/**
	 * 查询模块名是否存在
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery searchModuleName(String moduleName){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumTopic.class);
		dc.add(Restrictions.eq("topicTitle", moduleName));
		mq.setDetachedCriteria(dc);
//		mq.setFetch(1);
		return mq;       
	}
	/**
	 * 查询模块名是否存在
	 * @param
	 * @version 2006-11-23
	 * @return MyQuery
	 */
	public MyQuery searchModuleName(String moduleName,String id){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ForumTopic.class);
		dc.add(Restrictions.ne("id", id));
		dc.add(Restrictions.eq("topicTitle", "moduleName"));
		mq.setDetachedCriteria(dc);
		mq.setFetch(1);
		return mq;       
	}
	
}
