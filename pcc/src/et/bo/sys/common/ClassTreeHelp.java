package et.bo.sys.common;

import ocelot.framework.base.query.MyQuery;
import ocelot.framework.base.query.impl.MyQueryImpl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import org.hibernate.criterion.Restrictions;

import et.po.SysTree;
import et.po.SysUser;



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
