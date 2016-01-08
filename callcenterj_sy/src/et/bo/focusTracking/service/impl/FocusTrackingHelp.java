package et.bo.focusTracking.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.FocusTracking;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class FocusTrackingHelp extends MyQueryImpl {

	public MyQuery focusTrackingQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FocusTracking.class);
		
		dc.add(Restrictions.eq("ftIsDel", 0));
		
		String ftPeriod = (String)dto.get("ftPeriod");		
		String ftTitle =(String)dto.get("ftTitle");
		String ftSummary = (String)dto.get("ftSummary");
		
		if(ftPeriod!=null&&!ftPeriod.equals("")){
			dc.add(Restrictions.like("ftPeriod", "%"+ftPeriod+"%"));
		}
		
		if(ftTitle!=null&&!ftTitle.equals("")){
			dc.add(Restrictions.like("ftTitle","%"+ftTitle +"%"));
		}
		
		if(ftSummary!=null&&!ftSummary.equals("")){
			dc.add(Restrictions.like("ftSummary", "%"+ftSummary+"%"));
		}
		
		dc.addOrder(Order.desc("ftCreateTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;       
	}   
	
}