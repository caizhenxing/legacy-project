/**
 * 
 */
package et.bo.callcenter.calloutlog.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.RecordId;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author Administrator
 * 
 */
public class CallOutHelp {
	public MyQuery recordQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		String caller = dto.get("telNum").toString();
		String wid = dto.get("workNum").toString();
		String beginTime = dto.get("beginTime").toString();
		String endTime = dto.get("endTime").toString();
		DetachedCriteria dc = DetachedCriteria.forClass(RecordId.class);
		if (!beginTime.equals("")) {
			dc.add(Restrictions.gt("starttime", TimeUtil.getTimeByStr(
					beginTime, "yyyy-MM-dd")));
		}
		if (!endTime.equals("")) {
			dc.add(Restrictions.lt("starttime", TimeUtil.getTimeByStr(endTime,
					"yyyy-MM-dd")));
		}
		if (!caller.equals("")) {
			dc.add(Restrictions.eq("caller", caller));
		}
		if (!wid.equals("")) {
			dc.add(Restrictions.eq("wid", wid));
		}
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	public MyQuery telQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		String telNum = (String) dto.get("telNum");
		String beginTime = (String) dto.get("beginTime");
		String beginHour = (String) dto.get("beginHour");
		String beginMinute = (String) dto.get("beginMinute");
		String endTime = (String) dto.get("endTime");
		String endHour = (String) dto.get("endHour");
		String endMinute = (String) dto.get("endMinute");
		String calloutType = (String) dto.get("calloutType");
		// DetachedCriteria dc=DetachedCriteria.forClass(CcTalk.class);
		StringBuffer hql = new StringBuffer();
		hql.append(" from GroupEgressive where 1=1 ");
		// 组合的开始时间(要求格式是到时分)
		String beginT = "";
		// 组合的结束时间(要求格式是到时分)
		String endT = "";

		if (!beginTime.equals("")) {
			// dc.add(Restrictions.gt("touchBegintime",
			// TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
			beginT = beginTime;
			if (!beginHour.equals("")) {
				beginT = beginT + " " + beginHour + ":0";
				if (!beginMinute.equals("")) {
					beginT = beginT.substring(0, beginT.lastIndexOf(":")) + ":"
							+ beginMinute;
				}
			}
			hql.append(" and begintime >= '" + beginT + "'");
		}
		if (!endTime.equals("")) {
			// dc.add(Restrictions.lt("touchBegintime",
			// TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
			endT = endTime;
			if (!endHour.equals("")) {
				endT = endT + " " + endHour + ":0";
				if (!endMinute.equals("")) {
					endT = endT.substring(0, endT.lastIndexOf(":")) + ":"
							+ endMinute;
				}
			}
			hql.append(" and begintime <= '" + endT + "'");
		}

		if (!calloutType.equals("")) {
			// dc.add(Restrictions.eq("respondent", caseId));
			hql.append(" and menuType = '" + calloutType + "'");
		}
		if (!telNum.equals("")) {
			// dc.add(Restrictions.eq("phoneNum", telNum));
//			hql.append(" and phoneNum = '" + telNum + "'");
			hql.append(" and telnum like '%" + telNum + "%'");
		}
		hql.append(" order by begintime desc");
		// mq.setDetachedCriteria(dc);
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	public MyQuery getInfo(String calloutId) {
		MyQuery mq = new MyQueryImpl();
		StringBuffer hql = new StringBuffer();
		hql.append(" from GroupEgressive where 1=1 ");
		hql.append(" and id = '"+ calloutId +"'");
		
		mq.setHql(hql.toString());
		return mq;
	}
}
