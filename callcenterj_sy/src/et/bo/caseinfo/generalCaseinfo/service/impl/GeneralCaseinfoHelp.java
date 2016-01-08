package et.bo.caseinfo.generalCaseinfo.service.impl;
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
public class GeneralCaseinfoHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery generalCaseinfoQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperCaseinfo.class);
		
		String caseReply =(String)dto.get("caseReply");
		String caseContent = (String)dto.get("caseContent");
		String caseRid = (String)dto.get("caseRid");
		String caseAttr3 = (String)dto.get("caseAttr3");
		String caseExpert = (String)dto.get("caseExpert");
		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		String caseReview = dto.get("caseReview")==null?"":dto.get("caseReview").toString();
		
		if(!caseExpert.equals("")){
			dc.add(Restrictions.like("caseExpert","%"+caseExpert +"%"));
		}
		if(!caseReply.equals("")){
			dc.add(Restrictions.like("caseReply","%"+caseReply +"%"));
		}
		if(!caseReview.equals(""))
		{
			dc.add(Restrictions.like("caseReview","%"+caseReview+"%"));
		}
		if(!caseRid.equals("")){
			dc.add(Restrictions.like("caseRid","%"+caseRid +"%"));
		}
		
		if(!caseAttr3.equals("")){
			dc.add(Restrictions.like("caseAttr3","%"+caseAttr3 +"%"));
		}
		
		if(!caseContent.equals("")){
			dc.add(Restrictions.like("caseContent", "%"+caseContent+"%"));
		}
		
		if (!beginTime.equals("")){
			dc.add(Restrictions.ge("caseTime", TimeUtil.getTimeByStr(beginTime, StatDateStr.getParseDateStr(beginTime))));
		}
		if (!endTime.equals("")){
			dc.add(Restrictions.le("caseTime", TimeUtil.getTimeByStr(endTime, StatDateStr.getParseDateStr(beginTime))));
		}
		String state = (String) dto.get("state");
		if(!state.equals("")){
			dc.add(Restrictions.eq("state", state));
		}
		
		dc.add(Restrictions.eq("dictCaseType", "putong"));
		
		dc.addOrder(Order.desc("addtime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;       
	} 
	public MyQuery screenGeneralCaseinfoQuery(int size,String state){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperCaseinfo.class);
		
		dc.add(Restrictions.eq("state", state));
		
		dc.add(Restrictions.isNotNull("caseExpert"));
		
		dc.addOrder(Order.desc("addtime"));
		
		mq.setDetachedCriteria(dc);

		mq.setFirst(0);
		mq.setFetch(size);
		
		return mq;       
	}  
}
