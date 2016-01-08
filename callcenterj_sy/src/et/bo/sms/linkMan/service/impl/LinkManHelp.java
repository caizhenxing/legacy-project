package et.bo.sms.linkMan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.LinkGroup;
import et.po.LinkMan;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class LinkManHelp extends MyQueryImpl {
	private String addoneDate(String endTime) {
		Date date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd"));
		ca.add(ca.DATE, 1);
		date = ca.getTime();
		endTime = TimeUtil.getTheTimeStr(date, "yyyy-MM-dd HH:mm:ss");
		return endTime;
	}

	private String addHMS(String stime) {
		String hmsTime = "";
		hmsTime = stime + " 00:00:00";
		return hmsTime;
	}
	/**
	 * @describe ≤È—Ø¡–±Ì
	 */
	public MyQuery linkManQuery(IBaseDTO dto, PageInfo pi){
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String systime = sdf.format(cal.getTime());
		
//		MyQuery mq=new MyQueryImpl();
//		DetachedCriteria dc=DetachedCriteria.forClass(SmsSend.class);
//		String receiveNum =(String)dto.get("receiveNum");
//		if(!receiveNum.equals("")){
//			dc.add(Restrictions.like("receiveNum", "%"+receiveNum+"%"));
//		}
//	
//		String content =(String)dto.get("content");
//		if(!content.equals("")){
//			dc.add(Restrictions.like("content", "%"+content+"%"));
//		}
//		
////		String begintime =addHMS(dto.get("begintime").toString());
////		if (!begintime.equals("")) {
////			dc.add(Restrictions.le("operTime", begintime));
////		}	
////		String endtime =addoneDate(dto.get("endtime").toString());
////		if (!endtime.equals("")) {
////			dc.add(Restrictions.ge("operTime", endtime));
////		}
//		dc.add(Restrictions.ge("delSign","N"));
//		mq.setDetachedCriteria(dc);
//				
//		mq.setFirst(pi.getBegin());
//		mq.setFetch(pi.getPageSize());
//		return mq;       
		MyQuery mq=new MyQueryImpl();	
		StringBuffer hql = new StringBuffer();
		String s="select lm from LinkMan lm, LinkGroup lg where lm.linkGroup = lg.id";
		hql.append(s);
//			hql.append("and s3.num like '%"+num+"%' ");		
		String name =(String)dto.get("name");
		if(!"".equals(name)){
			hql.append(" and lm.name like '%"+ name +"%'");	
		}
		String mobile = (String)dto.get("mobile");
		if(!"".equals(mobile)){
			hql.append(" and lm.mobile like '%"+ mobile +"%'");
		}
		String sex = (String)dto.get("sex");
		if(!"".equals(sex)){
			hql.append(" and lm.sex like '%"+ sex +"%'");
		}
		String branch = (String)dto.get("branch");
		if(!"".equals(branch)){
			hql.append(" and lm.branch like '%"+ branch +"%'");
		}
		String groupId = (String)dto.get("groupId");
		if(!"".equals(groupId)){
			hql.append(" and lm.linkGroup.id like '%"+ groupId +"%'");
		}
		
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;   
	} 
	
	public MyQuery linkManQueryById(String id) {
		MyQuery mq=new MyQueryImpl();	
		DetachedCriteria dc=DetachedCriteria.forClass(LinkMan.class);
		dc.add(Restrictions.eq("id", id));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery linkGroupQueryById(String id) {
		MyQuery mq=new MyQueryImpl();	
		DetachedCriteria dc=DetachedCriteria.forClass(LinkGroup.class);
		dc.add(Restrictions.eq("id", id));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery linkGroupQuery() {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(LinkGroup.class);
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
