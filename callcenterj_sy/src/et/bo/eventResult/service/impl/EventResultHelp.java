

package et.bo.eventResult.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.OperCustinfo;
import et.po.OperEventResultView;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class EventResultHelp extends MyQueryImpl{
	
	public MyQuery eventResultQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperEventResultView.class);

		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		String topic = (String) dto.get("topic");
		String principal = (String) dto.get("principal");
		String linkmanId = (String) dto.get("linkman_id");
		String feedback = (String) dto.get("feedback");
		
		if (!beginTime.equals("")){
			dc.add(Restrictions.ge("feedbackDate", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals("")){
			dc.add(Restrictions.le("feedbackDate", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		if (!topic.equals("")){
			dc.add(Restrictions.like("topic", "%"+ topic +"%"));
		}
		if (!principal.equals("")){
			dc.add(Restrictions.like("principal", "%"+ principal +"%"));
		}
		if (!linkmanId.equals("")){
			dc.add(Restrictions.like("linkmanId", "%"+ linkmanId +"%"));
		}
		if (!feedback.equals("")){
			dc.add(Restrictions.like("feedback", "%"+ feedback +"%"));
		}

		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;
	}
	
	public MyQuery linkmanQuery(String linkman){
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);
		
		dc.add(Restrictions.eq("custId", linkman));
		mq.setDetachedCriteria(dc);
		return mq;
	}

}
