package et.bo.oa.privy.plan.service.impl;



import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import org.hibernate.criterion.Restrictions;

import et.po.PlanInfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class PlanHelp {

	public MyQuery createplanInfo(String user,String name,String type,String begin,String end,PageInfo pi)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(PlanInfo.class);
		if(name!=null&&!name.equals(""))
		dc.add(Restrictions.like("planTitle","%"+name+"%"));
		if(type!=null&&!type.trim().equals(""))
		dc.add(Restrictions.eq("planType",type));
		if(begin!=null&&!begin.trim().equals(""))
			dc.add(Restrictions.ge("endDate",TimeUtil.getTimeByStr(begin,"yyyy-MM-dd")));
		if(end!=null&&!end.trim().equals(""))
			dc.add(Restrictions.le("beginDate",TimeUtil.getTimeByStr(end,"yyyy-MM-dd")));
		if(user!=null&&!user.trim().equals(""))
			dc.add(Restrictions.le("employeeId",user));
		
		mq.setDetachedCriteria(dc);
		if(pi!=null)
		{
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		}
		return mq;
	}
	public MyQuery createplanDetailInfo(PlanInfo pi)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(PlanInfo.class);
		
			dc.add(Restrictions.eq("planInfo",pi));
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	public MyQuery createNow(String user)
	{
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(PlanInfo.class);
		
		dc.add(Restrictions.le("beginDate",TimeUtil.getNowTime()));
		dc.add(Restrictions.ge("endDate",TimeUtil.getNowTime()));
		dc.add(Restrictions.eq("employeeId",user));
		dc.addOrder(Order.asc("beginDate"));
		dc.add(Restrictions.isNotNull("approveMan"));
		
		mq.setDetachedCriteria(dc);
		
		return mq;
	}
	
}
