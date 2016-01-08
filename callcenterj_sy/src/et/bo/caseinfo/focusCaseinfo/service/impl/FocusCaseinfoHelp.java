package et.bo.caseinfo.focusCaseinfo.service.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;



import et.bo.stat.service.impl.StatDateStr;
import et.po.OperCaseinfo;
import et.po.OperCorpinfo;
import et.po.OperPriceinfo;

import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class FocusCaseinfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery focusCaseinfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperCaseinfo.class);
		
		String caseRid =(String)dto.get("caseRid");
		String custName = (String)dto.get("custName");
		String custTel = (String)dto.get("custTel");
		String custAddr = (String)dto.get("custAddr");
		String beginTime = (String)dto.get("beginTime");
		String endTime = (String)dto.get("endTime");
		String caseContent = (String)dto.get("caseContent");
		String caseAttr4 = (String)dto.get("caseAttr4");
		
		String caseReview = dto.get("caseReview")==null?"":dto.get("caseReview").toString();
		if(!caseReview.equals(""))
		{
			dc.add(Restrictions.like("caseReview","%"+caseReview+"%"));
		}
		if(!caseAttr4.equals("")){
			dc.add(Restrictions.like("caseAttr4","%"+caseAttr4 +"%"));
		}
		if(!caseRid.equals("")){
			dc.add(Restrictions.like("caseRid","%"+caseRid +"%"));
		}
		if(!caseContent.equals("")){
			dc.add(Restrictions.like("caseContent", "%"+caseContent+"%"));
		}
		
		if(!caseRid.equals("")){
			dc.add(Restrictions.like("caseRid","%"+caseRid +"%"));
		}
	
		if(!custName.equals("")){
			dc.add(Restrictions.like("custName", "%"+custName+"%"));
		}
		if(!custTel.equals("")){
			dc.add(Restrictions.like("custTel", "%"+custTel+"%"));
		}
	
		if(!custAddr.equals("")){
			dc.add(Restrictions.like("custAddr", "%"+custAddr+"%"));
		}
			
		
		if(!beginTime.equals(""))
		{
			dc.add(Restrictions.ge("caseTime",TimeUtil.getTimeByStr(beginTime, StatDateStr.getParseDateStr(beginTime))));
		}
		if(!endTime.equals(""))
		{
			dc.add(Restrictions.le("caseTime",TimeUtil.getTimeByStr(endTime, StatDateStr.getParseDateStr(beginTime))));
		}
		
		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		
		dc.add(Restrictions.eq("dictCaseType", "FocusCase"));
		dc.addOrder(Order.desc("caseTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		
		
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
}
