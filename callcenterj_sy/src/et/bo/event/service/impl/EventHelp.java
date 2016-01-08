

package et.bo.event.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.OperCaseinfo;
import et.po.OperEvent;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class EventHelp extends MyQueryImpl{
	
	public MyQuery eventQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperEvent.class);

		String beginTime = (String) dto.get("beginTime");
		String endTime = (String) dto.get("endTime");
		String topic = (String) dto.get("topic");
		String contents = (String) dto.get("contents");
		String principal = (String) dto.get("principal");
		String actor = (String) dto.get("actor");
		
		
		if (!beginTime.equals("")){
			dc.add(Restrictions.ge("eventdate", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals("")){
			dc.add(Restrictions.le("eventdate", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
		if (!topic.equals("")){
			dc.add(Restrictions.like("topic", "%"+ topic +"%"));
		}
		if (!contents.equals("")){
			dc.add(Restrictions.like("contents", "%"+ contents +"%"));
		}
		if (!principal.equals("")){
			dc.add(Restrictions.like("principal", "%"+ principal +"%"));
		}
		if (!actor.equals("")){
			dc.add(Restrictions.like("actor", "%"+ actor +"%"));
		}
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;
	}

}
