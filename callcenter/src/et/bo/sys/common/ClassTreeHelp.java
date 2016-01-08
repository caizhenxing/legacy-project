package et.bo.sys.common;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import org.hibernate.criterion.Restrictions;

import et.po.SysTree;
import et.po.SysUser;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;



public class ClassTreeHelp {

	public MyQuery createClassTreeQuery()
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysTree.class);
		
		dc.add(Restrictions.eq("tagDel","0"));
		dc.addOrder(Order.asc("layerOrder"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	

}
