package et.bo.sys.department.service.impl;

import ocelot.framework.base.query.MyQuery;
import ocelot.framework.base.query.impl.MyQueryImpl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import org.hibernate.criterion.Restrictions;

import et.po.SysDepartment;





public class ClassTreeHelp {

	public MyQuery createClassTreeQuery()
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysDepartment.class);
		//dc.add(Restrictions.eq("tagShow","1"));
		
		dc.addOrder(Order.asc("id"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	

}
