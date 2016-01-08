package et.bo.oa.assissant.conference.service.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.SynodNote;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ConferenceHelp {

	public MyQuery searchCMQ(IBaseDTO dto, PageInfo pi)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(SynodNote.class);
		//TODO
		if(null !=dto.get("synodTopic") && !"".equals((String)dto.get("synodTopic")))
			dc.add(Expression.like("synodTopic","%"+(String)dto.get("synodTopic")+"%"));
		if(null !=dto.get("synodOwner") && !"".equals((String)dto.get("synodOwner")))
			dc.add(Expression.like("synodOwner","%"+(String)dto.get("synodOwner")+"%"));
		if(null !=dto.get("synodAddr") && !"".equals((String)dto.get("synodAddr")))
			dc.add(Expression.like("synodAddr","%"+(String)dto.get("synodAddr")+"%"));
		if(null !=dto.get("examResult") && !"".equals((String)dto.get("examResult")))
			dc.add(Expression.eq("examResult",(String)dto.get("examResult")));
		dc.addOrder(Order.desc("applyTime"));
		MyQuery mq =new MyQueryImpl();
		mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	public MyQuery searchExamCMQ(IBaseDTO dto, PageInfo pi)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(SynodNote.class);
		//TODO
		if(null !=dto.get("synodTopic") && !"".equals((String)dto.get("synodTopic")))
			dc.add(Expression.like("synodTopic","%"+(String)dto.get("synodTopic")+"%"));
		if(null !=dto.get("synodOwner") && !"".equals((String)dto.get("synodOwner")))
			dc.add(Expression.like("synodOwner","%"+(String)dto.get("synodOwner")+"%"));
		if(null !=dto.get("synodAddr") && !"".equals((String)dto.get("synodAddr")))
			dc.add(Expression.like("synodAddr","%"+(String)dto.get("synodAddr")+"%"));
//		if(null !=dto.get("examResult") && !"".equals((String)dto.get("examResult")))
			dc.add(Expression.eq("examResult","-1"));
		dc.addOrder(Order.desc("applyTime"));
		MyQuery mq =new MyQueryImpl();
		mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
		return mq;
	}
}
