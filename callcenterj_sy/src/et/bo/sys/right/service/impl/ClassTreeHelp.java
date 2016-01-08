package et.bo.sys.right.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.BaseTree;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;




public class ClassTreeHelp {

	public MyQuery createClassTreeQuery()
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(BaseTree.class);
		dc.add(Expression.ne("deleteMark", "1"));
		dc.addOrder(Order.asc("layerOrder"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery createClassTreeQuery(String type)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(BaseTree.class);
		dc.add(Expression.ne("deleteMark", "1"));
		dc.addOrder(Order.asc("layerOrder"));
		dc.add(Expression.eq("type", type));
		mq.setDetachedCriteria(dc);
		return mq;
	}

}
