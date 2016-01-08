package et.bo.callcenter.appraise.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.CcUserAppraiseInfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * 
 * @author chen gang
 *
 */
public class AppraiseHelp extends MyQueryImpl {
	
	public MyQuery appQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		StringBuilder sb = new StringBuilder();
		sb.append("select cai from CcUserAppraiseInfo cai where cai.id = cai.id");
		if(!"".equals(dto.get("telNum").toString())) {
			sb.append(" and cai.callerid = '"+ dto.get("telNum").toString() +"'");
		}
		if(!"".equals(dto.get("appType").toString())) {
			sb.append(" and cai.type = '"+ dto.get("appType").toString() +"'");
		}
		if(!"".equals(dto.get("appResult").toString())) {
			sb.append(" and cai.userAppraise = '"+(String)dto.get("appResult")+"' ");
			//dc.add(Restrictions.eq("userAppraise", dto.get("appResult")));
		}
		if(!"".equals(dto.get("appraiseObject").toString())) {
			sb.append(" and cai.appraiseNum = '"+dto.get("appraiseObject").toString()+"'");
		}

		String beginTime = (String)dto.get("beginTime");
		String endTime = (String)dto.get("endTime");
		if(beginTime==null)
			beginTime = "";
		if(endTime==null)
			endTime = "";
		if(!"".equals(beginTime.trim())&&!"".equals(endTime.trim()))
		{
			sb.append(" and appraiseTime between '"+beginTime + "' and '" + endTime + "'");
		}
		else if(!"".equals(beginTime.trim()))
		{
			sb.append(" and appraiseTime like '"+beginTime+"'");
		}
		else if(!"".equals(endTime.trim()))
		{
			sb.append(" and appraiseTime like '"+endTime+"'");
		}
		sb.append(" order by appraiseTime desc");
		mq.setHql(sb.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	public MyQuery appQuerySize(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(CcUserAppraiseInfo.class);
		
		if(!"".equals(dto.get("telNum").toString())) {
			dc.add(Restrictions.ilike("callerid", dto.get("telNum")));
		}
		if(!"".equals(dto.get("appType").toString())) {
			dc.add(Restrictions.eq("type", dto.get("appType")));
		}
		if(!"".equals(dto.get("appResult").toString())) {
			dc.add(Restrictions.eq("userAppraise", dto.get("appResult")));
		}
		if(!"".equals(dto.get("appraiseObject").toString())) {
			dc.add(Restrictions.eq("appraiseNum", dto.get("appraiseObject")));
//			sb.append(" and cai.appraiseNum = '"+dto.get("appraiseObject").toString()+"'");
		}
		String beginTime = (String)dto.get("beginTime");
		String endTime = (String)dto.get("endTime");
		if(beginTime==null)
			beginTime = "";
		if(endTime==null)
			endTime = "";
		if(!"".equals(beginTime.trim())&&!"".equals(endTime.trim()))
		{
			dc.add(Restrictions.between("appraiseTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd"), TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
			//sb.append(" and appraiseTime between "+TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd") + " and " + TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd"));
		}
		else if(!"".equals(beginTime.trim()))
		{
			dc.add(Restrictions.like("appraiseTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
			//sb.append(" and appraiseTime like "+TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd"));
		}
		else if(!"".equals(endTime.trim()))
		{
			dc.add(Restrictions.like("appraiseTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
			//sb.append(" and appraiseTime like "+TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd"));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
