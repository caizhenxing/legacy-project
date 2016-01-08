package et.bo.caseinfo.hzinfo.service.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.stat.service.impl.StatDateStr;
import et.po.OperCaseinfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class HZHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery HZinfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperCaseinfo.class);
		
	
		String caseSubject = (String)dto.get("caseSubject");
		String caseContent = (String)dto.get("caseContent");
		String caseReview = dto.get("caseReview")==null?"":dto.get("caseReview").toString();
		String beginTime = (String)dto.get("beginTime");
		String endTime = (String)dto.get("endTime");
		String caseExpert = (String)dto.get("caseExpert");		
		String id=dto.get("caseRid").toString();
		if(!id.equals(""))
		{
			dc.add(Restrictions.eq("caseRid", id));
		}
		if(!caseExpert.equals(""))
		{
			dc.add(Restrictions.like("caseExpert","%"+caseExpert+"%"));
		}
		if(!caseReview.equals(""))
		{
			dc.add(Restrictions.like("caseReview","%"+caseReview+"%"));
		}
		if(!caseSubject.equals("")){
			dc.add(Restrictions.like("caseSubject", "%"+caseSubject+"%"));
		}
		
		if(!caseContent.equals("")){
			dc.add(Restrictions.like("caseContent", "%"+caseContent+"%"));
		}
		
		if(!beginTime.equals(""))
		{
			dc.add(Restrictions.ge("caseTime",TimeUtil.getTimeByStr(beginTime, StatDateStr.getParseDateStr(beginTime))));
		}
		if(!endTime.equals(""))
		{
			dc.add(Restrictions.le("caseTime",TimeUtil.getTimeByStr(endTime, StatDateStr.getParseDateStr(beginTime))));
		}
		//System.out.println(TimeUtil.getTimeByStr(beginTime, StatDateStr.getParseDateStr(beginTime))+"################"+TimeUtil.getTimeByStr(beginTime, StatDateStr.getParseDateStr(beginTime)));
		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		
		dc.add(Restrictions.eq("dictCaseType", "HZCase"));
		dc.addOrder(Order.desc("caseTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		
		
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
